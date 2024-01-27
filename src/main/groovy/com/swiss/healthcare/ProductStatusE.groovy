package com.swiss.healthcare

enum ProductStatusE {
    IN_STOCK(1, 'IN_STOCK', 'Product in stock'),
    OUT_STOCK(2, 'OUT_STOCK', 'Product out stock'),
    OUT_SALE(3, 'OUT_SALE', 'Product out sale')

    int id
    String name
    String description

    ProductStatusE(int id, String name, String description) {
        this.id = id
        this.name = name
        this.description = description
    }
}
