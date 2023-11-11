package com.swiss.healthcare

import groovy.util.logging.Log

@Log
class BootStrap {


    def init = { servletContext ->

        new Product(barcode: "dasdasd", name: "frutsi", description: '').save()

    }

    def destroy = {
    }
}