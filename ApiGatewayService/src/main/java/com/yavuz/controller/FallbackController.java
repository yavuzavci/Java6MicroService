package com.yavuz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @GetMapping("/auth")
    public ResponseEntity<String> fallbackAuth(){
        return ResponseEntity.ok("Auth servisi şuan hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz.");
    }

    @GetMapping("/product")
    public ResponseEntity<String> fallbackProduct(){
        return ResponseEntity.ok("Product servisi şuan hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz.");
    }

    @GetMapping("/sales")
    public ResponseEntity<String> fallbackSales(){
        return ResponseEntity.ok("Sales servisi şuan hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz.");
    }

    @GetMapping("/user")
    public ResponseEntity<String> fallbackUser(){
        return ResponseEntity.ok("User servisi şuan hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz.");
    }
}
