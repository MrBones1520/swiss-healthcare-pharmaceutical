package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import grails.gorm.services.Service
import grails.gorm.services.Where

@Service(ProductItem)
interface ProductItemService {

    ProductItem save(ProductItem productItem)

    ProductItem get(String barcode)

    List<ProductItem> findAll()

    @Where({status == ProductStatus.IN_STOCK})
    List<ProductItem> listAllInStock()

    @Where({status == ProductStatus.OUT_STOCK})
    List<ProductItem> listAllOutStock()

    @Where({status == ProductStatus.OUT_SALE})
    List<ProductItem> listAllOutSale()

}