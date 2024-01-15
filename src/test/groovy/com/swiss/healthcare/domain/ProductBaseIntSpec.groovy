package com.swiss.healthcare.domain

import com.swiss.healthcare.entity.inventory.products.ProductBase
import org.grails.orm.hibernate.HibernateDatastore
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ProductBaseIntSpec extends Specification{

    @Shared @AutoCleanup HibernateDatastore hibernateDatastore

    void setupSpec(){
        hibernateDatastore = new HibernateDatastore(ProductBase)
    }

    void 'test'(){



    }

}
