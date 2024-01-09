package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.Sale
import com.swiss.healthcare.service.SaleService
import grails.rest.RestfulController

class SaleController extends RestfulController<Sale>{

    SaleService saleService

    SaleController() {
        super(Sale.class)
    }

    def index(){
        [sales: saleService.findAll()]
    }

}
