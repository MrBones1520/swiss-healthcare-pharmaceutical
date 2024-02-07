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
        def data = productBaseService.findAll()
        render([view: 'index', status: data ? '200' : '204', model: [products: data]])
    }

    def show(int id){
        def base = productBaseService.get(id)

        if(base){
            render([view: 'show', status: '200', model: [productBase: base]])
            return
        }
            String error = !id ? "El id es invalido" : !base ? "El producto base no existe: ${id}": ''
            render([view: '/error', status:'409', model: [messageError: error]])
    }
}
