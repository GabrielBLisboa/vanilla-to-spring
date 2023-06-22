package over.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import over.entities.Product;
import over.repositories.ProductRepository;

// Essa anotação serve para registrar a classe como um componente
// que vai participar do mecanismo de injeção de dependência do Spring
@Service
public class ProductService {

//O mais correto seria retirar essa injeção da classe controlador, porém como o intuito é
// exemplificar maneiras diversas, não vou quebrar os outros tipos de requisição
    @Autowired
    private ProductRepository repository;

// função que a nível de serviço que caso receba um ID que não exista dispara uma Exceção personalizada
    public Product findById(Long id){
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Id not found" + id)
        );
    }

}
