package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import com.swiss.healthcare.service.ProductItemService
import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import groovy.util.logging.Log

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
            def newItem = baseItemBody.clone() as ProductItem

            if(!originalItem) {
                def notFound = new ProductItem(barcode: it)
                notFound.errors.rejectValue("barcode", "4000", "Product Item Not found")
                return notFound
            }

            if(ProductBase.exists(newItem.base.ident()))
                originalItem.base = ProductBase.get(newItem.base.ident())
            else
                originalItem.errors.rejectValue("base.id", "1000", "Base id not found")

            if(!ProductStatus.exists(newItem.status.ident()))
                originalItem.status = ProductStatus.get(newItem.status.ident())
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
            stockInCount:   productItemService.countByProductStatus(ProductStatus.IN_STOCK),
            stockOutCount:  productItemService.countByProductStatus(ProductStatus.OUT_STOCK),
            saleOutCount:   productItemService.countByProductStatus(ProductStatus.OUT_SALE)
        ]
    }

    def status(){
        def status0 = params.get('status').toString()
        def values0 = new ArrayList<ProductItem>()

        if('stockIn'.equalsIgnoreCase(status0))
            values0 = productItemService.listAllInStock()
        if('stockOut'.equalsIgnoreCase(status0))
            values0 = productItemService.listAllOutStock()
        if('saleOut'.equalsIgnoreCase(status0))
            values0 = productItemService.listAllOutSale()

        [statusName: status0, values: values0]
    }

    def base(){
        def productBaseId = params['id'].toString().toLong()
        def all = ProductItem.where { base.id == productBaseId }.list()

        [
                view: 'index',
                model: [
                    products:       all,
                    stockInCount:   all.findAll {it.status.id == ProductStatus.IN_STOCK.id }.size(),
                    stockOutCount:  all.findAll {it.status.id == ProductStatus.OUT_STOCK.id }.size(),
                    saleOutCount:   all.findAll {it.status.id == ProductStatus.OUT_SALE.id }.size()
                ]
        ]
    }

}
