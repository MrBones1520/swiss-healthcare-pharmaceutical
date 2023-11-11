package com.swiss.healthcare

class UrlMappings {
    static mappings = {

        "/"(controller: 'application', action:'index')
        "/products"(resources: 'product')
        "/users"(resources: 'user')

        get "/product/status"(controller: 'productStatus', action: 'index')

        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}
