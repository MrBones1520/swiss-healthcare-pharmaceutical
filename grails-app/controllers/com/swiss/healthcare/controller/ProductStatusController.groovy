package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductStatus
import com.swiss.healthcare.service.ProductStatusService
import grails.rest.RestfulController
import groovy.util.logging.Log

@Log
class ProductStatusController extends RestfulController<ProductStatus>{

    ProductStatusService productStatusService

    ProductStatusController() {
        super(ProductStatus.class)
    }

    def index(){
        [products: productStatusService.findAll()]
    }


}

