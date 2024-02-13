package com.swiss.healthcare

enum ProductStatusE {
    IN_STOCK(1, 'EN ALMACEN', 'Prooducto en almacen'),
    OUT_STOCK(2, 'FUERA DE ALMACEN', 'Producto fuera de almacen'),
    OUT_SALE(3, 'VENDIDO', 'Producto vendido'),
    LOST(9, 'PERDIDO', 'Producto perdido')

    int id
    String name
    String description

    ProductStatusE(int id, String name, String description) {
        this.id = id
        this.name = name
        this.description = description
    }
}
