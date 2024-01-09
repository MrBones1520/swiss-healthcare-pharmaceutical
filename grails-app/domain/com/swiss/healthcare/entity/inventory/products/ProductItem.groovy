package com.swiss.healthcare.entity.inventory.products

import com.swiss.healthcare.entity.auth.User
import grails.rest.Resource
import org.grails.datastore.gorm.GormEntity

@Resource
class ProductItem implements GormEntity<ProductItem>{

    String barcode
    Date dateCreated

    static hasOne = [descriptor:ProductBase, status:ProductStatus, user:User]

    static constraints = {
        barcode unique: true, size: 7..25
        user nullable: true
    }

    static mapping = {
        id name: 'barcode', generator: 'assigned'
        descriptor updateable: false, lazy: false
    }

}
