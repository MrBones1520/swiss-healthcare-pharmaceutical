package com.swiss.healthcare.entity.inventory

import com.swiss.healthcare.entity.auth.User
import com.swiss.healthcare.entity.inventory.products.ProductItem
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp

@ToString(includes = ['folio', 'dateCreated', 'user'], includeNames = true, includePackage = false)
class Sale implements GormEntity<Sale> {

  String folio
  User user
  Timestamp dateCreated

  static constraints = {
    folio unique: true, blank: false
    products validator: validProductListActive
  }

  static mapping = {
    id name: 'folio', generator: 'assigned'
  }

  static hasMany = [products: ProductItem]

  static validProductListActive = {
    def productInactive = products.findAll {it.status.enable}
    if(productInactive) return ['productInactive', products*.uid, folio]
  }


}
