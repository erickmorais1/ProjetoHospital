# Sistema Hospitalar

## Descrição do Projeto
O Sistema Hospitalar é uma aplicação desktop desenvolvida em Java para auxiliar no gerenciamento de consultas médicas. Ele permite o cadastro, busca, listagem, remoção e atualização de informações de consultas de forma centralizada e intuitiva.

O projeto foi desenvolvido seguindo conceitos de Programação Orientada a Objetos (POO), arquitetura em camadas e padrões de projeto (Design Patterns).

---

## Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Interface Gráfica:** JavaFX 22
* **Banco de Dados:** MySQL
* **Gerenciamento de Dependências:** Maven
* **IDE:** IntelliJ IDEA

---

## Funcionalidades

### Área do Médico
* Login de acesso ao sistema.
* Cadastro de consultas.
* Listagem de todas as consultas cadastradas.
* Busca de consultas por:
  * ID da consulta
  * CPF do paciente
  * CRM do médico
* Remoção de consultas.
* Adição de observações em consultas.
* Exibição de mensagens de sucesso e erro durante as operações.

### Área do Paciente
* Tela inicial de acesso.
* Navegação para funcionalidades específicas do paciente.

---

## Estrutura do Projeto

```text
src
 └── main
      ├── java
      │    └── br.edu.ufersa.ProjetoHospital
      │         ├── Controller
      │         ├── DAO
      │         ├── Facade
      │         ├── Service
      │         ├── Util
      │         ├── model
      │         └── App.java
      │
      └── resources
           └── fxml
```

---

## Arquitetura Utilizada

O sistema foi estruturado seguindo o padrão de arquitetura em camadas:

* **Controller:** Responsável pela comunicação entre a interface gráfica (JavaFX) e as regras de negócio.
  * *Exemplos:* `LoginController`, `MenuMedicoController`, `CadastrarConsultaController`
* **Service:** Responsável pelas regras de negócio do sistema.
  * *Exemplos:* `ConsultaService`, `MedicoService`, `PacienteService`
* **DAO (Data Access Object):** Camada responsável pelas consultas SQL e comunicação com o MySQL.
  * *Exemplos:* `ConsultaDAO`, `MedicoDAO`, `PacienteDAO`
* **Model:** Representação das entidades do sistema.
  * *Exemplos:* `Consulta`, `Medico`, `Paciente`, `Gerente`, `Endereco`

---

## Padrões de Projeto Utilizados

### 1. Singleton
Utilizado na classe `ConexaoBD` para garantir uma única instância de conexão com o banco de dados.
* **Benefícios:** Redução do consumo de recursos e centralização da conexão.

### 2. Facade
Utilizado através da classe `HospitalFacade`.
* **Benefícios:** Simplificação da comunicação entre Controllers e Services, reduzindo o acoplamento.

```java
facade.agendarConsulta();
facade.buscarConsulta();
facade.listarTodasAsConsultas();
```

### 3. Factory Method
Utilizado por meio do `FXMLLoader` do JavaFX e da classe `TrocaTela`.
* **Benefícios:** Centralização da criação de telas e redução de repetição de código.

---

## Conceitos de Programação Orientada a Objetos

* **Encapsulamento:** Atributos privados e acessados por meio de métodos públicos `getters` e `setters`.
* **Herança:** A classe `Pessoa` é utilizada como classe base para `Médico`, `Paciente` e `Gerente`.
* **Polimorfismo:** Utilização da interface `FacadeController` implementada pelos controllers do sistema.
* **Abstração:** Separação clara das responsabilidades entre Model, Controller, Service e DAO.

---

## Banco de Dados

O sistema utiliza as seguintes entidades principais:

* **Paciente:** CPF, Nome, Endereço.
* **Médico:** CRM, Nome, CPF, Salário.
* **Consulta:** ID, Paciente, Médico, Data, Status, Observação.

---

## Como Executar o Projeto

### Pré-requisitos
* Java JDK 21
* MySQL
* Maven

### Passos

1. Clone o repositório:
   ```bash
   git clone <url-do-repositorio>
   ```

2. Configure o banco de dados MySQL executando os scripts necessários.

3. Atualize as credenciais de acesso na classe `ConexaoBD`.

4. Execute o projeto utilizando Maven:
   ```bash
   mvn javafx:run
   ```
   *Ou execute diretamente a classe principal `App.java` por meio da IDE.*

---

## Desenvolvedores

Projeto desenvolvido para a disciplina de Programação Orientada a Objetos.
* **Instituição:** Universidade Federal Rural do Semi-Árido (UFERSA)

---

## Considerações Finais

Este projeto teve como objetivo aplicar conceitos de Programação Orientada a Objetos, persistência de dados, interfaces gráficas e padrões de projeto em uma aplicação real de gerenciamento hospitalar, proporcionando experiência prática no desenvolvimento de software utilizando Java.
