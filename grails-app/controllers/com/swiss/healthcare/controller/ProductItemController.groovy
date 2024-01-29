package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import com.swiss.healthcare.service.ProductItemService
import grails.gorm.transactions.Transactional
import grails.rest.RestfulController

import static com.swiss.healthcare.ProductStatusE.*

class ProductItemController extends RestfulController<ProductItem>{

    ProductItemService productItemService

    ProductItemController() {
        super(ProductItem.class)
    }

    @Transactional
    def save(){
        def barcodes = request.JSON?['barcodes'] as List<String>
        def baseItemBody = request.JSON as HashMap<String, Object>
        baseItemBody.remove('barcodes')

        def items = barcodes.collect {
            def copy = baseItemBody
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
        }.groupBy { it.isAttached()  }

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

        def items = barcodes.collect {
            def originalItem = productItemService.get(it)
            def newItem = baseItemBody as ProductItem

            if(!originalItem) {
                def notFound = new ProductItem(barcode: it)
                notFound.errors.rejectValue("barcode", "4000", "Product Item Not found")
                return notFound
            }

            if(ProductBase.exists(newItem.base.id))
                originalItem.base = ProductBase.get(newItem.base.id)
            else
                originalItem.errors.rejectValue("base.id", "1000", "Base id not found")

            if(!ProductStatus.exists(newItem.status.id))
                originalItem.status = ProductStatus.get(newItem.status.id)
            else
                originalItem.errors.rejectValue("status.id", "1000", "Status id not found")

            return originalItem.validate() ?
                    productItemService.update(it, newItem.assigned, newItem.base, newItem.status) :
                    productItemService.get(it)
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
        def status0 = params['status'].toString().toInteger()

        if(!ProductStatus.exists(status0))
            return render(view: '/error')

        [values: productItemService.findAllByProductStatus(status0)]
    }

    @Transactional(readOnly = true)
    def base(){
        def productBaseId = params?['id']?.toString()?.toInteger()

        if(!ProductBase.exists(productBaseId))
            return render(view: 'error')

        def all = productItemService.findAllByProductBase(productBaseId)
        def groupStatus = all.groupBy {it.status.id}

        render(
                view: 'index',
                model: [
                        products:       all,
                        stockInCount:   groupStatus.getOrDefault(IN_STOCK.id, []).size(),
                        stockOutCount:  groupStatus.getOrDefault(OUT_STOCK.id, []).size(),
                        saleOutCount:   groupStatus.getOrDefault(OUT_SALE.id, []).size()
                ]
        )
    }

}
