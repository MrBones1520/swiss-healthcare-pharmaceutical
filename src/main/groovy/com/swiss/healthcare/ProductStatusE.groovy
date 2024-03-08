package com.swiss.healthcare

import grails.util.Environment

enum ProductStatusE {
    IN_STOCK(1, 'NUCLÉO', 'Prooducto en almacen/nucléo'),
    OUT_STOCK(2, 'FUERA DE ALMACEN', 'Producto fuera de almacen'),
    OUT_SALE(3, 'VENDIDO', 'Producto vendido'),
    LOST(4, 'PERDIDO', 'Producto perdido'),
    OUT_CORE(5, 'FUERA DE NUCLÉO', 'Producto en Oficina/Sin Responsable asociado'),
    RESPONSIBLE_SELLER(6, 'Responsable Vendedor', 'Producto con responsable asociado ');


    int id
    String name
    String description

    ProductStatusE(int id, String name, String description) {
        this.id = id
        this.name = name
        this.description = description
    }
}
