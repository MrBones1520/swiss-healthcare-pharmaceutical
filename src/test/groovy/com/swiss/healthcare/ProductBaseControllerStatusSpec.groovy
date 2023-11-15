package com.swiss.healthcare

import com.swiss.healthcare.entity.inventory.products.ProductStatus
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ProductBaseControllerStatusSpec extends Specification implements DomainUnitTest<ProductStatus> {

     void "test domain constraints"() {
        when:
        ProductStatus domain = new ProductStatus()

        then:
        domain.validate()
     }
}
