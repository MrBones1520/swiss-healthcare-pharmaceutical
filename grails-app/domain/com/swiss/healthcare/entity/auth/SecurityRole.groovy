package com.swiss.healthcare.entity.auth

import org.grails.datastore.gorm.GormEntity

class SecurityRole implements GormEntity<SecurityRole>{

    String authority

    static constraints = {
        authority blank: false, unique: true
    }

    static mapping = {
        cache true
    }

}
