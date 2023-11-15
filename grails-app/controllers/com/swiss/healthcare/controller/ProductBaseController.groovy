package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.inventory.products.ProductBase
import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import org.springframework.http.HttpStatus

class ProductBaseController extends RestfulController<ProductBase> {

    static allowedMethods = [save:'POST', index: 'GET']

    ProductBaseController() {
        super(ProductBase)
    }

    @Transactional
    def save(){
        def product = ProductBase.get(getBarcode())

        if(product){
            respond([message: 'duplicated', code: '0332321', data: product], status: HttpStatus.FOUND.value())
        } else {
            super.save()
        }
    }

    @Transactional
    def delete(String id){
        def product = ProductBase.get(id)

        if(product){
            product.delete()
            respond([message: "Se elimino producto base", code: '002541', data: [barcode: product.barcode]], status: HttpStatus.FOUND.value())
        } else {
            respond([message: "No existe el producto base", code: '002541', data: [barcode: product.barcode]], status: HttpStatus.FOUND.value())
        }
    }

    @Override
    protected Object getObjectToBind() {
        request.JSON
    }

    protected String getBarcode(){
        request.JSON['barcode']
    }

}
