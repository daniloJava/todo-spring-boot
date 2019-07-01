# Todos Microservice 

Este projeto tem como objetivo demonstrar Crudo como microservices utilizando a [linguagem de programação Java](https://pt.wikipedia.org/wiki/Java_(linguagem_de_programa%C3%A7%C3%A3o)) em conjunto com o framework [Spring Boot](https://spring.io/projects/spring-boot), framework amplamente difundido no mercado de Tecnologia da Informação.

Conteúdo
==================

- [Introdução](#introdução)
- [Acesso](#acesso)
    - [Heroku](#heroku)  
- [Deploy](#banco-de-dados)
  - [Manual](#elasticsearch)
  - [Docker](#postgresql)
- [Referências](#referências)
- [TODO](#todo)

## Introdução

Aplicação usando Springo Boot com MongoDb. Foi realizado Depoy na ferramenta em cloud no [Hekoku](https://www.heroku.com/home) com acesso ao mongoDB com [MongoDb Atlas](https://www.mongodb.com/cloud/atlas/lp/general/try?jmp=search&utm_source=google&utm_campaign=GS_Americas_Brazil_Search_Brand_Atlas_Desktop&utm_keyword=mongodb%20atlas&utm_device=c&utm_network=g&utm_medium=cpc&utm_creative=335229503988&utm_matchtype=e&gclid=Cj0KCQjwu-HoBRD5ARIsAPIPenfnpVgpzsDfCoA2oU1KBWEp35ipXe8BEMbGE52YxpoBdYhER_I-pMAaAnfzEALw_wcB)

## Acesso

Fazer acesso a Api pelo Heroku com as URLs:
Foi disponibilizado a documentação acessando o [Swagger](https://todo-itau-spring-boot.herokuapp.com/swagger-ui.html)
onde está descrevento todas as Urls e é possivel fazer testes.
-   POST https://todo-itau-spring-boot.herokuapp.com/todo
-   GET https://todo-itau-spring-boot.herokuapp.com/todo/{id}
-   PUT https://todo-itau-spring-boot.herokuapp.com/todo/{id}
-   DELETE https://todo-itau-spring-boot.herokuapp.com/todo/{id}
-   GET https://todo-itau-spring-boot.herokuapp.com/todo/count

Para fazer acesso via Curl:<br>
POST https://todo-itau-spring-boot.herokuapp.com/todo
```shell
curl -X POST "https://todo-itau-spring-boot.herokuapp.com/todo" -H "accept: application/json;charset=UTF-8" -H "Content-Type: application/json;charset=UTF-8" -d "
{
    \"description\": \"Criar todo nova\",
    \"status\": \"completed\"
}"
```
GET https://todo-itau-spring-boot.herokuapp.com/todo/{id}

```shell
curl -X GET "https://todo-itau-spring-boot.herokuapp.com/todo/<ID>" -H "accept: application/json;charset=UTF-8"
```
PUT https://todo-itau-spring-boot.herokuapp.com/todo/{id}
```shell
curl -X PUT "https://todo-itau-spring-boot.herokuapp.com/todo/<ID>" -H "accept: application/json;charset=UTF-8" -H "Content-Type: application/json;charset=UTF-8" -d "{ 
    \"description\": \"Atualizando2 Api\",
    \"status\": \"completed\"
}"
```
DELETE https://todo-itau-spring-boot.herokuapp.com/todo/{id}
```shell
curl -X DELETE "https://todo-itau-spring-boot.herokuapp.com/todo/<ID>" -H "accept: application/json;charset=UTF-8"
```
GET https://todo-itau-spring-boot.herokuapp.com/todo/count
```shell
curl -X GET "https://todo-itau-spring-boot.herokuapp.com/todo/count" -H "accept: application/json;charset=UTF-8"
```
### Deploy

Para fazer o deploy local, temos da forma com Maven ou via Docker.

Na pasta raiz do projeto, executar os comandos abaixo, para baixar o fonte
```shell
git clone https://gitlab.com/daniloJava/task-spring-boot.git
cd ./todo-spring-boot
```
Executar comandos Maven para empacotar e executar
```shell
mvn clean package install -DskipTests
mvn spring-boot:run
```

### Via Docker
Esta disponibilizado uma imagem no [docker Hub](https://hub.docker.com/u/danilojava) com o nome [todo-spring-boot:1.0.0](https://hub.docker.com/r/danilojava/todo-spring-boot)
```shell
docker pull danilojava/todo-spring-boot:1.0.0
docker run -d -p 8080 danilojava/todo-spring-boot:1.0.0
```

Para fazer o build via Docker local se não tiver acesso ao [docker Hub](https://hub.docker.com/u/danilojava).
1. Clonar o projeto
```shell
git clone https://gitlab.com/daniloJava/task-spring-boot.git
cd ./todo-spring-boot
```
2. Empacotar o projeto na pasta raiz
```shell
mvn clean package install -DskipTests
```
3. Run build local
```shell
docker build --build-arg PORT=8080 --build-arg JAR_FILE=target/todo-spring-boot-1.0.0.jar  -t danilojava/todo-spring-boot:1.0.0 .
```
4. Executar imagem.
```shell
docker run -d -p 8080 danilojava/todo-spring-boot:1.0.0
```


## Referências

- [Spring Boot - Documentação](https://docs.spring.io/spring-boot/docs/2.0.4.RELEASE/reference/htmlsingle/)
- [Spring Boot - Endpoints prontos para produção](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)
- [Spring Acuators - Documentação](https://docs.spring.io/spring-boot/docs/2.0.x/actuator-api/html/#overview)
- [Spring Swagger - Introdução ao uso do Swagger para documentação de API](https://www.vojtechruzicka.com/documenting-spring-boot-rest-api-swagger-springfox/)
- [Spring Testing - Documentação](https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html)
- [Restful - Boas práticas para versionamento](https://www.baeldung.com/rest-versioning)
- [Restful - Como versionar uma API](https://www.javadevjournal.com/spring/rest/rest-versioning/)
- [Spring Auto Configuration - Documentação](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html)
- [Logging - Boas práticas para uso do log](http://development.wombatsecurity.com/development/2018/12/20/json-logging-for-spring-boot/)
