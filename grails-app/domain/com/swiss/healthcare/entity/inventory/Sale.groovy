package com.swiss.healthcare.entity.inventory

import com.swiss.healthcare.entity.User
import com.swiss.healthcare.entity.products.ProductItem
import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp

class Sale implements GormEntity<Sale> {

  String folio
  List<ProductItem> productList
  User user
  Timestamp dateCreated

  static constraints = {
    folio unique: true
    productList validator: validProductListActive
  }

  static mapping = {
    id name: 'folio'
  }

  static validProductListActive = {
    def productInactive = productList.findAll {!it.status.isActive }
    if(productInactive) return ['productInactive', productInactive*.uid, folio]
  }


}
