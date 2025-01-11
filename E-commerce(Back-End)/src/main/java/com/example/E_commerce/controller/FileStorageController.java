package com.example.E_commerce.controller;


import com.example.E_commerce.exception.IdNotFoundException;
import com.example.E_commerce.model.Product;
import com.example.E_commerce.repository.ProductRepository;
import com.example.E_commerce.service.FileStorageService;
import jakarta.persistence.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/photos")
public class FileStorageController {


    private final FileStorageService fileStorageService;
    private final ProductRepository productRepository;


    public FileStorageController(FileStorageService fileStorageService, ProductRepository productRepository) {
        this.fileStorageService = fileStorageService;
        this.productRepository = productRepository;
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadfile(@PathVariable int id, @RequestParam("file") MultipartFile multipartFile){
            try {
                Product product = productRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
                String photoUrl = fileStorageService.saveFile(multipartFile);
                product.setImageUrl(photoUrl);
                productRepository.save(product);
               return ResponseEntity.ok(photoUrl);
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
            }

    }


}
