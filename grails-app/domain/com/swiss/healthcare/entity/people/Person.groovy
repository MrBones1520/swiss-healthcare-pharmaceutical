package com.swiss.healthcare.entity.people

import org.grails.datastore.gorm.GormEntity

class Person implements GormEntity<Person>{

    String firstName
    String secondName
    String fatherSName
    String motherSName
    Date birthday

    Date dateCreated

    static constraints = {
        firstName size: 1..30
        secondName nullable:true
        fatherSName size: 1..30
        motherSName size: 0..30
    }

}
