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

    @Where({status.id == ProductStatus.IN_STOCK.id})
    List<ProductItem> listAllInStock()

    @Where({status.id == ProductStatus.OUT_STOCK.id})
    List<ProductItem> listAllOutStock()

    @Where({status.id == ProductStatus.OUT_SALE.id})
    List<ProductItem> listAllOutSale()

}