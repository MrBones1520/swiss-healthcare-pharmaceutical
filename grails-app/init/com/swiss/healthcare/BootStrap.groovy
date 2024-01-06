package com.swiss.healthcare

import com.siwss.healthcare.service.ProductBaseService
import com.siwss.healthcare.service.ProductItemService
import com.siwss.healthcare.service.ProductStatusService
import com.siwss.healthcare.service.SaleService
import com.siwss.healthcare.service.UserService
import com.swiss.healthcare.entity.auth.User
import com.swiss.healthcare.entity.inventory.Sale
import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import com.swiss.healthcare.entity.people.Person
import groovy.util.logging.Log

@Log
class BootStrap {

    UserService userService
    SaleService saleService
    ProductStatusService productStatusService
    ProductBaseService productBaseService
    ProductItemService productItemService

    def init = { servletContext ->
        userService.save(
            new User(email: 'root@admin.com', username: 'root', password: 'x1234',
                    person: new Person(firstName: 'root',motherSName: '546', fatherSName: 'Sanchez', birthday: new Date())
            )
        )
        productStatusService.save(new ProductStatus(name: 'ACTIVE', description: 'ACTIVE STATUS'))
        productStatusService.save(new ProductStatus(name: 'MANAGER', description: 'MANAGER STATUS'))
        productBaseService.save(new ProductBase(barcode: "000000000", name: 'coca cola', description: '600ml'))
        productItemService.save(new ProductItem(status: productStatusService.get(1), descriptor: productBaseService.get(1)))
        saleService.save(
                new Sale(
                        user: User.get(1),
                        products: [ProductItem.get(1)])
        )

    }

    def destroy = {
    }

}