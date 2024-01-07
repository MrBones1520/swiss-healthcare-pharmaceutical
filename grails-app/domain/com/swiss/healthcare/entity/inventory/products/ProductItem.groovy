package com.swiss.healthcare.entity.inventory.products

import grails.rest.Resource
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

@Resource
@ToString(includeFields = true, includePackage = false)
class ProductItem implements GormEntity<ProductItem>{

    String uid
    ProductBase descriptor
    ProductStatus status

    static mapping = {
        id name: 'uid'
    }

}
