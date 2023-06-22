package over.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import over.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import over.repositories.ProductRepository;
import over.services.ProductService;

import java.rmi.ServerException;
import java.util.List;
import java.util.NoSuchElementException;

//  Definição do controlador REST
    @RestController
    @RequestMapping(value = "/products")
    public class ProductController {

//  Este serviço será uma camada entre o controlador e o repositório
//  para tratar erros de requisição, por exemplo.
    @Autowired
    private ProductService productService;

//  Dependência do controlador com o repository
    @Autowired
    private ProductRepository repository;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> result = repository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok().body(product);

    }
//   Outra maneira de se fazer a função anterior sem a camada Service e tratamento personalizado:

//        if (repository.findById(id).isPresent()) {
//  O findByID retorna um valor do tipo Optional e dentro dele estará o tipo Product extraído pelo método .get()
//            Product product = repository.findById(id).get();
//            return new ResponseEntity<>(product,HttpStatus.FOUND);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }



    @GetMapping(value = "/{name}")
    public ResponseEntity<List<Product>> productByName(@PathVariable String name){
        if (!repository.productByName(name).isEmpty()) {
            List<Product> result = repository.productByName(name);
            return new ResponseEntity<>(result,HttpStatus.FOUND);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

//        @ResponseStatus(HttpStatus.CREATED)
//        public Product created(@RequestBody Product product){
//            return repository.save(product);
//        }

    @PutMapping(value = "/update-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {

        product = repository.saveAndFlush(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {

//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.add("custom-header", "o seu header customizável");

        try {
            Product product = repository.findById(id).get();
            repository.delete(product);
            return ResponseEntity.status(410).body(product);

        } catch (Exception NoSuchElementException){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}




