package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import com.swiss.healthcare.service.ProductItemService
import grails.artefact.Controller
import grails.gorm.transactions.Transactional

class AuditoryController implements Controller {

    ProductItemService productItemService

    def index(){
        def items = productItemService.findAll()
        def notFounds = items.clone()
        if(params.containsKey("barcodes")) {
            def barcodes = params['barcodes'].toString().split(',').toList()
            items = items.findAll {barcodes.contains(it.barcode)}
            notFounds = notFounds.findAll {  !barcodes.contains(it.barcode)}
            if(params.containsKey('baseId')){
                def baseId = params['baseId'].toString().toLong()
                def filterBaseId = {ProductItem it -> it.base.id == baseId}
                items = items.findAll(filterBaseId)
                notFounds = notFounds.findAll(filterBaseId)
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

    @Transactional(readOnly = true)
    def search(){
        def value = params?.value
        render(
                view: 'search',
                status: value ? '200' : '204',
                model: [searchValue: value, items: productItemService.searchAllContains(value)]
        )
    }

}
