package com.swiss.healthcare

import grails.util.Environment

enum ProductStatusE {
    IN_STOCK(1, 'EN ALMACEN', 'Prooducto en almacen'),
    OUT_STOCK(2, 'FUERA DE ALMACEN', 'Producto fuera de almacen'),
    OUT_SALE(3, 'VENDIDO', 'Producto vendido'),
    LOST(Environment.current == Environment.PRODUCTION ? 9 : 4, 'PERDIDO', 'Producto perdido')

    int id
    String name
    String description

    ProductStatusE(int id, String name, String description) {
        this.id = id
        this.name = name
        this.description = description
    }
}
