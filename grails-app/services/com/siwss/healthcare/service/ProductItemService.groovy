package com.siwss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductItem
import grails.gorm.services.Service

@Service(ProductItem)
interface ProductItemService {

    ProductItem save(ProductItem productItem)

    ProductItem get(Serializable id)

}