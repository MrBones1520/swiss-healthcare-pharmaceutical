package com.swiss.healthcare

class UrlMappings{
    static mappings = {

        "/"(controller: 'application', action:'index')

        "/products/status"(resources: 'productStatus', excludes:['delete', 'update'])
        "/products/item"(resources: 'productItem', excludes:['insert', 'update'])
        "/products/base"(resources: 'productBase')
        "/persons"(resources: 'person')
        "/users"(resources: 'user')
        "/sales"(resources: 'sale')

        "500"(view:'/error')
        "404"(view:'/notFound')
        "409"(view:'/info')

    }
}
