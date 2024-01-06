package com.siwss.healthcare.service

import com.swiss.healthcare.entity.inventory.Sale
import grails.gorm.services.Service

@Service(Sale)
interface SaleService {

    Sale save(Sale sale)

    Sale get(Serializable id)

    long count()

}