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
        def notFounds= items.clone()

        if(params.containsKey("barcodes")) {
            def barcodes = params['barcodes'].toString().split(',').toList()
            items = items.findAll {it.barcode in barcodes}
            notFounds = notFounds
                    .findAll {!barcodes.contains(it.barcode)}
                    .findAll {it.status.id != 2}
            if(params.containsKey('baseId')){
                def baseId = params['baseId'].toString().toLong()
                items = items.findAll { it.base.id == baseId}
                notFounds = notFounds.findAll {it.base.id == baseId}
            }
        }

        def groupStatus = items.groupBy {it.status.id}
        [
            inStockCount            :   productItemService.countByProductStatus(1),
            outStockCount           :   productItemService.countByProductStatus(2),
            outSaleCount            :   productItemService.countByProductStatus(3),
            listNotFound            :   notFounds,
            allItems                :   items,
            inconOutSale            :   groupStatus.getOrDefault(2, []).size(),
            inconOutStock           :   groupStatus.getOrDefault(3, []).size(),
        ]
    }

    def search(){
        def value = params?.value
        def barcodes = params?.barcodes ?: [] as Set<String>
        def searchValues = productItemService.searchLike(value)
        def notFounds   = searchValues
                .findAll {!barcodes.contains(it.barcode)}
                .findAll {it.status.id != 2}
        def groupStatus = searchValues.groupBy {it.status.id}
        render(
                view:   'search',
                status: value ? '200' : '204',
                model: [
                        searchValue             :   value,
                        items                   :   searchValues,
                        listNotFound            :   notFounds,
                        inStockCount            :   productItemService.countByProductStatus(1),
                        outStockCount           :   productItemService.countByProductStatus(2),
                        outSaleCount            :   productItemService.countByProductStatus(3),
                        inconOutSale            :   groupStatus.getOrDefault(2, []).size(),
                        inconOutStock           :   groupStatus.getOrDefault(3, []).size(),
                ]
        )
    }

    def inStock(){
        if('')
            render()
        def barcodes = request.JSON['barcodes']
        if(',' in barcodes)
            barcodes = barcodes.split(',')
        def barcodeExist = barcodes.
                findAll {ProductItem.exists(it)}.
                collect {
                    def item = productItemService.get(it)
                    item.status = ProductStatus.IN_STOCK
                    item.assigned = null
                }.
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
