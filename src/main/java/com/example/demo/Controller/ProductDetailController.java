package com.example.demo.Controller;


import com.example.demo.Entity.ProductDetail;
import com.example.demo.ProductDetailValidator;
import com.example.demo.repository.ProductDetailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductDetailController {
    private final ProductDetailRepository productDetailRepository;
    private final ObjectMapper objectMapper;
    private final ProductDetailValidator productDetailValidator;



    @Autowired
    public ProductDetailController(ProductDetailRepository productDetailRepository,ObjectMapper objectMapper,ProductDetailValidator productDetailValidator){
        this.productDetailRepository=productDetailRepository;
        this.objectMapper=objectMapper;
        this.productDetailValidator=productDetailValidator;




    }


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(productDetailValidator);
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductDetail find(@PathVariable String id) {
        ProductDetail detail = productDetailRepository.findOne(id);
        if (detail == null) {
            throw new ProductNotFoundException();
        } else {
            return detail;
        }
    }




    @RequestMapping(method = RequestMethod.GET)
    public Iterable findAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                            @RequestParam(value = "count", defaultValue = "10", required = false) int count,
                            @RequestParam(value = "order", defaultValue = "ASC", required = false) Sort.Direction direction,
                            @RequestParam(value = "sort", defaultValue = "productName", required = false) String sortProperty) {
        Page result = productDetailRepository.findAll(new PageRequest(page, count, new Sort(direction, sortProperty)));
        return result.getContent();
    }



    @RequestMapping(method = RequestMethod.POST)
    public ProductDetail create(@RequestBody ProductDetail detail){

        return productDetailRepository.save(detail);




    }



    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity update(@PathVariable String id, HttpServletRequest request) throws IOException {
        ProductDetail existing = find(id);
        ProductDetail updated = objectMapper.readerForUpdating(existing).readValue(request.getReader());
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("productId", updated.getProductId());
        propertyValues.add("productName", updated.getProductName());
        propertyValues.add("shortDescription", updated.getShortDescription());
        propertyValues.add("longDescription", updated.getLongDescription());
        propertyValues.add("inventoryId", updated.getInventoryId());
        DataBinder binder = new DataBinder(updated);
        binder.addValidators(productDetailValidator);
        binder.bind(propertyValues);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            return new ResponseEntity<>(binder.getBindingResult().getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
        }
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpEntity delete(@PathVariable String id) {
        ProductDetail detail = find(id);
        productDetailRepository.delete(detail);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class ProductNotFoundException extends RuntimeException {
    }










}
