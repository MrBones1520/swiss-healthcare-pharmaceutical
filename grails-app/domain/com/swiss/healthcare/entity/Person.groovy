package com.swiss.healthcare.entity

import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp

class Person implements GormEntity<Person>{

    String firstName
    String secondName
    String fatherSName
    String motherSName
    Date birthday
    Timestamp dateCreated
    Timestamp lastUpdated

    static constraints = {
        firstName size: 1..30
        secondName nullable:true
        fatherSName size: 1..30
        motherSName size: 0..30
    }

}
