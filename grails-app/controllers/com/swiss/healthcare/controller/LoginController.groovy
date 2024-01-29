package com.swiss.healthcare.controller

import com.swiss.healthcare.service.UserService
import grails.artefact.Controller

class LoginController implements Controller {

    UserService userService

    def login() {
        String username0 = request.JSON?['username']
        String password0 = request.JSON?['password']
        def user = userService.findByUsernameAndPassword(username0, password0)

        return render(
                view:   user.username ? 'successful' : 'rejection',
                status: user.username ? '202' : '401',
        )
    }
}
