package com.swiss.healthcare

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class ProductBaseControllerStatusControllerSpec extends Specification implements ControllerUnitTest<ProductStatusController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
