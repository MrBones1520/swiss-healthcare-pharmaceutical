package com.swiss.healthcare.entity.auth

import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.people.Person
import org.grails.datastore.gorm.GormEntity

class User implements GormEntity<User>{

    String email
    String username
    String password
    boolean enabled = true
    Person person
    Date dateCreated
    Date lastUpdated

    static hasMany = [items:ProductItem]

    static belongsTo = [person: Person]

    static constraints = {
        email email:true, unique:true
        username blank:true, unique:true
        password password:true, blank:true
    }

    static mapping = {
        password column: 'password'
    }

}