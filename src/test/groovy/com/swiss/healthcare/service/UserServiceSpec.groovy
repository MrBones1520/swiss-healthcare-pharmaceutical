package com.swiss.healthcare.service

import com.swiss.healthcare.entity.auth.User
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class UserServiceSpec extends Specification implements ServiceUnitTest<UserService>{

    void "test method find USer by username And password"(){
        setup:
        service.findByUsernameAndPassword("", "") >> new User()


        expect:
        service.findByUsernameAndPassword("", "")
    }

}
