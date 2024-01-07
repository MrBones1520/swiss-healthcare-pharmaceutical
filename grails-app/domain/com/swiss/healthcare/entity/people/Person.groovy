package com.swiss.healthcare.entity.people

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.apache.tools.ant.util.DateUtils
import org.grails.datastore.gorm.GormEntity

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

    static constraints = {
        firstName size: 1..30
        secondName nullable:true
        fatherSName size: 1..30
        motherSName size: 0..30
    }

}
