package com.swiss.healthcare.controller

import com.swiss.healthcare.ProductStatusE
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

        def items = barcodes.collect {
            def copy = baseItemBody.clone()
            copy['barcode'] = it
            def newItem = copy as ProductItem

            if(!newItem.validate())
                return newItem
            if(ProductItem.exists(it)){
                newItem.errors.rejectValue("barcode", "204", "Already exist")
                return newItem
            }
            if(!ProductBase.exists(newItem.base.ident()))
                newItem.errors.rejectValue("base.id", "1000", "Base id not found")
            if(!ProductStatus.exists(newItem.status.ident()))
                newItem.errors.rejectValue("status.id", "1000", "Status id not found")

            return newItem.errors.hasErrors() ? newItem : productItemService.save(newItem)
        }.groupBy { it.isAttached()}

        render(
            view: 'save',
            status: items ? '200' : '204',
            model: [saved: items.get(true), errors: items.get(false)]
        )
    }

    @Transactional
    def update(){
        def barcodes = request.JSON['barcodes'] as List<String>
        def baseItemBody = request.JSON as HashMap<String, Object>
        baseItemBody.remove('barcodes')
        def status = ProductStatus.findAll().find {it.name == baseItemBody?.status }
        def items = barcodes.collect {barcode ->
            def originalItem = productItemService.get(barcode)
            def newItem = baseItemBody.clone() as ProductItem

            if(!originalItem) {
                def notFound = new ProductItem(barcode: barcode)
                notFound.errors.rejectValue("barcode", "4000", "Product Item Not found")
                return notFound
            }

            if(ProductBase.exists(newItem.base.ident()))
                originalItem.base = ProductBase.get(newItem.base.ident())
            else
                originalItem.errors.rejectValue("base.id", "1000", "Base id not found")

            if(status)
                originalItem.status = status
            else
                originalItem.errors.rejectValue("status.id", "1000", "Status id not found")

            return originalItem.validate() ?
                    productItemService.update(barcode, newItem.assigned, newItem.base, status) :
                    productItemService.get(barcode)
        }.groupBy {it.isDirty()}

        render(
                view: 'save',
                status: items ? '200' : '204',
                model: [saved: items.get(true), errors: items.get(false)]
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
        def groupStatus = all.groupBy {it.status.id}

        render(
                view: 'index',
                model: [
                        products:       all,
                        stockInCount:   groupStatus?.get(IN_STOCK.id)?.size()  ?: 0,
                        stockOutCount:  groupStatus?.get(OUT_STOCK.id)?.size() ?: 0,
                        saleOutCount:   groupStatus?.get(OUT_SALE.id)?.size()  ?: 0
                ]
        )
    }


    @Transactional(readOnly = true)
    def show(String id){
        boolean exist = ProductItem.exists(id)
        if(!exist){
            String msgError = "No existe ningún producto con valor $id"
            render(view: '/error', status: '204', model: [messageError: msgError])
            return
        }

        super.show()
    }
}
