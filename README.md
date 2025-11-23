![Pipeline CI - Crypto Wallet](https://github.com/AndreFerrarez/transacoes/actions/workflows/pipeline.yml/badge.svg)
# Projeto Crypto Wallet — Etapa 3

## 🎯 Objetivo
Evoluir o projeto monolítico existente para uma **arquitetura de microsserviços**, criando um novo serviço independente responsável pelo **histórico de transações**.

Esta etapa demonstra a separação de responsabilidades, a comunicação entre serviços e a modularização do sistema.

---
# 🧩 Etapa 4 — Refatoração para Arquitetura Orientada a Eventos

## 📘 Contexto Geral
Esta etapa refatora o sistema **Crypto Wallet**, transformando a comunicação entre os microsserviços `wallet-service` e `historico-service` em um modelo **orientado a eventos**, utilizando **RabbitMQ** como *message broker*.  
O objetivo é aumentar **desempenho, escalabilidade e desacoplamento** entre os componentes do sistema.

---

## 🎯 Objetivo
> Refatorar o sistema existente para uma **Arquitetura Orientada a Eventos**,  
> substituindo as chamadas HTTP síncronas por **mensagens assíncronas** via RabbitMQ.

## 🧩 Implementações Principais

### 1. **Publicação de Eventos (wallet-service)**
- Criado `TransacaoCriadaEvent` para representar as operações.
- Implementado `TransacaoEventPublisher` com `RabbitTemplate` para enviar eventos.
- Adicionado `Jackson2JsonMessageConverter` para enviar mensagens em JSON legível.
- Ajustado `TransacaoService` para publicar eventos nos casos:
  - `CREATE` → Nova transação criada
  - `DELETE` → Transação removida

### 2. **Consumo de Eventos (historico-service)**
- Criado `TransacaoEventListener` com `@RabbitListener` para escutar a fila `transacoes.queue`.
- Recebe os eventos JSON e persiste automaticamente em `TransacaoHistorico`.
- Registro de todas as operações (`CREATE`, `DELETE`) no banco H2 (`historicodb`).

### Para subir o container 
- docker run -d --hostname rabbitmq-local --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

-Painel de controle: http://localhost:15672
(Usuário: guest, Senha: guest)



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


---
# 🚀 Etapa 5 — Implantação e Operação em Produção

![Pipeline CI - Crypto Wallet](https://github.com/AndreFerrarez/transacoes/actions/workflows/pipeline.yml/badge.svg)

## 🎯 Objetivo
A etapa final focou na preparação do sistema para um ambiente produtivo, garantindo **portabilidade, escalabilidade e confiabilidade**. O sistema deixou de rodar apenas localmente via IDE para ser orquestrado em containers, com monitoramento ativo e pipeline de entrega automatizada.

## 🛠️ O Que Foi Implementado

### 1. 📦 Conteinerização (Docker)
- Criação de **Dockerfiles** otimizados com *Multi-Stage Build* (separando a fase de compilação Maven da fase de execução com JRE leve).
- Geração de imagens independentes para `wallet-service` e `historico-service`.

### 2. ☸️ Orquestração (Kubernetes)
- Desenvolvimento de manifestos (`k8s-deployment.yaml`) para gerenciamento do cluster.
- Configuração de **Services (LoadBalancer)** para expor as aplicações e o Broker.
- Definição de variáveis de ambiente dinâmicas para comunicação interna entre pods (Service Discovery).

### 3. 🔍 Monitoramento e Observabilidade
- Integração com **Spring Boot Actuator**.
- Exposição de endpoints de saúde (`/actuator/health`) que monitoram em tempo real:
  - Conectividade com o RabbitMQ.
  - Estado do Banco de Dados (H2).
  - Espaço em disco e disponibilidade do serviço.

### 4. 🔄 CI/CD (GitHub Actions)
- Implementação de pipeline de **Integração Contínua** automatizada.
- A cada `push` na branch principal, o workflow:
  1. Baixa o código.
  2. Configura o ambiente Java 21 (Temurin).
  3. Executa a compilação e testes de build dos microsserviços.

---

## ⚙️ Como Executar o Ambiente (Kubernetes)

Pré-requisitos: Docker Desktop com Kubernetes habilitado (`kubectl`).

1. **Subir todo o ecossistema:**
   ```bash
   kubectl apply -f k8s-deployment.yaml

2. **Verificar Pods:**
  ```bash
  kubectl get pods --watch
  Aguarde até que todos estejam com status Running..

3. **Acessar os servicos:**

  RabbitMQ Dashboard: http://localhost:15672

  Wallet Health: http://localhost:8080/actuator/health

  Histórico Health: http://localhost:8081/actuator/health

4. **Encerrar o ambiente:**
  ```bash
  kubectl delete -f k8s-deployment.yaml


