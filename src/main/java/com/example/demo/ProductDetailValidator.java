package com.example.demo;


import com.example.demo.Entity.ProductDetail;
import com.example.demo.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@Component
public class ProductDetailValidator implements Validator {
    private InventoryService inventoryService;

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductDetail.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
       ProductDetail productDetail=(ProductDetail) o;
        if (!inventoryService.isValidInventory(productDetail.getInventoryId())) {
            errors.rejectValue("inventoryId", "inventory.id.invalid", "Inventory ID is invalid");
        }


    }




    private ProductDetailValidator(InventoryService inventoryService){

     this.inventoryService=inventoryService;






    }



}
