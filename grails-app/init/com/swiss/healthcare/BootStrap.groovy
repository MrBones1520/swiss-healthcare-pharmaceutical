package com.swiss.healthcare

import com.swiss.healthcare.service.ProductBaseService
import com.swiss.healthcare.service.ProductItemService
import com.swiss.healthcare.service.ProductStatusService
import com.swiss.healthcare.service.SaleService
import com.swiss.healthcare.service.UserService
import com.swiss.healthcare.entity.auth.User
import com.swiss.healthcare.entity.inventory.Sale
import com.swiss.healthcare.entity.inventory.products.ProductBase
import com.swiss.healthcare.entity.inventory.products.ProductItem
import com.swiss.healthcare.entity.inventory.products.ProductStatus
import com.swiss.healthcare.entity.people.Person
import grails.util.Environment
import groovy.util.logging.Log

@Log
class BootStrap {

    UserService userService
    SaleService saleService
    ProductStatusService productStatusService
    ProductBaseService productBaseService
    ProductItemService productItemService

    def init = { servletContext ->
        if(Environment.getCurrentEnvironment() == Environment.DEVELOPMENT)
            defaultData()
    }

    def destroy = {
    }

    def defaultData = {
            log.info("Try create a user")
            def user = userService.save(
                    new User(email: 'root@admin.com',
                            username: 'qotsa1520',
                            password: 'x1234',
                            person: new Person(firstName: 'root',motherSName: '546', fatherSName: 'Sanchez', birthday: new Date())
                    )
            )
            printCube(user)

            log.info("Try create product status")
            def stockIn = productStatusService.save(ProductStatus.IN_STOCK)
            def stockOut = productStatusService.save(ProductStatus.OUT_STOCK)
            def saleOut = productStatusService.save(ProductStatus.OUT_SALE)
            printCube(stockIn)
            printCube(stockOut)
            printCube(saleOut)

            log.info("Try create product base")
            def base = productBaseService.save(new ProductBase(name: 'coca cola', description: '600ml'))
            printCube(base)

            log.info("Try create product item")
            def productItem = productItemService.save(new ProductItem(
                    barcode: "000000000",
                    status: productStatusService.get(1),
                    descriptor: productBaseService.get(1),
                    user: userService.get(1))
            )
            printCube(productItem)

            log.info("Try create sale")
            def sale = saleService.save(
                    new Sale(
                            user: userService.get(1),
                            products: [productItemService.get("000000000")])
            )
            printCube(sale)
    }

    def printCube = { value ->
        log.info("=".repeat(100))
        log.info("${value}")
        log.info("=".repeat(100))
    }

}