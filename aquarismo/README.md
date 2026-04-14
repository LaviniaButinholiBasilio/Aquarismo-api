# 🐠 Aquarismo API

API REST para **Sistema de Gestão de Aquarismo** desenvolvida com Spring Boot 3.

> **1ª Entrega — v1.0** · Model e Banco de Dados

---

## 📋 Funcionalidades

| Módulo            | Descrição                                              |
|-------------------|--------------------------------------------------------|
| **Tanques**       | Cadastro e controle de tanques (doce, salgada, salobra)|
| **Peixes**        | Cadastro de peixes ornamentais com linhagem (mãe/pai)  |
| **Reprodução**    | Controle de reprodução e nascimento de filhotes        |
| **Vendas**        | Registro de vendas com atualização automática de status|
| **Parâmetros**    | Monitoramento de pH, temperatura, amônia, nitrito, etc.|

---

## 🏗️ Estrutura do Projeto

```
src/main/java/com/aquarismo/
├── config/              # Configurações (Jackson, etc.)
├── controller/v1/       # Endpoints REST versionados
├── dto/
│   ├── request/         # DTOs de entrada com validações
│   └── response/        # DTOs de saída
├── exception/           # Tratamento global de erros
├── model/               # Entidades JPA + Enums
├── repository/          # Interfaces Spring Data JPA
└── service/             # Regras de negócio

src/main/resources/
├── application.properties
└── db/migration/
    ├── V1__create_tables.sql       # DDL completo
    └── V2__seed_dados_iniciais.sql # Dados de exemplo
```

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.8+
- MySQL 8.0+

### 1. Criar o banco de dados
```sql
CREATE DATABASE aquarismo_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

### 2. Configurar credenciais
Edite `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/aquarismo_db?useSSL=false&serverTimezone=America/Sao_Paulo
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

### 3. Executar
```bash
mvn spring-boot:run
```
O Flyway criará as tabelas e inserirá os dados de exemplo automaticamente.

---

## 🌐 Endpoints da API

Base URL: `http://localhost:8080/api/v1`

### Tanques `/tanques`
| Método | Rota          | Descrição              |
|--------|---------------|------------------------|
| GET    | `/`           | Listar todos os tanques |
| GET    | `/{id}`       | Buscar por ID          |
| POST   | `/`           | Criar tanque           |
| PUT    | `/{id}`       | Atualizar tanque       |
| DELETE | `/{id}`       | Excluir tanque         |

### Peixes `/peixes`
| Método | Rota                  | Descrição                  |
|--------|-----------------------|----------------------------|
| GET    | `/`                   | Listar todos               |
| GET    | `/{id}`               | Buscar por ID              |
| GET    | `/tanque/{tanqueId}`  | Listar ativos no tanque    |
| GET    | `/buscar?termo=`      | Buscar por nome/científico |
| POST   | `/`                   | Cadastrar peixe            |
| PUT    | `/{id}`               | Atualizar peixe            |
| DELETE | `/{id}`               | Excluir peixe              |

### Reproduções `/reproducoes`
| Método | Rota          | Descrição                  |
|--------|---------------|----------------------------|
| GET    | `/`           | Listar todas               |
| GET    | `/{id}`       | Buscar por ID              |
| GET    | `/mae/{id}`   | Histórico por mãe          |
| GET    | `/pai/{id}`   | Histórico por pai          |
| POST   | `/`           | Registrar reprodução       |
| PUT    | `/{id}`       | Atualizar registro         |
| DELETE | `/{id}`       | Excluir registro           |

### Vendas `/vendas`
| Método | Rota                     | Descrição               |
|--------|--------------------------|-------------------------|
| GET    | `/`                      | Listar todas            |
| GET    | `/{id}`                  | Buscar por ID           |
| GET    | `/peixe/{peixeId}`       | Vendas de um peixe      |
| GET    | `/total?inicio=&fim=`    | Total vendido no período |
| POST   | `/`                      | Registrar venda         |
| DELETE | `/{id}`                  | Cancelar venda          |

### Parâmetros da Água `/parametros-agua`
| Método | Rota                        | Descrição                  |
|--------|-----------------------------|----------------------------|
| GET    | `/tanque/{tanqueId}`        | Histórico do tanque        |
| GET    | `/tanque/{tanqueId}/ultima` | Última medição do tanque   |
| GET    | `/{id}`                     | Buscar medição por ID      |
| POST   | `/`                         | Registrar medição          |
| DELETE | `/{id}`                     | Excluir medição            |

---

## 📦 Exemplo de Requisição

### Criar Tanque
```http
POST /api/v1/tanques
Content-Type: application/json

{
  "nome": "Tanque Principal",
  "capacidadeLitros": 150.0,
  "tipoAgua": "DOCE",
  "localizacao": "Sala de estar",
  "descricao": "Tanque comunitário tropical"
}
```

### Registrar Parâmetros da Água
```http
POST /api/v1/parametros-agua
Content-Type: application/json

{
  "tanqueId": 1,
  "ph": 7.2,
  "temperatura": 26.5,
  "amonia": 0.0,
  "nitrito": 0.02,
  "nitrato": 10.0,
  "observacoes": "Medição matinal"
}
```

---

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 3.2.4**
- **Spring Data JPA** + Hibernate
- **Spring Validation** (Bean Validation)
- **MySQL 8**
- **Flyway** (migrações de banco)
- **Lombok**
- **Maven**

---

## 🏷️ Versionamento

| Tag    | Descrição                              |
|--------|----------------------------------------|
| `v1.0` | Model, entidades JPA e banco de dados  |

