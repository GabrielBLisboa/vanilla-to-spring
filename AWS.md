Subi a API para uma instância (servidor virtual) com IP Elástico na cloud da AWS.

### Instalação

1) Abrir um terminal com git bash na pasta local onde está localizada a chave .pem

2) Executar:
>  ssh -i "vanilla-to-spring.pem" ubuntu@ec2-52-0-25-99.compute-1.amazonaws.com

3) Ir até a pasta onde está o arquivo .jar
> cd vanilla-to-spring
> cd target

4) Executar:
 > java -jar demo-0.0.1-SNAPSHOT.jar

5) Acessar os endpoints trocando o * http://localhost: * por * http://52.0.25.99: *

![AWS2](https://github.com/GabrielBLisboa/vanilla-to-spring/assets/113642602/ca58ed0d-d1ee-450f-9285-69b044bd0b82)
![AWS1](https://github.com/GabrielBLisboa/vanilla-to-spring/assets/113642602/cc44d68d-5a01-464e-8ea3-d67c9bf25125)
