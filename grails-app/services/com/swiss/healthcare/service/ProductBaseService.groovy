package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductBase
import grails.gorm.services.Service

@Service(ProductBase)
interface ProductBaseService {

    ProductBase save(ProductBase productBase)

    ProductBase get(Serializable id)

    List<ProductBase> findAll()

}
