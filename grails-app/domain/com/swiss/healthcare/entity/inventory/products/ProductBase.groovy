package com.swiss.healthcare.entity.inventory.products

import org.grails.datastore.gorm.GormEntity

class ProductBase implements GormEntity<ProductBase> {

    String name
    String description
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name unique:true, size: 1..50
        description size: 0..150
    }

}