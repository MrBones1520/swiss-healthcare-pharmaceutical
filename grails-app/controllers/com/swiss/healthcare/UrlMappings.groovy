package com.swiss.healthcare

class UrlMappings{
    static mappings = {

        "/"(controller: 'application', action:'index')

        get "/products"(controller: 'productBase', action: 'index')
        post "/products"(controller: 'productBase', action: 'save')
        put "/products/$id"(controller: 'productBase', action: 'update')
        delete "/products/$id"(controller: 'productBase', action: 'delete')

        get "/products/status"(controller: 'productStatus', action: 'index')
        post "/products/status"(controller: 'productStatus', action: 'save')

        "/persons"(resources: 'person')
        "/users"(resources: 'user')
        "/sales"(resources: 'sale')

        "500"(view:'/error')
        "404"(view:'/notFound')
        "409"(view:'/info')

    }
}
