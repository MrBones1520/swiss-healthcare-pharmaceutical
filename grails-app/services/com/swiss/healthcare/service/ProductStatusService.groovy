package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductStatus
import grails.gorm.services.Service

@Service(ProductStatus)
interface ProductStatusService {

    ProductStatus get(Serializable id)

    ProductStatus save(ProductStatus arg)

    List<ProductStatus> findAll()



}