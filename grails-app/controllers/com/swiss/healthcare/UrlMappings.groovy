package com.swiss.healthcare

class UrlMappings{
    static mappings = {
        "/"(controller: 'application', action:'index')
        post "/login"(controller: 'login', action: 'login')

        get     "/products/auditory"(controller: 'auditory', action: 'index')
        get     "/products/auditory/search"(controller: 'auditory', action: 'search')

        get     "/products/status"(controller: 'productStatus', action: 'index')
        get     "/products/status/$id"(controller: 'productStatus', action: 'show')
        post    "/products/status"(controller: 'productStatus', action: 'save')

        get     "/products/base"(controller: 'productBase', action: 'index')
        get     "/products/base/$id"(controller: 'productBase', action: 'show')
        post    "/products/base"(controller: 'productBase', action: 'save')
        put     "/products/base/$id"(controller: 'productBase', action: 'update')

        get     "/products/item"(controller: 'productItem', action: 'index')
        get     "/products/item/$id"(controller: 'productItem', action: 'show')
        get     "/products/item/base/$id"(controller: 'productItem', action: 'base')
        get     "/products/item/status/$status"(controller: 'productItem', action: 'status')
        get     "/products/item/search"(controller: 'productItem', action: 'search')
        get     "/products/item/export"(controller: 'productItem', action: 'export')
        post    "/products/item"(controller: 'productItem', action: 'save')
        put     "/products/item"(controller: 'productItem', action: 'update')

        get     "/persons"(controller: 'person', action: "index")
        get     "/persons/$id"(controller: 'person', action: "show")

        get     "/users"(controller: 'user', action: 'index')
        get     "/users/$id"(controller: 'user', action: 'show')
        post    "/users"(controller: 'user', action: 'save')

        get     "/sales"(controller: 'sale', action: 'index')
        get     "/sales/$id"(controller: 'sale', action:  'show')
        post    "/sales"(controller: 'sale', action: 'save')

        "500"(view:'/error')
        "404"(view:'/notFound')
        "409"(view:'/info')

    }
}
