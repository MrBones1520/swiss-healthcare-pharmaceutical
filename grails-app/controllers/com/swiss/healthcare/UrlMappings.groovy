package com.swiss.healthcare

class UrlMappings{
    static mappings = {
        "/"(controller: 'application', action:'index')

        get "/products/status"(controller: 'productStatus', action: 'index')
        get "/products/status/$id"(controller: 'productStatus', action: 'show')
        post "/products/status"(controller: 'productStatus', action: 'save')

        get "/products/base"(controller: 'productBase', action: 'index')
        get "/products/base/$id"(controller: 'productBase', action: 'show')
        post "/products/base"(controller: 'productBase', action: 'save')

        get "/products/item"(controller: 'productItem', action: 'index')
        get "/products/item/$id"(controller: 'productItem', action: 'show')
        post "/products/item"(controller: 'productItem', action: 'save')

        get "/persons"(controller: 'person', action: "index")
        get "/persons/$id"(controller: 'person', action: "show")
        post "/persons"(controller: 'person', action: "save")

        get "/users"(controller: 'user', action: 'index')
        get "/users/$id"(controller: 'user', action: 'show')
        post "/users"(controller: 'user', action: 'save')
        delete "/users/$id"(controller: 'user', action: 'delete')

        get "/sales"(controller: 'sale', action: 'index')
        get "/sales/$id"(controller: 'sale', action:  'show')
        post "/sales"(controller: 'sale', action: 'save')

        "500"(view:'/error')
        "404"(view:'/notFound')
        "409"(view:'/info')

    }
}
