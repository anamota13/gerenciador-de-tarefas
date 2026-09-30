# Gerenciador de Tarefas

Backend de uma aplicação para gerenciamento de tarefas do dia a dia, desenvolvido como projeto avaliativo do 1º bimestre do curso de Desenvolvimento de Software Multiplataforma.

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit
- Mockito

## Funcionalidades

O sistema permite:

- Criar tarefas;
- Listar tarefas;
- Buscar uma tarefa por ID;
- Alterar tarefas;
- Excluir tarefas.

## Estrutura da tarefa

Cada tarefa possui:

- Nome;
- Descrição;
- Status;
- Observações;
- Data de criação;
- Data de atualização.

### Status disponíveis

- `PENDENTE`
- `EM_ANDAMENTO`
- `CONCLUIDA`

## Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/tarefas` | Criar uma tarefa |
| GET | `/tarefas` | Listar todas as tarefas |
| GET | `/tarefas/{id}` | Buscar uma tarefa |
| PUT | `/tarefas/{id}` | Alterar uma tarefa |
| DELETE | `/tarefas/{id}` | Excluir uma tarefa |

## Banco de dados

O projeto utiliza PostgreSQL.

Banco utilizado:

```text
todo_db
```

O script de criação do banco e da tabela está disponível em:

```text
script/script.sql
```

## Executando o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/anamota13/gerenciador-de-tarefas.git
```

### 2. Acessar a pasta

```bash
cd gerenciador-de-tarefas
```

### 3. Configurar a senha do PostgreSQL

No PowerShell:

```powershell
$env:DB_PASSWORD="SUA_SENHA"
```

### 4. Executar o projeto

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação será executada em:

```text
http://localhost:8080
```

## Testes

Para executar os testes automatizados:

```powershell
.\mvnw.cmd test
```

Os testes abrangem as funcionalidades do serviço e do controller.

## Projeto acadêmico

Projeto desenvolvido para a disciplina de Laboratório de Desenvolvimento Multiplataforma – 6º DSM.

Faculdade de Tecnologia de Franca – FATEC Franca.
