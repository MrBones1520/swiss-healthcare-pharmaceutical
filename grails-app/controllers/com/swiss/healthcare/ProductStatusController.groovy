package com.swiss.healthcare


import com.swiss.healthcare.entity.products.ProductStatus
import grails.rest.RestfulController

class ProductStatusController extends RestfulController<ProductStatus> {

  ProductStatusController() {
    super(ProductStatus)
  }


}
