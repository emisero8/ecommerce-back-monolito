package com.tienda.ecommerce.compartido.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VersionController {

    @GetMapping("version")
    public Map getVersion(){

        HashMap<String, String> response= new HashMap<>();
        response.put("name", "Ecommerce-bakcend-monolito");
        response.put("version", "1.0.0");

        return response;
    }
}
