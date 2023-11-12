package com.swiss.healthcare

class UrlMappings{
    static mappings = {

        "/"(controller: 'application', action:'index')
        "/products"(resources: 'productBase')
        "/users"(resources: 'user')

        "500"(view:'/error')
        "404"(view:'/notFound')
        "409"(view:'/info')

    }
}
