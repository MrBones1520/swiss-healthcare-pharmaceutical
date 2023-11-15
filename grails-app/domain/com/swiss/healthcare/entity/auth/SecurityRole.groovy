package com.swiss.healthcare.entity.auth

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class SecurityRole implements GormEntity<SecurityRole>{

    String authority

    static constraints = {
        authority blank: false, unique: true
    }

    static mapping = {
        cache true
    }

}
