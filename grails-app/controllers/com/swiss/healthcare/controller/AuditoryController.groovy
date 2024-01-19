package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import com.swiss.healthcare.service.ProductItemService
import grails.artefact.Controller
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
class AuditoryController implements Controller {

    ProductItemService productItemService

    def index(){
        def items = productItemService.findAll()
        def notFounds= []

        if(params.containsKey("barcodes")) {
            def barcodes = params['barcodes'].toString().split(',').toList()
            items = items.findAll {it.barcode in barcodes}
            notFounds = items
                    .findAll {!barcodes.contains(it.barcode)}
                    .findAll {it.status.id == ProductStatus.IN_STOCK.id}
            if(params.containsKey('baseId')){
                def baseId = params['baseId'].toString().toLong()
                items = items.findAll { it.base.id == baseId}
                notFounds = notFounds.findAll {it.base.id == baseId}
            }
        }

        def groupStatus = items.groupBy {it.status.id}
        [
            inStockCount            :   productItemService.countByProductStatus(ProductStatus.IN_STOCK),
            outStockCount           :   productItemService.countByProductStatus(ProductStatus.OUT_STOCK),
            outSaleCount            :   productItemService.countByProductStatus(ProductStatus.OUT_SALE),
            listNotFound            :   notFounds,
            allItems                :   items,
            inconOutSale            :   groupStatus.getOrDefault(ProductStatus.OUT_SALE.id, []).size(),
            inconOutStock           :   groupStatus.getOrDefault(ProductStatus.OUT_STOCK.id, []).size(),
        ]
    }

    def search(){
        def value = params?.value
        render(
                view: 'search',
                status: value ? '200' : '204',
                model: [searchValue: value, items: productItemService.searchLike(value)]
        )
    }

    def inStock(){
        if('')
            render()
        def barcodes = request.JSON['barcodes']
        if(',' in barcodes)
            barcodes = barcodes.split(',')
        def barcodeExist = barcodes.
                findAll { ProductItem.exists(it)}.
                collect {productItemService.get(it)}.
                collect {it.status = ProductStatus.IN_STOCK}.
                collect {it.save(flush: true)}

        if(!barcodeExist){
            request.status = '204'
            return
        }
        [
            'items': barcodeExist
        ]
    }

}
