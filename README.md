# Projeto Crypto Wallet API

Este projeto é uma API RESTful simples para gerenciar uma carteira de transações de criptomoedas. Desenvolvido como parte do Bloco de "Engenharia de Softwares Escaláveis", ele serve como uma aplicação monolítica inicial construída com Spring Boot, seguindo as melhores práticas de desenvolvimento de software.

## ✨ Funcionalidades 

- [x] Criar novas transações (Depósito ou Saque).
- [x] Listar todas as transações registradas.
- [x] Calcular o saldo consolidado para uma moeda específica.
- [x] Excluir uma transação pelo seu ID.

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA / Hibernate**
- **Spring Web**
- **Maven**
- **H2 Database (In-Memory)**
- **Lombok**

## 🔧 Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
- [JDK 17 ou superior](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8 ou superior](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads) (ou outra ferramenta de controle de versão)

## ▶️ Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/AndreFerrarez/transacoes.git
   ```

2. **Navegue até a pasta raiz do projeto:**
   ```bash
   cd wallet
   ```

3. **Execute o comando Maven para iniciar a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

4. A API estará disponível em `http://localhost:8080`.

## 🔌 Endpoints da API

A API fornece os seguintes endpoints para interação:

| Método HTTP | Endpoint                         | Descrição                                         | Exemplo de Body (Payload)                                                                         |
|-------------|----------------------------------|-----------------------------------------------------|---------------------------------------------------------------------------------------------------|
| `GET`       | `/transacoes`                    | Retorna uma lista de todas as transações.           | N/A                                                                                               |
| `POST`      | `/transacoes`                    | Cria uma nova transação.                            | `{ "tipo": "DEPOSITO", "valor": 1.5, "moeda": "BTC" }`                                            |
| `GET`       | `/transacoes/saldo/{moeda}`      | Calcula e retorna o saldo para uma moeda específica.| N/A                                                                                               |
| `DELETE`    | `/transacoes/{id}`               | Exclui uma transação com base no seu `id`.          | N/A                                                                                               |

## 🗃️ Acesso ao Banco de Dados

O projeto utiliza um banco de dados H2 em memória. É possível acessá-lo através de um console web enquanto a aplicação estiver em execução.

1. **URL de acesso:** `http://localhost:8080/h2-console`
2. **Preencha os campos de login da seguinte forma:**
   - **JDBC URL:** `jdbc:h2:mem:testdb`
   - **User Name:** `sa`
   - **Password:** (deixe em branco)

## 🏗️ Estrutura do Projeto

O projeto segue uma arquitetura em três camadas para garantir a separação de responsabilidades e a manutenibilidade do código:

- `infnet.wallet.wallet.controller`: Camada responsável por expor os endpoints REST e lidar com as requisições HTTP.
- `infnet.wallet.wallet.service`: Camada que contém a lógica de negócio da aplicação.
- `infnet.wallet.wallet.repository`: Camada de acesso a dados, responsável pela comunicação com o banco de dados através do Spring Data JPA.
- `infnet.wallet.wallet.model`: Contém as entidades de domínio (JPA) que modelam os dados do negócio.

## 👤 Autor

Desenvolvido por **Andre A. Ferrarez**.
