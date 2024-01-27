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
        def notFounds = items.clone()

        if(params.containsKey("barcodes")) {
            def barcodes = params['barcodes'].toString().split(',').toList()
            items = items.findAll {it.barcode in barcodes}
            notFounds = notFounds
                    .findAll {!barcodes.contains(it.barcode) && it.status.id == OUT_STOCK.id}
            if(params.containsKey('baseId')){
                def baseId = params['baseId'].toString().toLong()
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
            inconOutSale            :   groupStatus?.get(OUT_SALE.id)?.size()  ?: 0,
            inconOutStock           :   groupStatus?.get(OUT_STOCK.id)?.size() ?: 0,
        ]
    }

    def search(){
        String value = params?.value
        def barcodes= params?.barcodes ?: [] as Set<String>
        def searchValues =  productItemService.searchLike(value)
        def statusOuter  = {it.status.id == OUT_STOCK.id}
        def checkBarcodes= {barcodes.contains(it.barcode)}
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
                status: value ? '200' : '204',
                model: [
                        searchValue             :   value,
                        items                   :   items,
                        listNotFound            :   notFounds,
                        inStockCount            :   productItemService.countByProductStatus(IN_STOCK.id),
                        outStockCount           :   productItemService.countByProductStatus(OUT_STOCK.id),
                        outSaleCount            :   productItemService.countByProductStatus(OUT_SALE.id),
                        inconOutSale            :   groupStatus?.get(OUT_SALE.id)?.size() ?: 0,
                        inconOutStock           :   groupStatus?.get(OUT_STOCK.id)?.size() ?: 0,
                ]
        )
    }

    def inStock(){
        def barcodes = request.JSON?.barcodes
        if(',' in barcodes)
            barcodes = barcodes.split(',')
        def barcodeExist = barcodes
                .findAll {ProductItem.exists(it)}
                .collect {
                    def item = productItemService.get(it)
                    item.status = ProductStatus.IN_STOCK
                    item.assigned = null
                }
                .collect {it.save(flush: true)}

        if(!barcodeExist){
            request.status = '204'
            return
        }

        ['items': barcodeExist]
    }

}
