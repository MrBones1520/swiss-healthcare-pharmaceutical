package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import grails.gorm.services.Service

@Service(ProductBase)
interface ProductBaseService {

    Long count()

    ProductBase save(ProductBase productBase)

    ProductBase get(Serializable id)

    List<ProductBase> findAll()

}
