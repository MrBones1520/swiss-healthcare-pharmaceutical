package com.swiss.healthcare.service

import com.swiss.healthcare.entity.auth.User
import grails.gorm.services.Service

@Service(User)
interface UserService {

    User save(User user)

    User get(Serializable id)

    List<User> findAll()

    User findByUsernameAndPassword(String username, String password)

}