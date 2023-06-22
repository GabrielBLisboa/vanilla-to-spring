package over.resources.exceptions;

// O RunTime foi adicionado a declaração da classe porque ele é uma exceção que não exige try-catch
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String msg){
//      O super repassa a String para a classe mãe (de quem recebe a herança de métodos e atributos)
        super(msg);
    }


}
