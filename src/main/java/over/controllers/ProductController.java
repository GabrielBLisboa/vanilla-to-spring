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
import java.util.Objects;

//  Definição do controlador REST
@RestController
@RequestMapping(path = "/products")
public class ProductController {

//  ProductService será uma camada entre o controlador e o repositório
//  para tratar erros de requisição, por exemplo.
    @Autowired
    private ProductService productService;

//  Dependência do controlador com o repository
    @Autowired
    private ProductRepository repository;


//  Uma requisição simples como a findAll pode trazer como retorno a lista sem a necessidade de uma ResponseEntity.
//  Esta faz mais sentido, portanto, quando usada com seus parâmetros, um custom Header e um StatusCode
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<Product> result = repository.findAll();
        httpHeaders.add("Quantidade de produtos", String.valueOf(result.size()));
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }
//  Outra maneira de se fazer a função anterior sem a camada Service e tratamento personalizado:
//        if (repository.findById(id).isPresent()) {
//  O findByID retorna um valor do tipo Optional e dentro dele estará o tipo Product extraído pelo método .get()
//            Product product = repository.findById(id).get();
//            return new ResponseEntity<>(product,HttpStatus.FOUND);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }


//  Essa requisição pede como parâmetro o formato search?name=*nome-do-produto*
//  O caminho extra /search precisa ser adicionado porque apenas /produtos?name= esse Request dá conflito
//  com o findAll que utiliza o path /produtos sozinho
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> productByName(@RequestParam(value="name") String name) {
        if (repository.productByName(name.toLowerCase()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<Product> result = repository.productByName(name.toLowerCase());
            return new ResponseEntity<>(result, HttpStatus.FOUND);
        }
    }

//    Como queria testar o tipo Pageable, achei interessante nessa request porque caso o usuário
//      insira valor menor do que aquele do produto mais barato, a página informa que está vazia

    @GetMapping(value = "/price-limit/{valorMaximo}")
    public ResponseEntity<Page<Product>> priceLimit(@PathVariable Double valorMaximo,
            Pageable page){

        Page<Product> result = repository.priceLimit(valorMaximo, page);
        return ResponseEntity.ok(result);
    }

//  Para criar um produto novo é preciso adicionar seus parâmetros em formato JSON no Postman
    @PostMapping(value = "/add-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addProduct(@RequestBody Product product)  {
        product = repository.saveAndFlush(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

// Criei essa função para testar a criação de um produto sem um POST ou path
    @ResponseStatus(HttpStatus.CREATED)
    public Product created(@RequestBody Product product){
            return repository.save(product);
        }


//  Uma requisição PUT faz mais sentido atualizando um item (pode também criá-lo),
//      portanto é feita a validação se o produto existe.
    @PutMapping(value = "/update-product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody Product productUpdate, @PathVariable Long id) {
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        productUpdate.setId(id);
        productUpdate = repository.save(productUpdate);
            return new ResponseEntity<>(productUpdate, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
            Product product = productService.findById(id);
            repository.delete(product);
            return ResponseEntity.status(204).body(product);
    }
}




