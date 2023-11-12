package com.swiss.healthcare.entity.products

import com.swiss.healthcare.entity.User
import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp

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
