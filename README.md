# Digital Wallet API
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

API REST para gerenciamento de carteiras digitais, permitindo criação de carteiras, depósitos e transferências entre usuários, com controle de concorrência via lock otimista.

## Sumário

- [Tecnologias](#tecnologias)
- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Executando o projeto](#executando-o-projeto)
- [Endpoints da API](#endpoints-da-api)
- [Tratamento de erros](#tratamento-de-erros)
- [Modelo de dados](#modelo-de-dados)
- [Roadmap](#roadmap)

## Tecnologias

- Java 21
- Spring Boot 4.1.0 (Web, Data JPA, Validation)
- PostgreSQL
- Flyway (versionamento de schema)
- Lombok
- Maven

## Funcionalidades

- Criação de carteira digital vinculada a um CPF único
- Consulta de carteira por id
- Depósito de saldo em uma carteira
- Transferência de saldo entre carteiras
- Registro de todas as movimentações (depósitos e transferências) como transações
- Controle de concorrência otimista (`@Version`) para evitar condições de corrida em atualizações de saldo

## Pré-requisitos

- JDK 21+
- PostgreSQL rodando localmente (ou acessível via rede)
- Não é necessário ter o Maven instalado — o projeto usa o Maven Wrapper (`mvnw` / `mvnw.cmd`)


## Executando o projeto

1. Clone o repositório e entre na pasta:
```bash
git clone https://github.com/HenriqueMPereira/digital-wallet-api.git
cd digital-wallet-api
```

2. Crie o banco de dados no PostgreSQL (as tabelas são criadas automaticamente pelo Flyway):
```sql
CREATE DATABASE digital_wallet;
```

3. Defina as variáveis de ambiente com os valores do **seu** ambiente (exemplo em PowerShell):

| Variável  | Descrição                    |
|-----------|-------------------------------|
| `DB_NAME` | Nome do banco de dados        |
| `DB_USER` | Usuário do PostgreSQL         |
| `DB_PASS` | Senha do usuário               |


```powershell
$env:DB_NAME = "digital_wallet"
$env:DB_USER = "postgres"
$env:DB_PASS = "sua_senha"
```

4. Rode a aplicação:
```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Endpoints da API

### Criar carteira

`POST /wallets`

```json
{
  "name": "Henrique Pereira",
  "cpf": "12345678909"
}
```

> O CPF acima é fictício, usado apenas para fins de documentação. O campo é validado pela anotação `@CPF` do Hibernate Validator, que confere apenas os dígitos verificadores (algoritmo módulo 11) — não consulta a Receita Federal nem exige um CPF real.

**Resposta `201 Created`**
```json
{
  "id": 1,
  "name": "Henrique Pereira",
  "balance": 0.00
}
```

### Consultar carteira

`GET /wallets/{id}`

**Resposta `200 OK`**
```json
{
  "id": 1,
  "name": "Henrique Pereira",
  "balance": 150.00
}
```

### Depositar saldo

`POST /wallets/{id}/deposits`

```json
{
  "amount": 150.00
}
```

**Resposta `201 Created`** — carteira com saldo atualizado.

### Transferir entre carteiras

`POST /transfers`

```json
{
  "sourceWalletId": 1,
  "destinationWalletId": 2,
  "amount": 50.00
}
```

**Resposta `201 Created`**
```json
{
  "id": 10,
  "type": "TRANSFER",
  "sourceWalletId": 1,
  "destinationWalletId": 2,
  "amount": 50.00
}
```

## Tratamento de erros

| Status | Cenário                                                        |
|--------|-----------------------------------------------------------------|
| `400`  | Dados de entrada inválidos, transferência entre a mesma carteira, ou saldo insuficiente |
| `404`  | Carteira não encontrada                                         |
| `409`  | CPF já cadastrado, ou conflito por modificação concorrente (lock otimista) |

## Modelo de dados

- **Wallet**: id, name, cpf (único), balance, version, createdAt
- **Transaction**: id, type (`DEPOSIT`, `TRANSFER`), sourceWallet, destinationWallet, amount, createdAt

## Roadmap

- [ ] Documentação interativa com Swagger/OpenAPI
- [ ] Extrato/histórico de transações (com paginação)
- [ ] Testes automatizados (unitários e de integração, incluindo teste de concorrência)
- [ ] Idempotência via header `Idempotency-Key` (Redis com TTL de 24h)
- [ ] Retry automático em conflitos de lock otimista (`@Retryable` do Spring Framework)
- [ ] Autenticação e autorização (Spring Security + JWT)
- [ ] Health check e métricas (Spring Boot Actuator)
- [ ] Containerização com Docker (multi-stage build + docker-compose)