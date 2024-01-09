package com.swiss.healthcare.service

import com.swiss.healthcare.entity.inventory.Sale
import grails.gorm.services.Service

@Service(Sale)
interface SaleService {

    Sale save(Sale sale)

    List<Sale> findAll()

}