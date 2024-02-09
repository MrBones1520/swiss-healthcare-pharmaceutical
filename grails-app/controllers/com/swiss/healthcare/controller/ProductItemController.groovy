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
        def barcodes = request.JSON['barcodes'] as List<String>
        def baseItemBody = request.JSON as HashMap<String, Object>
        baseItemBody.remove('barcodes')
        def items = barcodes
                .collect {barcode ->
                    def original = baseItemBody.clone() + [barcode: barcode] as ProductItem
                    if (ProductItem.exists(barcode)) {
                        original = ProductItem.get(barcode)
                        original.errors.reject('productItem.barcode.notunique')
                    }
                    if(!ProductStatus.exists(original?.status?.id))
                        original.errors.reject('productStatus.id.notfound')
                    if(!ProductBase.exists(original?.base?.id))
                        original.errors.reject('productBase.id.notfound')

                    original.validate()
                    original
                }
                .groupBy {it.hasErrors()}

        render(
            view: 'save',
            status: items ? '200' : '204',
            model: [saved: items[false]*.save(), errors: items[true]]
        )
    }

    @Transactional
    def update(){
        def barcodes = request.JSON['barcodes'] as List<String>
        def baseItemBody = request.JSON as HashMap<String, Object>
        baseItemBody.remove('barcodes')
        def status = ProductStatus.findAll().find {it.name == baseItemBody?.status }
        def base = ProductBase.get(baseItemBody?['base']?['id'])
        def items = barcodes
                .collect {barcode ->
                    if(!ProductItem.exists(barcode)){
                        def item = new ProductItem(barcode: barcode)
                        item.errors.reject('productItem.notfound.message')
                        return item
                    }
                    def loadItem = ProductItem.load(barcode)
                    loadItem.status = status
                    loadItem.base = base
                    loadItem.validate(['base', 'status'])
                    loadItem
                }.groupBy {it.hasErrors()}

        render(
                view: 'save',
                status: items ? '200' : '204',
                model: [saved: items.get(false)*.save(flush: true), errors: items.get(true)]
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
        def status0 = params.get('status').toString().toInteger()
        if(!ProductStatus.exists(status0))
            return render(view: '/error', status: '204', model: [messageError: ''])

        [values: productItemService.findAllByProductStatus(status0)]
    }

    @Transactional(readOnly = true)
    def base(){
        def productBaseId = params['id'].toString().toInteger()
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
