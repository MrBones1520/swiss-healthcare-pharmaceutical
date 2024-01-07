package com.siwss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import grails.gorm.services.Service

@Service(ProductBase)
interface ProductBaseService {

    ProductBase save(ProductBase productBase)

    ProductBase get(String barcode)

}
