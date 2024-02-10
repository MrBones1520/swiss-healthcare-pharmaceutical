package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import com.swiss.healthcare.service.ProductItemService
import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import groovy.util.logging.Log

import static com.swiss.healthcare.ProductStatusE.*

@Log
class ProductItemController extends RestfulController<ProductItem>{

    ProductItemService productItemService

    ProductItemController() {
        super(ProductItem.class)
    }

    @Transactional
    def save(){
        def barcodes = request.JSON?.barcodes as List<String>
        def baseItem = request.JSON as HashMap<String, Object>
        baseItem.remove('barcodes')

        def items = barcodes
                .collect {productItemService.saveResolver(it, baseItem)}
                .groupBy { it.hasErrors()}

        render(
            view: 'save',
            status: items ? '200' : '204',
            model: [saved: items[false], errors: items[true]]
        )
    }

    @Transactional
    def update(){
        def barcodes = request.JSON['barcodes'] as List<String>
        def baseItem = request.JSON as HashMap<String, Object>
        baseItem.remove('barcodes')

        def status = ProductStatus.get(baseItem?.status?.id)
        def base = ProductBase.get(baseItem?.base?.id)

        def items = barcodes
                .collect {productItemService.updateResolver(it, base, status) }
                .groupBy {it.hasErrors()}

        render(
            view: 'save',
            status: items ? '200' : '204',
            model: [saved: items[false], errors: items[true]]
        )
    }

    @Transactional(readOnly = true)
    def index(){
        [
            products:       productItemService.findAll(),
            stockInCount:   productItemService.countByProductStatus(IN_STOCK.id),
            stockOutCount:  productItemService.countByProductStatus(OUT_STOCK.id),
            saleOutCount:   productItemService.countByProductStatus(OUT_SALE.id)
        ]
    }

    @Transactional(readOnly = true)
    def status(){
        int status0 = params.get('status').toString().toInteger()
        if(!ProductStatus.exists(status0))
            return render(view: '/error', status: '204', model: [messageError: ''])

        [values: productItemService.findAllByProductStatus(status0)]
    }

    @Transactional(readOnly = true)
    def base(){
        int productBaseId = params['id'].toString().toInteger()
        if(!ProductBase.exists(productBaseId))
            return render(view: '/error', status: '204', model: [messageError: "El producto base no existe con el valor $id".toString()])

        def all = productItemService.findAllByProductBase(productBaseId)
        def groupStatus = all.groupBy {it.status.id as Integer}

        render(
                view: 'index',
                model: [
                    products:       all,
                    stockInCount:   groupStatus?[IN_STOCK.id]?.size()  ?: 0,
                    stockOutCount:  groupStatus?[OUT_STOCK.id]?.size() ?: 0,
                    saleOutCount:   groupStatus?[OUT_SALE.id]?.size()  ?: 0
                ]
        )
    }

    @Transactional(readOnly = true)
    def show(String id){
        if(!ProductItem.exists(id))
            return render(view: '/error', status: '204', model: [messageError: "No existe ningún producto con valor $id"])
        [item: ProductItem.get(id)]
    }
}
