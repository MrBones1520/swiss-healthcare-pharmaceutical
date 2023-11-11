package com.swiss.healthcare

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ProductStatusSpec extends Specification implements DomainUnitTest<ProductStatus> {

     void "test domain constraints"() {
        when:
        ProductStatus domain = new ProductStatus()

        then:
        domain.validate()
     }
}
