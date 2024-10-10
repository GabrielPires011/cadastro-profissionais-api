# Cadastro de Profissionais - API

Este projeto é uma API para o cadastro de profissionais, desenvolvida em Java 17, Spring Boot 3.3.3, e utilizando PostgreSQL como banco de dados.

## Pré-requisitos

- Docker e Docker Compose instalados na máquina
- Java 17
- Maven

## Configuração do Ambiente

### Banco de Dados

O projeto utiliza o PostgreSQL como banco de dados, configurado no Docker. Abaixo, as credenciais utilizadas:

- **Usuário:** `root`
- **Senha:** `root`
- **Banco de dados:** `profissional`

A porta exposta para o banco de dados local é a `5432`.

### Aplicação

A aplicação Java é configurada para rodar na porta `8080`. O código fonte está configurado com as seguintes variáveis de ambiente para conexão com o banco de dados:

- **URL:** `jdbc:postgresql://postgres:5432/profissional`
- **Usuário:** `root`
- **Senha:** `root`

## Como Iniciar o Projeto

1. Clone este repositório:

   ```bash
   git clone <url_do_repositorio>
   cd <nome_do_diretorio_clonado>
   ```

2. Certifique-se de ter o Docker e Docker Compose instalados em sua máquina. Se necessário, siga a documentação oficial para instalação:
    - [Docker](https://docs.docker.com/get-docker/)
    - [Docker Compose](https://docs.docker.com/compose/install/)

3. Crie o arquivo `.jar` da aplicação:

   ```bash
   mvn clean package
   ```

4. Inicie os contêineres com Docker Compose:

   ```bash
   docker-compose up --build
   ```

5. Aguarde até que o banco de dados PostgreSQL esteja pronto. A aplicação será iniciada automaticamente após a verificação da disponibilidade do banco de dados.

6. Acesse a aplicação:

    - **URL da API:** [http://localhost:8080](http://localhost:8080)

## Estrutura do Projeto

- **PostgreSQL:** Servidor de banco de dados que armazena os dados dos profissionais.
- **Java 17 / Spring Boot 3.3.3:** Framework utilizado para construir a API REST.
- **Docker Compose:** Facilita a orquestração de contêineres, tanto da aplicação quanto do banco de dados.

## Comandos Úteis

- **Para parar os contêineres:**

  ```bash
  docker-compose down
  ```

- **Para reconstruir a aplicação sem usar cache:**

  ```bash
  docker-compose up --build --no-cache
  ```

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.3**
- **PostgreSQL 17**
- **Docker / Docker Compose**

## Licença

Este projeto está licenciado sob os termos da licença MIT.