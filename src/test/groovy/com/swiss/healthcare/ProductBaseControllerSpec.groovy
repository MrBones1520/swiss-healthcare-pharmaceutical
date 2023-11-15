package com.swiss.healthcare

import com.swiss.healthcare.entity.inventory.products.ProductBase
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ProductBaseControllerSpec extends Specification implements DomainUnitTest<ProductBase> {

     void "test domain constraints"() {
        when:
        ProductBase domain = new ProductBase()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
