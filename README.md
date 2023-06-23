Nachos, tacos e burritos!!

Tudo começou quando quis pegar a base de um projeto sem framework (ver meu repositório: java-postgresql) e criar um novo com Spring Boot. 

Então pensei nessa API-REST que consulta, cria, atualiza e deleta produtos de um banco de dados. De início, são gerados três pratos da culinária mexicana (amo!) pelo arquivo import.sql.

Foram criadas classes de controle, repositório e uma de serviço para intermediá-las para tratar exceções. Todo projeto está comentado então é possível seguir minha linha de raciocínio e implementações.

### Instalação

É necessário ter o Maven instalado e criar suas variáveis de dependência.

Tutorial para Windows, Linux e Mac [aqui.](https://www.baeldung.com/install-maven-on-windows-linux-mac)

### Para rodar a API pelo terminal:

Abrir o Prompt de Comando

	Pesquisar -> cmd
	
Vá até o diretório do arquivo 

	cd .../vanilla-to-spring/
	
Dgite o comando + Enter

	mvn spring-boot:start

### Rodar os testes e a API numa IDE :

Como criei os testes no me mesmo projeto que a API, não poderia rodar duas aplicações ao mesmo tempo. Pelo próprio terminal do IntelliJ é possível então rodar a API usando o comando *mvn spring-boot:start* e em seguida rodar a classe com todos os testes.

### Para as verificações CRUD com o Postman criei os seguintes requests:

GET <br>
All Products: http://localhost:8080/products <br>
Find By ID That Exist: http://localhost:8080/products/2 <br>
Find By ID That Don't Exist: http://localhost:8080/products/5 <br>
Search By Name That Exist: http://localhost:8080/products/search?name=Tacos <br>
Search By Name That Don't Exist: http://localhost:8080/products/search?name=Pizza <br>
Limit By Price 30: http://localhost:8080/products/price-limit/30 <br>
Limit By Price 10: http://localhost:8080/products/price-limit/10 <br>


POST <br>
New Product: http://localhost:8080/products/add-product  <br>
Body de exemplo: <br>
{ <br>
  "name": "quesadilla", <br>
  "price": 20.90, <br>
  "description": "Quesadilla com queijo" <br>
} <br>


PUT <br>
Update Product: http://localhost:8080/products/update-product/4
Body de exemplo: <br>
{ <br>
  "name": "quesadilla", <br>
  "price": 21.90, <br>
  "description": "Quesadilla com queijo" <br>
} <br>


DELETE <br>
Delete Product: http://localhost:8080/products/4
