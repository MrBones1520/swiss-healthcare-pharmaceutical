package com.swiss.healthcare.domain;

import com.swiss.healthcare.entity.auth.User
import com.swiss.healthcare.entity.people.Person
import grails.testing.gorm.DomainUnitTest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class UserSpec extends Specification implements DomainUnitTest<User> {

    @Shared int id

    void 'test create new user'(){
        setup:
        new User(
                email: 'xyz@me.com',
                username: 'root',
                password: 'root',
                person: new Person()
        ).save()

        expect:
        User.count() == 1
    }

    void "test domain instance"() {
        setup:
        id = System.identityHashCode(domain)

        expect:
        domain != null
        domain.hashCode() == id

        when:
        domain.email = 'xyz@me.com'

        then:
        domain.email == 'xyz@me.com'
    }

    void "test we get a new domain"() {
        expect:
        domain != null
        domain.username == null
        System.identityHashCode(domain) != id
    }

}
