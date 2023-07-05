# Projeto Final: Super CevaJA - API Backend

## Resumo do Projeto

O projeto Super CevaJA é uma cervejaria que busca vender suas cervejas através de um aplicativo mobile. Para evitar
altas taxas cobradas por aplicativos de mercado, os donos da Super CevaJA decidiram desenvolver sua própria API REST,
utilizando Java 17+ e Spring Boot.

A equipe de desenvolvimento tem como objetivo construir uma API que permita o cadastro de usuários, tipos de cervejas e
pedidos. Além disso, a API deve oferecer recursos como exclusão lógica, alteração de dados de usuários e cervejas, e
cálculo do valor total dos pedidos com base em regras específicas.

A API será documentada utilizando Swagger e serão implementados testes de unidade e integração. Também será realizada
integração com uma API externa de previsão do tempo para aplicar descontos nos pedidos com base na temperatura atual.

O projeto segue as boas práticas RESTful e utiliza bancos de dados como PostgreSQL, MongoDB ou H2 em Memória. A equipe
deve focar em um bom design de código, seguindo princípios como Clean Code, SOLID e Design Patterns.

## Requisitos

* Java 17+ e Spring Boot
* Versionamento da API
* Porta de execução: 8081
* Boas práticas RESTful
* Banco de Dados: PostgreSQL, MongoDB ou H2 em Memória
* Orientação a Objetos e Design de Código
* Testes de Unidade/Unitários: JUnit e Mockito
* Testes de Integração: H2 Database em Memória
* Integração com API externa: Spring RestTemplate ou Feign Client (Weather API)
* Versionamento do código fonte: GitHub, GitLab ou Bitbucket
* Documentação da API: Swagger
* Validação dos Endpoints: Postman, Insomnia REST ou ferramenta similar
* Gerenciamento de dependências: Maven ou Gradle
* Utilização de LocalDate e API Streams
* Utilização de DTOs

## Endpoints
### GET - /cevaja/api/v1/health
Retorna um Status OK informando que a API está no ar.

### POST - /cevaja/api/v1/usuarios
Adiciona um Usuário específico no Banco de Dados. [...]

### GET - /cevaja/api/v1/usuarios
Retorna a lista de Usuários cadastrados na plataforma. [...]

### DELETE - /cevaja/api/v1/usuarios/{login}
Remove um Usuário, a partir do seu Username/Login. [...]

### PUT - /cevaja/api/v1/usuarios
Altera o nome e/ou sobrenome de um Usuário específico. [...]

### GET - /cevaja/api/v1/cervejas/tipos
Retorna a lista de Tipos de Cervejas disponíveis cadastrados na plataforma. [...]

### POST - /cevaja/api/v1/cervejas/tipos
Adiciona um Tipo de Cerveja específico no Banco de Dados. [...]

### DELETE - /cevaja/api/v1/cervejas/tipos/{nome tipo cerveja}
Remove um Tipo de Cerveja, a partir do seu Nome. [...]

### PUT - /cevaja/api/v1/cervejas/tipos
Altera o Valor de um Tipo de Cerveja. [...]

### POST - /cevaja/api/v1/pedidos
Adiciona um Pedido no Banco de Dados. [...]

## Executando o Projeto
1. Certifique-se de ter o Java 17+ instalado em seu ambiente de desenvolvimento.
2. Clone este repositório para o seu ambiente local.
3. Abra o projeto em sua IDE favorita.
4. Configure corretamente o banco de dados PostgreSQL, MongoDB ou H2 em Memória.
5. Execute a classe principal SuperCevaJaApplication para iniciar a aplicação.
6. A API estará disponível em http://localhost:8081/cevaja/api/v1/.

## Documentação da API
A documentação da API está disponível em http://localhost:8081/swagger-ui.html. Utilize essa documentação para entender e testar os endpoints disponíveis.

## Contribuição
Se você deseja contribuir para este projeto, fique à vontade para fazer um fork e enviar suas propostas de melhoria através de pull requests.