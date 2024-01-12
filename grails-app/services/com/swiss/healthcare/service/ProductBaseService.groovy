package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductBase
import grails.gorm.services.Service
import grails.gorm.services.Where

@Service(ProductBase)
interface ProductBaseService {

    ProductBase save(ProductBase productBase)

    ProductBase get(Serializable id)

    @Where({enabled == true})
    List<ProductBase> findAll()

}
