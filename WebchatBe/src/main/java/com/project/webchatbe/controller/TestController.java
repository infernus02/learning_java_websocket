package com.project.webchatbe.controller;

import com.project.webchatbe.entity.Product;
import com.project.webchatbe.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    IProductRepository productRepository;

    @GetMapping("/hello")
    List<Product> findAny(@RequestParam("idCate")Long idCate){
        return productRepository.findAny(idCate);
    }
}
