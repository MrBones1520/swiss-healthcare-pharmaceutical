package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import grails.gorm.services.Service
import grails.gorm.services.Where
import groovy.transform.PackageScope

import static com.swiss.healthcare.ProductStatusE.IN_STOCK

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

    def saveResolver = { String barcode, HashMap<String, Object> baseProductItem ->
        def productItem = baseProductItem + [barcode: barcode] as ProductItem

        if(!ProductBase.exists(productItem.base?.id))
            productItem.errors.reject('productBase.id.notfound')

        if(!ProductStatus.exists(productItem.status?.id))
            productItem.errors.reject('productStatus.id.notfound')

        if(ProductItem.exists(barcode))
            productItem.errors.reject('productItem.barcode.unique')

        if(productItem.hasErrors())
            return productItem

        productItem.validate() ? productItem.save() : productItem
    }

    def updateResolver = { String barcode, ProductBase base, ProductStatus status ->
        def exist = ProductItem.exists(barcode)
        def loadItem = exist ? ProductItem.load(barcode) : new ProductItem(barcode: barcode)

        if(!exist)
            loadItem.errors.reject('productItem.notfound.message')

        if(!status)
            loadItem.errors.reject('productStatus.id.notfound')

        if(!base)
            loadItem.errors.reject('productBase.id.notfound')

        if(loadItem.hasErrors())
            return loadItem

        loadItem.status = status
        loadItem.base = base

        loadItem.validate() ? loadItem.save(flush: true) : loadItem
    }

    def changeInStock = { String barcode ->
        def item = get(barcode)
        item.status = ProductStatus.get(IN_STOCK.id)
        item.assigned = null
        item.save(flush: true)
    }

}