package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.service.ProductBaseService
import grails.gorm.transactions.Transactional
import grails.rest.RestfulController

class ProductBaseController extends RestfulController<ProductBase> {

    ProductBaseService productBaseService

    ProductBaseController() {
        super(ProductBase.class)
    }

    @Transactional(readOnly = true)
    def index(){
        def data = productBaseService.findAll()
        def status = data ? '200' : '204'

        render([view: 'index', status: status, model: [products: data]])
    }

    @Transactional(readOnly = true)
    def show(int id){
        def base = productBaseService.get(id)

        if(base) {
            render([view: 'show', status: '200', model: [productBase: base]])
            return
        }

        String error = !id ? "El id es invalido" : !base ? "El producto base no existe: ${id}": ''
        render([view: '/error', status:'409', model: [messageError: error]])
    }

    @Transactional
    def save(){
        def base = request.JSON as ProductBase

        if(base.validate()){
            render(view: 'save', model: [base: base.save()])
            return
        }

        render(view: '/errors/_errors', model: [errors: base.errors])
    }

    @Transactional
    def update(){
        def base = request.JSON as ProductBase

        if(base.validate()){
            render(view: 'update', model: [base: base.save()])
            return
        }

        render(view: '/errors/_errors', model: [errors: base.errors])
    }


}
