package com.example.demo.Controller;


import com.example.demo.Entity.ProductDetail;
import com.example.demo.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/search")
public class ProductDetailSearchController {


     private final ProductDetailRepository productDetailRepository;



     public ProductDetailSearchController(ProductDetailRepository productDetailRepository){
         this.productDetailRepository=productDetailRepository;

     }







    @RequestMapping(method = RequestMethod.GET)
    public List search(@RequestParam("q") String queryTerm) {
        List productDetails = productDetailRepository.search("%"+queryTerm+"%");
        return productDetails == null ? new ArrayList<>() : productDetails;
    }






}
