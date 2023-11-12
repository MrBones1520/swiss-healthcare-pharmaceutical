package com.swiss.healthcare

import com.swiss.healthcare.entity.products.ProductBase
import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import org.grails.datastore.gorm.GormEntity
import org.springframework.http.HttpStatus

class ProductBaseController extends RestfulController<ProductBase> {

    static allowedMethods = [save:'POST', index: 'GET']

    ProductBaseController(Class<ProductBase> domainClass) {
        super(domainClass, false)
    }

    ProductBaseController(Class<ProductBase> domainClass, boolean readOnly) {
        super(domainClass, readOnly)
    }

    @Transactional
    def save(){
        def exist = resource.find {it.barcode == request.JSON['barcode']}
        if(exist){
            respond exist, status: HttpStatus.FOUND.value()
        } else {
            super.save()
        }
    }

    @Override
    protected Object getObjectToBind() {
        return request.JSON
    }

}
