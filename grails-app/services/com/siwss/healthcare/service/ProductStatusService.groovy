package com.siwss.healthcare.service

import com.swiss.healthcare.entity.auth.User
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import grails.gorm.services.Service

@Service(ProductStatus)
interface ProductStatusService {

    ProductStatus save(ProductStatus arg)

    ProductStatus findByUser(User user)

}