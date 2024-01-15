package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.auth.User
import com.swiss.healthcare.entity.people.Person
import com.swiss.healthcare.service.UserService
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class LoginControllerSpec extends Specification implements ControllerUnitTest<LoginController>{

    User user

    def setup(){
        user = new User(
                email: 'abcd@me.com', username: 'qotsa1520', password: '123456',
                person: new Person(firstName: 'testUser', fatherSName: 'stub', motherSName: 'stub', birthday: new Date())
        )
    }

    def 'login user successful'() {
        given:
        controller.userService = Stub(UserService){
            findByUsernameAndPassword(user.username, user.password) >> user
        }

        when:
        request.JSON = ['username': user.username, 'password': user.password]
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        controller.login()

        then:
        status == 202
        view == '/successful'
    }

    def 'login user denied'() {
        given:
        controller.userService = Stub(UserService){
            findByUsernameAndPassword('qotsa1520', '123456') >> new User(
                    email: 'abcd@me.com', username: 'qotsa1520', password: '123456',
                    person: new Person(firstName: 'testUser', fatherSName: 'stub', motherSName: 'stub', birthday: new Date())
            )
        }

        when:
        request.JSON = ['username': 'root', 'password': '1234565']
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        controller.login()

        then:
        status == 401
        view == '/rejection'
    }

}
