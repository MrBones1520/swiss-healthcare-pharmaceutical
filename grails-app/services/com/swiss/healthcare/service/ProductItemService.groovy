package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import grails.gorm.services.Service
import grails.gorm.services.Where

@Service(ProductItem)
abstract class ProductItemService {
    abstract ProductItem save(ProductItem productItem)

    abstract ProductItem get(String barcode)

    abstract ProductItem update(String id, String assigned, ProductBase base, ProductStatus status)

    @Where({enabled == true})
    abstract List<ProductItem> findAll()

    @Where({status.id == id})
    abstract List<ProductItem> findAllByProductStatus(Serializable id)

    @Where({base.id == id})
    abstract List<ProductItem> findAllByProductBase(Serializable id)

    @Where({status.id == productStatus.id})
    abstract int countByProductStatus(ProductStatus productStatus)

    List<ProductItem> searchAllContains(String value){
        def values = value.split(',')
        return ProductItem.where {
            barcode in values || assigned in values || base.name in values || base.description in values
        }.list()
    }

}