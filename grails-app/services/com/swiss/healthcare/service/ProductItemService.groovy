package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import grails.gorm.services.Service
import grails.gorm.services.Where
import groovy.transform.PackageScope

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

    @Where({status.id == id})
    abstract int countByProductStatus(int id)

    List<ProductItem> searchLike(String value){
        value.contains(',') ? value.split(',').collect(this::callCriteria).flatten() : callCriteria(value)
    }

   @PackageScope
   def callCriteria(String value){
        ProductItem.createCriteria().list {
            or {
                like("barcode", "%$value%")
                ilike("assigned", "%$value%")
                base {
                    or{
                        ilike("name", "%$value%")
                        ilike("description", "%$it%")
                    }
                }
            }
        } as List<ProductItem>
   }

}