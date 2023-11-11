package com.swiss.healthcare

import grails.rest.Resource
import org.grails.datastore.gorm.GormEntity

@Resource
class Product implements GormEntity<Product> {

    String barcode

    String name

    String description

    long idStatus

    static constraints = {
        name range: 1..50
        description nullable: true, range: 1..150
    }

    static mapping = {
        id name: 'barcode'
    }

}