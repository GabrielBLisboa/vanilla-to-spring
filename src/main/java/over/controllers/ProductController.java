package over.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import over.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import over.repositories.ProductRepository;

import java.rmi.ServerException;
import java.util.List;

    @RestController
    @RequestMapping(value = "/products")
    public class ProductController {

        @Autowired
        private ProductRepository repository;

        @GetMapping
        public ResponseEntity<List<Product>> findAll() {
            List<Product> result = repository.findAll();
            return ResponseEntity.ok(result);
        }

        @GetMapping(value = "/search-name")
        public ResponseEntity<List<Product>> productByName(
                @RequestParam(defaultValue = "Tacos") String name){
            List<Product> result = repository.productByName(name);
            return ResponseEntity.ok(result);
        }

        @GetMapping(value = "/price-limit")
        public ResponseEntity<Page<Product>> priceLimit(
                @RequestParam(defaultValue = "30") Double valorMaximo,
                Pageable page){
            Page<Product> result = repository.priceLimit(valorMaximo, page);
            return ResponseEntity.ok(result);
        }
        
        @PostMapping(value = "/add-product", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Product> addProduct(@RequestBody Product product)  {

                product = repository.saveAndFlush(product);

                return new ResponseEntity<>(product, HttpStatus.CREATED);

        }
    }


