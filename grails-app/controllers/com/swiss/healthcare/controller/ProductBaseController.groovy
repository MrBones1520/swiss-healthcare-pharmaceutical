package com.swiss.healthcare.controller;

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.service.ProductBaseService
import grails.rest.RestfulController

class ProductBaseController extends RestfulController<ProductBase> {

    ProductBaseService productBaseService

    ProductBaseController() {
        super(ProductBase.class)
    }

    def index(){
        [size: productBaseService.count(), products: productBaseService.findAll()]
    }


}
