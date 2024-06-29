package com.chinmay.dto;

import lombok.Data;

@Data
public class Customer {

    private int id;
    private String name;
    private String email;
    private String contactNo;


    public Customer(int i, String chinmay, String mail, String number) {
    }
}
// send Customer object to kafka topic and consumer should consume that