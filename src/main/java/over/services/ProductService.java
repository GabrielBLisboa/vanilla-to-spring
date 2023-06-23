package over.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import over.entities.Product;
import over.repositories.ProductRepository;
import over.resources.exceptions.EntityNotFoundException;

// Essa anotação serve para registrar a classe como um componente
// que vai participar do mecanismo de injeção de dependência do Spring
@Service
public class ProductService {

//O mais correto seria retirar essa injeção da classe controlador, porém como o intuito é
// exemplificar maneiras diversas, não vou quebrar os outros tipos de requisição
    @Autowired
    private ProductRepository repository;

// função a nível de serviço que caso receba um ID que não exista dispara uma Exceção personalizada
    public Product findById(Long id){
//  quando importar o EntityNotFoundException ter atenção para importar o que você criou e não o jakarta.persistence
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ID Not Found: " + id)
        );
    }

}
