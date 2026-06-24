# 🏥 Sistema Hospitalar

## 📝 Descrição do Projeto
O **Sistema Hospitalar** é uma aplicação desktop desenvolvida em Java para auxiliar no gerenciamento de consultas médicas. Ele permite o cadastro, busca, listagem, remoção e atualização de informações de consultas de forma centralizada e intuitiva.

O projeto foi desenvolvido seguindo conceitos rígidos de **Programação Orientada a Objetos (POO)**, arquitetura em camadas e padrões de projeto (Design Patterns).

---

## 🛠️ Tecnologias Utilizadas

![Java](https://shields.io)
![JavaFX](https://shields.io)
![MySQL](https://shields.io)
![Maven](https://shields.io)
![IntelliJ IDEA](https://shields.io)

* **Linguagem:** Java 21
* **Interface Gráfica:** JavaFX 22
* **Banco de Dados:** MySQL
* **Gerenciamento de Dependências:** Maven
* **IDE:** IntelliJ IDEA

---

## 🚀 Funcionalidades

### 👨‍⚕️ Área do Médico
* Login de acesso seguro ao sistema.
* Cadastro completo de consultas.
* Listagem em tempo real de todas as consultas cadastradas.
* Busca inteligente de consultas por:
  * ID da consulta
  * CPF do paciente
  * CRM do médico
* Remoção de consultas do banco de dados.
* Adição de observações detalhadas pós-consulta.
* Exibição de alertas e mensagens de sucesso/erro durante operações.

### 👥 Área do Paciente
* Tela inicial de acesso dedicada.
* Navegação fluida para funcionalidades específicas do paciente.

---

## 📂 Estrutura do Projeto

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

## 🏛️ Arquitetura Utilizadas

O sistema foi estruturado seguindo o padrão de **arquitetura em camadas** para garantir o baixo acoplamento:

* **`Controller`:** Comunicação direta entre a interface gráfica (JavaFX) e as regras de negócio.
  * *Exemplos:* `LoginController`, `MenuMedicoController`, `CadastrarConsultaController`
* **`Service`:** Concentra todas as regras de negócio do sistema.
  * *Exemplos:* `ConsultaService`, `MedicoService`, `PacienteService`
* **`DAO (Data Access Object)`:** Camada isolada responsável pelas consultas SQL e comunicação com o MySQL.
  * *Exemplos:* `ConsultaDAO`, `MedicoDAO`, `PacienteDAO`
* **`Model`:** Representação pura das entidades do sistema.
  * *Exemplos:* `Consulta`, `Medico`, `Paciente`, `Gerente`, `Endereco`

---

## 📐 Padrões de Projeto (Design Patterns)

### 1. Singleton
Aplicado na classe `ConexaoBD` para garantir que exista apenas uma instância ativa de conexão com o banco de dados.
* **Benefícios:** Economia drástica de recursos e centralização do fluxo de dados.

### 2. Facade
Implementado por meio da classe `HospitalFacade`.
* **Benefícios:** Simplifica o acesso aos métodos do sistema e reduz o acoplamento entre os Controllers e os Services.

```java
facade.agendarConsulta();
facade.buscarConsulta();
facade.listarTodasAsConsultas();
```

### 3. Factory Method
Utilizado via `FXMLLoader` do JavaFX e integrado na classe `TrocaTela`.
* **Benefícios:** Centraliza a renderização de novas janelas, elimina códigos repetidos e facilita manutenções visuais.

---

## ☕ Conceitos de POO Aplicados

* **Encapsulamento:** Atributos privados protegidos e acessados restritamente via métodos públicos `getters` e `setters`.
* **Herança:** Reutilização de código através da classe base `Pessoa`, herdada por `Médico`, `Paciente` e `Gerente`.
* **Polimorfismo:** Implementação da interface comum `FacadeController` pelos diferentes controladores da aplicação.
* **Abstração:** Divisão explícita de responsabilidades em componentes lógicos isolados (Model, View, Controller, Service, DAO).

---

## 🗄️ Modelagem do Banco de Dados

* **`Paciente`:** `CPF` (Chave), `Nome`, `Endereço`.
* **`Médico`:** `CRM` (Chave), `Nome`, `CPF`, `Salário`.
* **`Consulta`:** `ID` (Chave), `Paciente`, `Médico`, `Data`, `Status`, `Observação`.

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* **Java JDK 21** ou superior instalado.
* **MySQL Server** rodando localmente ou remotamente.
* **Apache Maven** configurado nas variáveis de ambiente.

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   ```

2. **Configure o banco de dados:** Execute o script SQL correspondente no seu servidor MySQL.

3. **Configure as credenciais:** Acesse a classe `ConexaoBD` e insira o usuário e a senha do seu banco de dados local.

4. **Execute a aplicação via Maven:**
   ```bash
   mvn javafx:run
   ```
   *Ou se preferir, execute diretamente o arquivo principal `App.java` através da sua IDE.*

---

## 🎓 Desenvolvimento
Projeto prático desenvolvido para a disciplina de **Programação Orientada a Objetos**.
* **Instituição:** Universidade Federal Rural do Semi-Árido (**UFERSA**)

---

## 🏁 Considerações Finais
Este software foi essencial para consolidar de forma prática a união entre interfaces gráficas ricas, persistência de dados em ambientes relacionais (SQL) e boas práticas de desenvolvimento de software utilizando o ecossistema Java moderno.
