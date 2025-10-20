# Projeto Crypto Wallet — Etapa 3

## 🎯 Objetivo
Evoluir o projeto monolítico existente para uma **arquitetura de microsserviços**, criando um novo serviço independente responsável pelo **histórico de transações**.

Esta etapa demonstra a separação de responsabilidades, a comunicação entre serviços e a modularização do sistema.

---

## 🗂 Estrutura do Repositório

Projeto/
├── wallet/ # Aplicação principal (porta 8080)
├── historico-service/ # Microsserviço de histórico (porta 8081)
└── pdfs/ # Relatórios e documentos do projeto


## ⚙️ Serviços

### Wallet Service
- Responsável pela gestão de transações (depósitos, saques e saldo).  
- Envia os registros de histórico para o microsserviço via **Feign Client**.  
- Banco de dados: **H2 em memória** (`walletdb`).  
- Utiliza **Spring Boot**, **Spring Data JPA**, **Spring Web** e **Feign Client**.  

### Historico Service
- Registra e consulta históricos de transações.  
- Banco de dados independente (**H2** – `historicodb`).  
- Expõe endpoints REST acessados pelo `wallet`.  
- Utiliza **Spring Boot**, **Spring Data JPA** e **Spring Web**.  

---

## 🚀 Como Executar

### 1️⃣ Iniciar o microsserviço de histórico
bash
cd historico-service
mvn spring-boot:run

Acessar: http://localhost:8081

cd wallet
mvn spring-boot:run

Acessar: http://localhost:8080

POST http://localhost:8080/transacoes
Content-Type: application/json

{
  "tipo": "DEPOSITO",
  "valor": 1000,
  "moeda": "BTC"
}

## Consultar hitorico

GET http://localhost:8081/historico


# Tecnologias Utilizadas

Java 21

Spring Boot 3.5.4

Spring Data JPA

Spring Web

Spring Cloud OpenFeign

H2 Database

Maven

IntelliJ IDEA


## Resumo Final

Esta entrega introduz o primeiro passo da migração para microsserviços.
O projeto agora possui:

Dois módulos independentes (wallet e historico-service);

Comunicação entre serviços via HTTP;

Bancos de dados isolados;

Estrutura escalável e pronta para futuras integrações.

O sistema está totalmente funcional e preparado para evoluir nas próximas etapas do projeto.
