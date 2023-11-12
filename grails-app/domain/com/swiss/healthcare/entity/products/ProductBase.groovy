package com.swiss.healthcare.entity.products

import com.swiss.healthcare.ProductBaseController
import grails.rest.Resource
import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp

@Resource(superClass = ProductBaseController)
class ProductBase implements GormEntity<ProductBase> {

    String barcode
    String name
    String description
    Timestamp dateCreated
    Timestamp lastUpdated

    static constraints = {
        barcode unique: true, size: 7..25
        name size: 1..50
        description size: 0..150
    }

    static mapping = {
        id name: 'barcode', generator: 'assigned'
        barcode updateable: false
    }

}