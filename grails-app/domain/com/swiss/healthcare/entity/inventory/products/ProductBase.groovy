package com.swiss.healthcare.entity.inventory.products

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

@Resource
@EqualsAndHashCode(includes = ['name', 'description'])
@ToString(
        includes = ['name', 'description'],
        includeNames = true,
        includePackage = false
)
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