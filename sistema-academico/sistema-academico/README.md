# Sistema Acadêmico — API REST

Projeto acadêmico desenvolvido com Java 17, Spring Boot, Spring Web, Spring Data JPA e banco H2.

## Requisitos
- Java 17 ou superior
- Maven 3.8+ (ou usar o Maven integrado da IDE)

## Como executar
1. Extraia o arquivo ZIP.
2. Abra a pasta `sistema-academico` no IntelliJ IDEA, Eclipse ou VS Code com suporte a Java.
3. Aguarde o Maven baixar as dependências.
4. Execute a classe `SistemaAcademicoApplication.java`.

A API ficará disponível em `http://localhost:8080`.

O banco H2 em memória pode ser acessado em `http://localhost:8080/h2-console`.
- JDBC URL: `jdbc:h2:mem:academico`
- User Name: `sa`
- Password: deixe em branco

> Como o banco é em memória, os dados são apagados quando a aplicação é encerrada.

## Relacionamentos implementados
- `Professor` ↔ `PerfilProfessor`: `@OneToOne`.
- `Curso` → `Disciplina`: `@OneToMany` / `@ManyToOne`.
- `Aluno` ↔ `Disciplina`: `@ManyToMany`, com tabela intermediária `matriculas`.

## Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/professores` | Cadastrar professor com perfil |
| GET | `/professores` | Listar professores |
| GET | `/professores/{id}` | Buscar professor |
| PUT | `/professores/{id}` | Atualizar professor |
| DELETE | `/professores/{id}` | Excluir professor |
| POST | `/cursos` | Cadastrar curso |
| GET | `/cursos` | Listar cursos |
| GET | `/cursos/{id}` | Buscar curso |
| PUT | `/cursos/{id}` | Atualizar curso |
| DELETE | `/cursos/{id}` | Excluir curso |
| POST | `/disciplinas` | Cadastrar disciplina vinculada a um curso |
| GET | `/disciplinas` | Listar disciplinas |
| GET | `/disciplinas/{id}` | Buscar disciplina |
| PUT | `/disciplinas/{id}` | Atualizar disciplina |
| DELETE | `/disciplinas/{id}` | Excluir disciplina |
| POST | `/alunos` | Cadastrar aluno |
| GET | `/alunos` | Listar alunos |
| GET | `/alunos/{id}` | Buscar aluno |
| PUT | `/alunos/{id}` | Atualizar aluno |
| DELETE | `/alunos/{id}` | Excluir aluno |
| POST | `/alunos/{id}/disciplinas/{disciplinaId}` | Matricular aluno |
| DELETE | `/alunos/{id}/disciplinas/{disciplinaId}` | Cancelar matrícula |

## Exemplos para testar no Postman

### 1. Professor com perfil
`POST http://localhost:8080/professores`
```json
{
  "nome": "Carlos Silva",
  "email": "carlos@email.com",
  "perfil": {
    "especializacao": "Desenvolvimento Java",
    "telefone": "71999999999",
    "curriculo": "Professor especialista em programação."
  }
}
```

### 2. Curso
`POST http://localhost:8080/cursos`
```json
{
  "nome": "Desenvolvimento de Sistemas",
  "descricao": "Curso de tecnologia e programação."
}
```

### 3. Disciplina vinculada ao curso de ID 1
`POST http://localhost:8080/disciplinas`
```json
{
  "nome": "Programação Java",
  "cargaHoraria": 80,
  "cursoId": 1
}
```

### 4. Aluno
`POST http://localhost:8080/alunos`
```json
{
  "nome": "João Santos",
  "email": "joao@email.com"
}
```

### 5. Matrícula do aluno 1 na disciplina 1
`POST http://localhost:8080/alunos/1/disciplinas/1`

## Observações
- O endpoint de disciplina recebe `cursoId` e verifica se o curso existe antes de salvar.
- O relacionamento ManyToMany é persistido na tabela `matriculas`.
- O projeto utiliza `@JsonIgnore` nos lados inversos de alguns relacionamentos para evitar recursão infinita na conversão para JSON.
- Para um sistema de produção, recomenda-se acrescentar DTOs de resposta, validações de entrada mais completas, tratamento global de exceções e autenticação.
