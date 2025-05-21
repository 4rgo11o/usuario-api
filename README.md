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

## 🧪 Collection Postman para Teste

Para facilitar o teste e a interação com a `usuario-api`, foi disponibilizada uma collection do Postman contendo os principais endpoints da aplicação.

### Como Obter a Collection:

Você pode baixar o arquivo da collection diretamente do repositório:

[Baixar Collection Postman (usuario-api.postman_collection.json)](https://github.com/4rgo11o/usuario-api/blob/main/docs/usuario-api.postman_collection.json)


### Como Importar a Collection:

1.  **Abra o Postman.**
2.  Clique no botão **"Import"** (Importar).
3.  Na janela de importação, **arraste e solte** o arquivo `usuario-api.postman_collection.json` que você baixou.
4.  Confirme a importação clicando em **"Import"**.

### Descrição dos Testes Manuais:

A collection está organizada para guiar o usuário através do fluxo de utilização da API:

1.  **Criação de Usuário:**
    * Utilize o endpoint `POST /api/v1/usuarios` para registrar um novo usuário com os dados fornecidos no corpo da requisição de exemplo.
2.  **Autenticação (Login):**
    * Após o cadastro, use `POST /api/v1/usuarios/login` para obter o token JWT. Este token será crucial para as próximas requisições.
3.  **Operações Autenticadas:**
    * **Importante:** Para as requisições `PUT /api/v1/usuarios/atualizar-usuario`, `PUT /api/v1/usuarios/alterar-senha` e `DELETE /api/v1/usuarios/deletar-usuario`, certifique-se de incluir o token JWT obtido no login no cabeçalho `Authorization` com o prefixo `Bearer` (ex: `Authorization: Bearer seu_token_aqui`).
    * Teste a atualização de informações do usuário, a alteração de senha e, por fim, a exclusão da conta.

Certifique-se de que a aplicação esteja em execução localmente (padrão: `http://localhost:8080`) antes de iniciar os testes.

## 📫 Contato

Criado por [Anderson L. Argollo](https://github.com/4rgo11o) • Pull requests e sugestões são bem-vindos!