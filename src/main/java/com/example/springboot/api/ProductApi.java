package com.example.springboot.api;


import com.example.springboot.entity.Product;
import com.example.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductApi {
    @Autowired
    ProductService productService;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Optional<Product> product = productService.findById(id);
        if (!product.isPresent()){
            ResponseEntity.badRequest().build();// khoong co du lieu tra ve
        }
        return ResponseEntity.ok(product.get());//cos du lieu tra ve
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> save(@RequestBody Product product){
        return ResponseEntity.ok(productService.save(product));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<Product> update(@PathVariable int id, @RequestBody Product updateProduct){
        Optional<Product> product = productService.findById(id);//tim product theo id
        if (!product.isPresent()){
            ResponseEntity.badRequest().build();// khoong co du lieu tra ve
        }
        Product exitsProduct = product.get();
        exitsProduct.setName(updateProduct.getName());
        exitsProduct.setSlug(updateProduct.getSlug());
        exitsProduct.setDescription(updateProduct.getDescription());
        exitsProduct.setThumbnail(updateProduct.getThumbnail());
        exitsProduct.setStatus(updateProduct.getStatus());
        return ResponseEntity.ok(productService.save(exitsProduct));//cos du lieu tra ve
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Optional<Product> product = productService.findById(id);//tim product theo id
        if (!product.isPresent()){
            ResponseEntity.badRequest().build();// khoong co du lieu tra ve
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();//cos du lieu tra ve
    }
}
