package com.swiss.healthcare

class UrlMappings{
    static mappings = {
        "/"(controller: 'application', action:'index')

        "/products/status"(resources: 'productStatus', excludes:['delete', 'update'])

        get "/products/base"(controller: 'productBase', action: 'index')
        get "/products/base/$id"(controller: 'productBase', action: 'show')
        post "/products/base"(controller: 'productBase', action: 'save')

        "/products/item"(resources: 'productItem')

        get "/persons"(controller: 'person', action: "index")
        get "/persons/$id"(controller: 'person', action: "show")
        post "/persons"(controller: 'person', action: "save")

        "/users"(resources: 'user')
        "/sales"(resources: 'sale', excludes:['delete', 'update'])

        "500"(view:'/error')
        "404"(view:'/notFound')
        "409"(view:'/info')

    }
}
