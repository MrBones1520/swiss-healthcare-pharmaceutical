package com.swiss.healthcare.controller

import com.swiss.healthcare.entity.auth.User
import com.swiss.healthcare.service.UserService
import grails.rest.RestfulController

class UserController extends RestfulController<User>{

    UserService userService

    UserController() {
        super(User.class)
    }

    def index(){ [users: userService.findAll()] }

}
