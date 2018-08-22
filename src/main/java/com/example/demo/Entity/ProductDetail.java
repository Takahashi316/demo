package com.example.demo.Entity;


import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class ProductDetail {



    @Id
    private String productId;
    private String productName;
    private String shortDescription;
    private String longDescription;
    private int inventoryId;









}
