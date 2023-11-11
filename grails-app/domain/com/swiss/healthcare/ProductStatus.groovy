package com.swiss.healthcare

import org.grails.datastore.gorm.GormEntity

class ProductStatus implements GormEntity<ProductStatus>{

    String name

    String description

    boolean isActive = true

    static constraints = {
        name unique: true
        description blank: true, nullable: false
        isActive column: 'is_active'
    }

}