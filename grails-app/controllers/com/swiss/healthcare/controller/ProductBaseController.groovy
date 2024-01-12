package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.service.ProductBaseService
import grails.rest.RestfulController

class ProductBaseController extends RestfulController<ProductBase> {

    ProductBaseService productBaseService

    ProductBaseController() {
        super(ProductBase.class)
    }

    def index(){
        [products: productBaseService.findAll()]
    }

    def show(int id){
        [productBase: productBaseService.get(id)]
    }
}
