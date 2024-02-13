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
        if(ProductStatus.count != 4)
            defaultStatus()

        if(User.count() == 0){
            log.info("Try create a user")
            def user = userService.save(
                    new User(email: 'root@admin.com',
                            username: 'root',
                            password: 'root',
                            person: new Person(firstName: 'root',motherSName: '546', fatherSName: 'Sanchez', birthday: new Date())
                    )
            )
            printCube(user)
        }

        if(Environment.getCurrentEnvironment() != Environment.PRODUCTION)
            defaultData()
    }

    def destroy = {
    }

    def defaultData = {
            log.info("Try create product base")
            def paracetamol = productBaseService.save(new ProductBase(name: 'paracetamol', description: '600ml'))
            def nolotil = productBaseService.save(new ProductBase(name: 'nolotil', description: '800gr'))
            def adiro100 = productBaseService.save(new ProductBase(name: 'adiro100', description: '20pz'))
            def enantyum = productBaseService.save(new ProductBase(name: 'enantyum', description: '1L'))
            def eutorirox = productBaseService.save(new ProductBase(name: 'eutorirox', description: '600ml'))
            def ventolin = productBaseService.save(new ProductBase(name: 'ventolin', description: '600ml'))
            def sintrom = productBaseService.save(new ProductBase(name: 'sintrom', description: '600ml'))
            def orfidal = productBaseService.save(new ProductBase(name: 'orfidal', description: '600ml'))
            printCube(paracetamol)
            printCube(nolotil)
            printCube(adiro100)
            printCube(enantyum)
            printCube(eutorirox)
            printCube(ventolin)
            printCube(sintrom)
            printCube(orfidal)


            log.info("Try create product item")
            def productItem0 = productItemService.save(
                    new ProductItem(barcode: "000000000", status: productStatusService.get(1), base: productBaseService.get(1)))
             def productItem1 = productItemService.save(
                        new ProductItem(barcode: "000000001", status: productStatusService.get(1), base: productBaseService.get(2)))
             def productItem2 = productItemService.save(
                        new ProductItem(barcode: "000000002", status: productStatusService.get(1), base: productBaseService.get(3)))
             def productItem3 = productItemService.save(
                        new ProductItem(barcode: "000000003", status: productStatusService.get(1), base: productBaseService.get(4)))
             def productItem4 = productItemService.save(
                        new ProductItem(barcode: "000000004", status: productStatusService.get(1), base: productBaseService.get(5)))
             def productItem5 = productItemService.save(
                        new ProductItem(barcode: "000000005", status: productStatusService.get(1), base: productBaseService.get(6)))
             def productItem6 = productItemService.save(
                        new ProductItem(barcode: "000000006", status: productStatusService.get(1), base: productBaseService.get(1)))
             def productItem7 = productItemService.save(
                            new ProductItem(barcode: "000000007", status: productStatusService.get(3), base: productBaseService.get(4)))
             def productItem8 = productItemService.save(
                            new ProductItem(barcode: "000000008", status: productStatusService.get(2), base: productBaseService.get(1)))
             def productItem9 = productItemService.save(
                            new ProductItem(barcode: "000000009", status: productStatusService.get(2), base: productBaseService.get(7)))
             def productItem10 = productItemService.save(
                            new ProductItem(barcode: "0000000010", status: productStatusService.get(2), base: productBaseService.get(1)))
            printCube(productItem0)
            printCube(productItem1)
            printCube(productItem2)
            printCube(productItem3)
            printCube(productItem4)
            printCube(productItem5)
            printCube(productItem6)
            printCube(productItem7)
            printCube(productItem8)
            printCube(productItem9)
            printCube(productItem10)


            log.info("Try create sale")
            def sale = saleService.save(new Sale(user: userService.get(1),
                    products: [productItemService.get("000000000")]))
            printCube(sale)
    }

    def printCube = { value ->
        log.info("=".repeat(100))
        log.info("${value}")
        log.info("=".repeat(100))
    }

    def defaultStatus(){
        if(ProductStatus.count > 0)
            ProductStatus.deleteAll(ProductStatus.findAll())

        log.info("Try create product status.gson")
        List<ProductStatus> statuses = ProductStatus.saveAll(
                new ProductStatus(name: 'EN ALMACEN', description: 'Producto en almacen'),
                new ProductStatus(name: 'FUERA DE ALMACEN', description: 'Producto fuera del almacen'),
                new ProductStatus(name: 'VENDIDO', description: 'Producto vendido'),
                new ProductStatus(name: 'PERDIDO', description: 'Producto no encontrado')
        )

        statuses.each { printCube(it)}

    }

}