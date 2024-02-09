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
        def status = data ? '200' : '204'
        render([view: 'index', status: status, model: [products: data]])
    }

    def show(int id){
        ProductBase base = productBaseService.get(id)
        if(base) {
            render([view: 'show', status: '200', model: [productBase: base]])
            return
        }
        String error = !id ? "El id es invalido" : !base ? "El producto base no existe: ${id}": ''
        render([view: '/error', status:'409', model: [messageError: error]])
    }

    def save(){
        ProductBase base = request.JSON as ProductBase
        if(base.validate()){
            render(view: 'save', model: [base: base.save()])
            return
        }
        render(view: '/errors/_errors', model: [errors: base.errors])
    }

    def update(){
        ProductBase base = request.JSON as ProductBase
        if(base.validate()){
            render(view: 'update', model: [base: base.save()])
            return
        }
        render(view: '/errors/_errors', model: [errors: base.errors])
    }


}
