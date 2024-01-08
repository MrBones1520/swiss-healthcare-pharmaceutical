package com.swiss.healthcare.entity.inventory.products

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp

@Resource
@EqualsAndHashCode(includes = ['name', 'description'])
@ToString(
        includes = ['name', 'description', 'enable'],
        includeNames = true,
        includePackage = false
)
class ProductStatus implements GormEntity<ProductStatus> {

    String name
    String description
    boolean enable = true

    Date dateCreated

    static constraints = {
        name unique: true
        description blank: true, nullable: false
    }
    static mapping = {
        name updateable: false
    }

}