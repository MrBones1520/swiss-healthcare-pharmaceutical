package com.swiss.healthcare

class UrlMappings{
    static mappings = {
        "/"(controller: 'application', action:'index')

        post "/login"(controller: 'login', action: 'login')

        get     "/products/auditory"(controller: 'auditory', action: 'index')
        get     "/products/auditory/search"(controller: 'auditory', action: 'search')
        post    "/products/auditory/stock"(controller: 'auditory', action: 'inStock')

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
        get     "/products/item/export"(controller: 'productItem', action: 'export')
        post    "/products/item"(controller: 'productItem', action: 'save')
        put     "/products/item"(controller: 'productItem', action: 'update')

        "/persons"(resources: 'person', includes: ['index', 'show'])
        "/users"(resources: 'user', includes: ['index', 'show', 'save'])
        "/sales"(resources: 'sale', includes: ['index', 'show', 'save'])

        "500"(view:'/error')
        "404"(view:'/notFound')
        "409"(view:'/info')

    }
}
