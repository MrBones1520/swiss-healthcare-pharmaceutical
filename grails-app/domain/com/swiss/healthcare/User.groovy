package com.swiss.healthcare

import grails.rest.Resource
import org.grails.datastore.gorm.GormEntity

@Resource
class User implements GormEntity<User> {

    String name

    boolean isAdmin = false

    static constraints = {
        name blank:false, nullable:false
    }

}