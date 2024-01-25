package com.swiss.healthcare.entity.inventory.products

import org.grails.datastore.gorm.GormEntity


class ProductItem implements GormEntity<ProductItem>{

    String barcode
    ProductBase base
    ProductStatus status
    String assigned
    Date dateCreated
    Date lastUpdated

    boolean enabled = true

    static transients = ['productStatus']

    static constraints = {
        barcode unique: true, size: 7..25
        assigned nullable: true
        dateCreated updatable: false
        status inList: ProductStatus.values()*.name
    }

    static mapping = {
        id name: 'barcode', generator: 'assigned'
        base updateable: true, lazy: false, fetch: 'join'
        cache false
        sort lastUpdated: 'desc'
    }

}
