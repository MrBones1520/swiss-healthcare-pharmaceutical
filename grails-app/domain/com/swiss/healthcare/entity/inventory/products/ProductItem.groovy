package com.swiss.healthcare.entity.inventory.products

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

@Resource
@EqualsAndHashCode(includes = ['barcode', 'descriptor', 'status'])
@ToString(
        includes = ['barcode', 'descriptor', 'status'],
        includeFields = true,
        includePackage = false
)
class ProductItem implements GormEntity<ProductItem>{

    String barcode
    ProductBase descriptor
    ProductStatus status

    Date dateCreated

    static constraints = {
        barcode unique: true, size: 7..25
    }

    static mapping = {
        id name: 'barcode', generator: 'assigned'
        barcode updateable: false
    }

}
