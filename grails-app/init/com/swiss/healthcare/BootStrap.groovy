package com.swiss.healthcare

import com.swiss.healthcare.entity.User
import grails.util.GrailsUtil
import groovy.util.logging.Log

@Log
class BootStrap {

    def init = { servletContext ->
        if(GrailsUtil.isDevelopmentEnv()) {
            new User()
        }
    }

    def destroy = {
    }

}