package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.people.Person
import grails.rest.RestfulController

class PersonController extends RestfulController<Person> {

    PersonController() {
        super(Person.class)
    }

}
