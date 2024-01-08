package com.swiss.healthcare.entity.inventory

import com.swiss.healthcare.entity.auth.User
import com.swiss.healthcare.entity.inventory.products.ProductItem
import grails.rest.Resource
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

@Resource
@ToString(includes = ['folio', 'user', 'products'], includeNames = true, includePackage = false)
class Sale implements GormEntity<Sale> {

  String folio
  User user
  List<ProductItem> products

  static constraints = {
    folio unique: true, blank: false
  }

  static mapping = {
    id name: 'folio', generator: 'uuid'
  }

}
