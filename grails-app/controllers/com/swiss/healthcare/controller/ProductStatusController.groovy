package com.swiss.healthcare.controller


import com.swiss.healthcare.entity.inventory.products.ProductStatus
import grails.rest.RestfulController

class ProductStatusController extends RestfulController<ProductStatus> {

  ProductStatusController() {
    super(ProductStatus)
  }


}
