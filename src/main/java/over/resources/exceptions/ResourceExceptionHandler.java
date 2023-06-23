package over.resources.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

// Anotação necessária para que essa classe possa interceptar uma exceção
// em outra classe (no caso ProductService)
@ControllerAdvice
public class ResourceExceptionHandler {

//  A anotação @ExceptionHandler serve para especificar a qual classe estará linkada
//  O generics (ou "parâmetro" do retorno) será da classe que criamos que contém o corpo/dados da mensagem de erro
//  Já o primeiro parâmetro da função é o tipo da Exceção que queremos tratar
//  O ServLetRequest passa o caminho da requisição
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        StandardError error = new StandardError();
        error.setTimeStamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Product Not Found");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
