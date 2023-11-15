package com.swiss.healthcare.entity.inventory.products

import com.swiss.healthcare.entity.auth.User
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp

@ToString(includeFields = true, includePackage = false, excludes = ['dateCreated', 'lastUpdated'])
class ProductItem implements GormEntity<ProductItem>{

    String uid
    ProductBase descriptor
    ProductStatus status
    User user
    Timestamp dateCreated
    Timestamp lastUpdated

    static mapping = {
        id name: 'uid', generator: 'uuid'
    }

}
