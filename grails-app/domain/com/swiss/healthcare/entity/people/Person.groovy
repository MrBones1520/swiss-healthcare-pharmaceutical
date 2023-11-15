package com.swiss.healthcare.entity.people

import com.swiss.healthcare.entity.auth.User
import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp

@Resource
@EqualsAndHashCode(includes = ['firstName', 'secondName', 'fatherSName', 'motherSName', 'birthday'])
@ToString(
    includes = ['firstName', 'secondName', 'fatherSName', 'motherSName'],
    includeNames = true,
    includePackage = false
)
class Person implements GormEntity<Person>{

    String firstName
    String secondName
    String fatherSName
    String motherSName
    Date birthday
    Timestamp dateCreated
    Timestamp lastUpdated

    static hasMany = [users: User]

    static constraints = {
        firstName size: 1..30
        secondName nullable:true
        fatherSName size: 1..30
        motherSName size: 0..30
    }

}
