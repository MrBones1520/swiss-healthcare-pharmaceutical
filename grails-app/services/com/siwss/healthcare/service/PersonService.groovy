package com.siwss.healthcare.service

import com.swiss.healthcare.entity.people.Person
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Service(Person)
interface PersonService {

    Person save(Person person)

}
