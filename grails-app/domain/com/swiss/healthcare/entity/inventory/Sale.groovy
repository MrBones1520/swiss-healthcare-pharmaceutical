package com.swiss.healthcare.entity.inventory

import com.swiss.healthcare.entity.auth.User
import com.swiss.healthcare.entity.inventory.products.ProductItem
import org.grails.datastore.gorm.GormEntity

class Sale implements GormEntity<Sale> {

  String folio
  List<ProductItem> products
  User user
  Date dateCreated

  static hasMany = [products:ProductItem]

  static constraints = {
    folio unique: true, blank: false
  }

  static mapping = {
    id name: 'folio', generator: 'uuid'
    products fetch: 'join'
    user fetch: 'select'
    batchSize 20
  }

}
