# Reservas de Restaurante

Sistema para gerenciamento de reservas de mesas em restaurantes, com backend desenvolvido em **Spring Boot**.

## Funcionalidades Principais

- **Autenticação e Cadastro de Usuários** (JWT)
  - Cadastro de novos usuários (com roles: ADMIN, USER)
  - Login e geração de token JWT
- **Gestão de Mesas** (apenas ADMIN)
  - Criar, listar, atualizar, deletar e buscar mesas
- **Reservas de Mesas**
  - Usuários autenticados podem reservar mesas disponíveis
  - Listagem de reservas do usuário
  - Cancelamento de reservas
- **Validações de Reserva**
  - Horário permitido: 10h às 21h
  - Dias permitidos: quarta a domingo
  - Capacidade da mesa e disponibilidade são checadas

## Principais Endpoints

- `/usuarios/login` — Login de usuário (retorna JWT)
- `/usuarios/registrar` — Cadastro de usuário
- `/mesas` — CRUD de mesas (restrito para ADMIN)
- `/reservas` — Reservar, listar e cancelar reservas (usuário autenticado)

## Endpoints da API

### Usuários (`/usuarios`)
- **POST** `/usuarios/login`  
  Efetua login e retorna um token JWT.
- **POST** `/usuarios/registrar`  
  Realiza o cadastro de um novo usuário.

### Mesas (`/mesas`)
- **POST** `/mesas`  
  Cria uma nova mesa. (Apenas ADMIN)
- **GET** `/mesas`  
  Lista todas as mesas.
- **PUT** `/mesas/{id}`  
  Atualiza uma mesa pelo ID. (Apenas ADMIN)
- **DELETE** `/mesas/{id}`  
  Deleta uma mesa pelo ID. (Apenas ADMIN)
- **GET** `/mesas/{id}`  
  Busca detalhes de uma mesa pelo ID. (Apenas ADMIN)

### Reservas (`/reservas`)
- **POST** `/reservas`  
  Cria uma nova reserva para o usuário autenticado.
- **GET** `/reservas`  
  Lista todas as reservas do usuário autenticado.
- **PUT** `/reservas/{id}/cancelar`  
  Cancela uma reserva do usuário autenticado pelo ID.

## Estrutura das Entidades

- **Usuário**
  - id, nome, email, senha (criptografada), role (ADMIN/USER)
- **Mesa**
  - id, nome, capacidade, status (DISPONIVEL, RESERVADA, INATIVA)
- **Reserva**
  - id, usuário, mesa, data/hora da reserva, status (ATIVO, CANCELADO)

## Segurança

- Autenticação via JWT
- Spring Security configurado para proteger endpoints
- CORS liberado para o frontend em `http://127.0.0.7:5501`

## Banco de Dados

- PostgreSQL
- Migrações gerenciadas com Flyway (`src/main/resources/db/migration`)
- Tabelas: `usuarios`, `mesas`, `reservas`

## Dependências Principais

- Spring Boot (Web, Data JPA, Security, Validation)
- PostgreSQL Driver
- Flyway
- JWT (com.auth0:java-jwt)

## Configuração

No arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}/reservas_de_restaurante
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
api.security.token.secret=${TOKEN_SENHA}
```

Defina as variáveis de ambiente `DB_HOST`, `DB_USER`, `DB_PASSWORD` e `TOKEN_SENHA` antes de rodar a aplicação.

## Como rodar

1. Configure o banco de dados PostgreSQL e as variáveis de ambiente.
2. Execute as migrações Flyway (automático ao rodar o Spring Boot).
3. Rode a aplicação:

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`. 