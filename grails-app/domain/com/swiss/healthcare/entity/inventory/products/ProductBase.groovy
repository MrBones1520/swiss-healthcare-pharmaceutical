package com.swiss.healthcare.entity.inventory.products

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

@Resource
@EqualsAndHashCode(includes = ['barcode', 'name', 'description'])
@ToString(includes = ['barcode', 'name', 'description'], includeNames = true, includePackage = false)
class ProductBase implements GormEntity<ProductBase> {

    String barcode
    String name
    String description

    static constraints = {
        barcode unique: true, size: 7..25
        name unique:true, size: 1..50
        description size: 0..150
    }

    static mapping = {
        id name: 'barcode', generator: 'assigned'
        barcode updateable: false
    }


}