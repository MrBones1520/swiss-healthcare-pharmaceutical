package com.swiss.healthcare.controller

import com.swiss.healthcare.service.UserService
import grails.artefact.Controller

class LoginController implements Controller {

    UserService userService

    def login() {
        def username0 = request.getJSON()['username']
        def password0 = request.getJSON()['password']
        def user = userService.findByUsernameAndPassword(username0, password0)

        render(
                view:   user ? '/accessSuccessful' : '/accessRejection',
                status: user ? '226' : '401',
                model: [
                        msg         : user ? 'Access done' : 'Access denied',
                        statusAccess: false
                ]
        )
    }

}
