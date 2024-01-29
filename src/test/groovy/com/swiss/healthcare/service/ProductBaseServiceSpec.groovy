package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.products.ProductBase
import grails.gorm.transactions.Transactional
import org.grails.orm.hibernate.HibernateDatastore
import org.springframework.transaction.PlatformTransactionManager
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ProductBaseServiceSpec extends Specification {

    @Shared
    ProductBaseService productBaseService

    @Shared @AutoCleanup HibernateDatastore hibernateDatastore
    @Shared PlatformTransactionManager transactionManager


    def setup(){
        hibernateDatastore = new HibernateDatastore(ProductBase)
        transactionManager = hibernateDatastore.getTransactionManager()
        productBaseService = hibernateDatastore.getService(ProductBaseService)
    }

    @Transactional
    def 'test save product base'(){
        given:
        def newBase = new ProductBase(name: 'coca cola', description: 'refresco sabor coca').save(flush: true)

        when:
        ProductBase base = productBaseService.get(newBase.id)

        then:
        base.name == newBase.name
        base.description == newBase.description

        cleanup:
        base.delete()
    }

}
