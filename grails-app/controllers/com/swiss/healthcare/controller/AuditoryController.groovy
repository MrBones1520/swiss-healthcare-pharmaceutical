package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import com.swiss.healthcare.service.ProductItemService
import grails.artefact.Controller
import grails.gorm.transactions.Transactional

import static com.swiss.healthcare.ProductStatusE.*

@Transactional(readOnly = true)
class AuditoryController implements Controller {

    ProductItemService productItemService

    def index(){
        def items = productItemService.findAll()
        def notFounds = items
        if(params.containsKey("barcodes")) {
            def barcodes = params['barcodes'].toString().split(',').toList()
            items = items.findAll {it.barcode in barcodes}
            notFounds = notFounds.findAll {it.barcode !in barcodes && it.status.id == OUT_STOCK.id}
            if(params.containsKey('baseId')){
                def baseId = params['baseId'] as Long
                def findBaseId = {it.base.id == baseId}
                items = items.findAll(findBaseId)
                notFounds = notFounds.findAll(findBaseId)
            }
        }

        def groupStatus = items.groupBy {it.status.id as Integer}
        [
            inStockCount            :   productItemService.countByProductStatus(IN_STOCK.id),
            outStockCount           :   productItemService.countByProductStatus(OUT_STOCK.id),
            outSaleCount            :   productItemService.countByProductStatus(OUT_SALE.id),
            listNotFound            :   notFounds,
            allItems                :   items,
            inconOutSale            :   groupStatus?[OUT_SALE.id]?.size()  ?: 0,
            inconOutStock           :   groupStatus?[OUT_STOCK.id]?.size() ?: 0,
        ]
    }

    def search(){
        String value = params?.value
        def barcodes= params?.barcodes ?: [] as Set<String>
        def searchValues = productItemService.searchLike(value)
        def statusOuter  = {it.status.id == OUT_STOCK.id}
        def checkBarcodes= {it.barcode in barcodes}
        def groupByStatus = {it.status.id as Integer}
        def items= searchValues
        def notFounds= searchValues.findAll(statusOuter)
        def groupStatus= items.groupBy(groupByStatus)

        if(barcodes){
            items       = items.findAll(checkBarcodes)
            notFounds   = items.findAll {!checkBarcodes(it) && statusOuter(it)}
            groupStatus = items.groupBy(groupByStatus)
        }
        render(
                view:   'search',
                status:  value ? '200' : '204',
                model:   [
                        searchValue             :   value,
                        items                   :   items,
                        listNotFound            :   notFounds,
                        inStockCount            :   productItemService.countByProductStatus(IN_STOCK.id),
                        outStockCount           :   productItemService.countByProductStatus(OUT_STOCK.id),
                        outSaleCount            :   productItemService.countByProductStatus(OUT_SALE.id),
                        inconOutSale            :   groupStatus?[OUT_SALE.id]?.size() ?: 0,
                        inconOutStock           :   groupStatus?[OUT_STOCK.id]?.size() ?: 0,
                ]
        )
    }

    def inStock(){
        def barcodes = request.JSON?.barcodes ?: ''
        if(',' !in barcodes)
            return
        barcodes = barcodes.split(',') as List<String>
        def barcodeExist = barcodes
                .findAll(ProductItem::exists)
                .collect(productItemService::get)
                .collect { item ->
                    item.status = ProductStatus.get(IN_STOCK.id)
                    item.assigned = null
                    item
                }.collect {it.save(flush: true)}

        if(!barcodeExist)
            render status: '204'

        ['items': barcodeExist]
    }

}
