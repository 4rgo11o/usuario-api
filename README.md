# 🔐 API de Gestão de Usuários

Esta API permite o gerenciamento de usuários (perfis cliente e/ou proprietário), incluindo cadastro, login, alteração de senha, atualização e exclusão.  
As rotas protegidas requerem autenticação via **JWT**.

---

## 🔗 Base URL

```
http://localhost:8080
```

---

## 📌 Endpoints

### 📥 POST `/api/v1/usuarios` – Cadastrar Usuário

Registra um novo usuário no sistema.

#### Corpo da Requisição:
```json
{
  "nome": "João da Silva",
  "email": "joao@email.com",
  "cpf": "12345678901",
  "login": "joaosilva",
  "senha": "senhaForte123",
  "endereco": {
    "rua": "Rua A",
    "numero": "123",
    "cep": "12345678",
    "cidade": "São Paulo",
    "estado": "SP"
  },
  "roles": ["cliente"]
}
```

#### Respostas:
- `201`: Usuário cadastrado com sucesso.
- `400`: Requisição inválida ou dados incorretos.

---

### 🔐 POST `/api/v1/usuarios/login` – Login

Gera um token JWT com as credenciais do usuário.

#### Corpo da Requisição:
```json
{
  "login": "joaosilva",
  "senha": "senhaForte123"
}
```

#### Respostas:
- `200`: Usuário autenticado com sucesso.
- `401`: Credenciais inválidas.

---

### 🛠 PUT `/api/v1/usuarios/atualizar-usuario` – Atualizar Usuário

Permite ao usuário autenticado atualizar seus dados.  
🔒 Requer token JWT.

#### Exemplo de requisição:
```json
{
  "nome": "João Silva",
  "email": "novo@email.com",
  "login": "joaosilva",
  "endereco": {
    "rua": "Rua Nova",
    "numero": "999",
    "cep": "87654321",
    "cidade": "Rio de Janeiro",
    "estado": "RJ"
  }
}
```

#### Respostas:
- `200`: Usuário atualizado com sucesso.
- `400`: Dados inválidos.
- `401`: Token ausente ou inválido.

---

### 🔑 PUT `/api/v1/usuarios/alterar-senha` – Alterar Senha

Altera a senha do usuário autenticado.  
🔒 Requer token JWT.

#### Exemplo de requisição:
```json
{
  "senhaAtual": "senhaAntiga123",
  "novaSenha": "novaSenha456"
}
```

#### Respostas:
- `200`: Senha alterada com sucesso.
- `401`: Senha atual incorreta ou token ausente/inválido.

---

### ❌ DELETE `/api/v1/usuarios/deletar-usuario` – Deletar Conta

Deleta o usuário autenticado.  
🔒 Requer token JWT.

#### Respostas:
- `204`: No Content – Usuário excluído com sucesso.
- `401`: Token ausente ou inválido.

---

## 🔧 Autenticação

Todos os endpoints protegidos requerem envio do token JWT:

```http
Authorization: Bearer seu_token_aqui
```

---

## 🛠 Tecnologias Utilizadas

Este projeto foi desenvolvido com as seguintes tecnologias e bibliotecas:

### ☕ Backend
- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security**
- **JWT (Auth0 Java JWT)**
- **Spring Validation**
- **Springdoc OpenAPI v2.8.6**
- **Postgres**
- **Lombok**

---

### ⚙️ Build e Gerenciamento
- **Maven**

---

## 📚 Documentação interativa

Acesse a interface do Swagger em:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🚀 Como rodar o projeto com Docker

### Pré-requisitos

- [Docker Compose](https://docs.docker.com/compose/)

### Passo a passo

1.  Execute o comando:

```bash
docker-compose up
```

2. A API estará acessível em:

```
http://localhost:8080
```

---

## 📫 Contato

Criado por [Anderson L. Argollo](https://github.com/4rgo11o) • Pull requests e sugestões são bem-vindos!