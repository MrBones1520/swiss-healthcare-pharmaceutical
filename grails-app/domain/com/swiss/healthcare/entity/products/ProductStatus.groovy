package com.swiss.healthcare.entity.products

import grails.rest.Resource
import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp

@Resource
class ProductStatus implements GormEntity<ProductStatus> {

    String name
    String description
    boolean isActive = true
    Timestamp dateCreated
    Timestamp lastUpdated

    static constraints = {
        name unique: true
        description blank: true, nullable: false
        isActive column: 'is_active'
    }
    static mapping = {
        name updateable: false
    }

}