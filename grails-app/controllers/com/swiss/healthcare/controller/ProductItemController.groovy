package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import com.swiss.healthcare.service.ProductBaseService
import com.swiss.healthcare.service.ProductItemService
import grails.rest.RestfulController
import groovy.util.logging.Log

@Log
class ProductItemController extends RestfulController<ProductItem>{

    ProductItemService productItemService

    ProductBaseService productBaseService

    ProductItemController() {
        super(ProductItem.class)
    }

    def index(){
        [products:  productItemService.findAll(),
         stockInCount:   productItemService.listAllInStock().size(),
         stockOutCount:  productItemService.listAllOutStock().size(),
         saleOutCount:   productItemService.listAllOutSale().size()]
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
        def id0 = params['id'].toString().toLong()

        [listProductBase: ProductItem.where { base.id == id0 }.list()]
    }

}
