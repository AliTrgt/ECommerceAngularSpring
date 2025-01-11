package com.example.E_commerce.exception;


public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException(int id){
            super("Product Not Found with ID : "+id);
    }


}
