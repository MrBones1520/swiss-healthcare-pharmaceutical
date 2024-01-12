package com.swiss.healthcare.controller

import com.swiss.healthcare.service.UserService
import grails.artefact.Controller

class LoginController implements Controller {

    UserService userService

    def login(){
        def username = request.getJSON()['username']
        def password = request.getJSON()['password']
        def user = userService.findByUsernameAndPassword(username, password)


        render('status': user ? '100' : '401', contentType: "application/json") {
            accesed(message: user ? 'Access done' : 'Access denied', access: user.asBoolean())
        }
    }

}
