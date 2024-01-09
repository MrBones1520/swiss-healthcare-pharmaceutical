package com.swiss.healthcare.entity.inventory.products

import org.grails.datastore.gorm.GormEntity

class ProductStatus implements GormEntity<ProductStatus> {

    static final ProductStatus IN_STOCK = new ProductStatus('IN_STOCK', "Product in stock")
    static final ProductStatus OUT_STOCK = new ProductStatus('OUT_STOCK', "Product out stock")
    static final ProductStatus OUT_SALE = new ProductStatus('OUT_SALE', "Product sale")

    String name
    String description
    Date dateCreated

    ProductStatus(String name, String description) {
        this.name = name
        this.description = description
    }
    static hasMany = [items:ProductItem]

    static constraints = {
        name unique: true
        description blank: true, nullable: false
    }
    static mapping = {
        items lazy:false
        name updateable: false
    }

}