package com.swiss.healthcare.entity.inventory.products

import org.grails.datastore.gorm.GormEntity

class ProductStatus implements GormEntity<ProductStatus> {

    String name
    String description
    boolean enable = true
    Date dateCreated

    static hasMany = [items:ProductItem]

    static constraints = {
        name unique: true
        description blank: true, nullable: false
    }
    static mapping = {
        name updateable: false
    }

}