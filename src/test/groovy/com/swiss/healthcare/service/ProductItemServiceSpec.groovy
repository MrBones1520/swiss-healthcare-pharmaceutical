package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductItem
import grails.gorm.transactions.Rollback
import org.grails.orm.hibernate.HibernateDatastore
import org.springframework.transaction.PlatformTransactionManager
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ProductItemServiceSpec extends Specification{

    @Shared
    ProductItemService productItemService

    @Shared
    @AutoCleanup
    HibernateDatastore hibernateDatastore

    @Shared
    PlatformTransactionManager transactionManager

    void setupSpec(){
        hibernateDatastore = new HibernateDatastore(ProductItem)
        transactionManager = hibernateDatastore.getTransactionManager()
        productItemService = this.hibernateDatastore.getService(ProductItemService)
    }

}
