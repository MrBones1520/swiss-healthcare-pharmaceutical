package com.swiss.healthcare

class UrlMappings{
    static mappings = {

        "/"(controller: 'application', action:'index')

        "/products/status"(resources: 'productStatus', excludes:['delete', 'update'])
        "/products/base"(resources: 'productBase', excludes:['delete'])
        "/products/item"(resources: 'productItem')
        "/persons"(resources: 'person')
        "/users"(resources: 'user')
        "/sales"(resources: 'sale')

        "500"(view:'/error')
        "404"(view:'/notFound')
        "409"(view:'/info')

    }
}
