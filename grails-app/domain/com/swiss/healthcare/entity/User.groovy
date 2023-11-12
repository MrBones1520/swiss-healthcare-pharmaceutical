package com.swiss.healthcare.entity


import grails.rest.Resource
import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp

@Resource
class User implements GormEntity<User> {

    String email
    Timestamp dateCreated
    Timestamp lastUpdated

    static belongsTo = [person: Person]

    static constraints = {
        email email:true, unique:true
    }

}