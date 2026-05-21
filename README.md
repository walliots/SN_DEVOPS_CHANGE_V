# 🚀 API RESTful - Spring Boot + PostgreSQL

## Pré-requisitos
- Java 17
- Maven 3.8+
- PostgreSQL rodando localmente

---

## ⚙️ Configuração do Banco de Dados

Crie o banco no PostgreSQL antes de rodar:

```sql
CREATE DATABASE demo_db;
```

Edite o arquivo `src/main/resources/application.properties` com suas credenciais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/demo_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

O Hibernate vai criar a tabela `produtos` automaticamente ao iniciar (`ddl-auto=update`).

---

## ▶️ Como Rodar

```bash
# Na raiz do projeto
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 📦 Estrutura do Projeto

```
src/main/java/com/example/demo/
├── DemoApplication.java        ← Ponto de entrada
├── controller/
│   └── ProdutoController.java  ← Endpoints REST
├── service/
│   └── ProdutoService.java     ← Regras de negócio
├── repository/
│   └── ProdutoRepository.java  ← Acesso ao banco
├── model/
│   └── Produto.java            ← Entidade JPA
├── dto/
│   └── ProdutoDTO.java         ← Objetos de transferência
└── exception/
    ├── ResourceNotFoundException.java
    └── GlobalExceptionHandler.java
```

---

## 🧪 Endpoints para testar no Insomnia

### Base URL: `http://localhost:8080/api/produtos`

---

### ✅ POST - Criar produto
**URL:** `POST http://localhost:8080/api/produtos`

**Body (JSON):**
```json
{
  "nome": "Notebook Dell",
  "descricao": "Notebook i7 16GB RAM 512GB SSD",
  "preco": 4599.90,
  "estoque": 10
}
```

**Resposta 201 Created:**
```json
{
  "id": 1,
  "nome": "Notebook Dell",
  "descricao": "Notebook i7 16GB RAM 512GB SSD",
  "preco": 4599.90,
  "estoque": 10,
  "criadoEm": "2024-07-16T10:00:00",
  "atualizadoEm": "2024-07-16T10:00:00"
}
```

---

### 📋 GET - Listar todos os produtos
**URL:** `GET http://localhost:8080/api/produtos`

**Resposta 200 OK:** Array de produtos

---

### 🔍 GET - Buscar por nome (query param)
**URL:** `GET http://localhost:8080/api/produtos?nome=notebook`

---

### 🔎 GET - Buscar por ID
**URL:** `GET http://localhost:8080/api/produtos/1`

**Resposta 404 se não encontrado:**
```json
{
  "status": 404,
  "mensagem": "Produto não encontrado com id: 1",
  "timestamp": "2024-07-16T10:00:00"
}
```

---

### ✏️ PUT - Atualizar produto
**URL:** `PUT http://localhost:8080/api/produtos/1`

**Body (JSON):**
```json
{
  "nome": "Notebook Dell XPS",
  "descricao": "Notebook i9 32GB RAM 1TB SSD",
  "preco": 7999.90,
  "estoque": 5
}
```

---

### 🗑️ DELETE - Deletar produto
**URL:** `DELETE http://localhost:8080/api/produtos/1`

**Resposta 204 No Content** (sem body)

---

## ⚠️ Validações

Campos obrigatórios. Se inválido, retorna **400 Bad Request**:

```json
{
  "status": 400,
  "mensagem": "Erro de validação",
  "campos": {
    "nome": "Nome é obrigatório",
    "preco": "Preço é obrigatório"
  },
  "timestamp": "2024-07-16T10:00:00"
}
```
