package com.swiss.healthcare.entity.auth

import com.swiss.healthcare.entity.inventory.Sale
import com.swiss.healthcare.entity.people.Person
import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

@Resource
@EqualsAndHashCode(includes = ['username', 'email', 'enabled'])
@ToString(includes = ['username', 'email'], includeNames = true, includePackage = false)
class User implements GormEntity<User>{

    String email
    String username
    String password
    List<Person> person
    boolean enabled = true

    Date dateCreated
    Date lastUpdated

    static constraints = {
        email email:true, unique:true
        username blank:true, unique:true
        password password:true, blank:true
    }

    static mapping = {
        password column: 'password'
        autowire true
    }

    static transients = ['springSecurityService']

    List<Sale> getSales(){
        Sale.findAll {user == this }
    }

    Set<SecurityRole> getAuthorities(){
        UserSecurityRole.findAllByUser(this) as List<UserSecurityRole>*.securityRole as Set<SecurityRole>
    }

    def beforeInsert(){
        encodePassword()
    }

    def beforeUpdate(){
        if(isDirty('password'))
            encodePassword()
    }

    boolean emptyRole(){
        getAuthorities()?.isEmpty()
    }

    protected void encodePassword(){
        password
    }

}