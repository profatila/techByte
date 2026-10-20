# 💻 TechByte Solutions — Sistema de Controle de Estoque

**Situação-Problema:** Solução de gerenciamento de inventário de produtos de informática para o terminal local da TechByte Solutions, desenvolvida com boas práticas de Engenharia de Software, desacoplamento total de camadas e os recursos mais recentes do Java SE 25.

---

## 📌 Sobre o Projeto

A TechByte Solutions, especializada em suporte e montagem de computadores de alta performance, enfrentava divergências no controle de estoque de hardware (processadores, placas de vídeo, SSDs e memórias). O controle por planilhas causava duplicidade e perdas financeiras.

Este projeto entrega uma **Aplicação Console** organizada sob os padrões **MVC + Service + Repository**, aplicando o princípio da **Inversão de Dependência (DIP)** do SOLID para garantir baixo acoplamento e facilidade de evolução para futuras interfaces gráficas ou bancos de dados relacionais.

---

## 🎯 Funcionalidades (CRUD Completo)

- [x] **Cadastrar Produto:** Atribuição automática de ID, validação do nome/preço e enriquecimento de dados (Tag de Alto Valor para itens ≥ R\$ 5.000,00).
- [x] **Listar Estoque:** Exibição do inventário em formato de tabela tabulada no terminal.
- [x] **Buscar por ID:** Consulta individual de detalhes de produtos com tratamento seguro via `Optional<T>`.
- [x] **Atualizar Produto:** Edição segura de atributos com reaplicação das regras de negócio.
- [x] **Remover Produto:** Deleção segura pelo ID da peça.
- [x] **Tratamento Defensivo de Erros:** Prevenção contra preço negativo, IDs inválidos e erros de digitação via terminal.

---
    %% Relacionamentos
    ProdutoMemoryRepository ..|> ProdutoRepository : implementa
    ProdutoMemoryRepository "1" o-- "*" Produto : armazena
    ProdutoService "1" --> "1" ProdutoRepository : aciona via interface
    ProdutoController "1" --> "1" ProdutoService : orquestra
    ProdutoConsoleView "1" --> "1" ProdutoController : invoca
    Main ..> ProdutoMemoryRepository : instancia
    Main ..> ProdutoService : injeta
    Main ..> ProdutoController : injeta
    Main ..> ProdutoConsoleView : injeta

## 🏗️ Arquitetura do Sistema

O projeto foi construído respeitando a divisão de responsabilidades em **5 Camadas Distintas**:

```text
techbyte-estoque/
└── src/
    └── main/
        └── java/
            └── br/
                └── com/
                    └── senai/
                        └── techbyte/
                            ├── model/
                            │   └── Produto.java             # Entidade POJO (Atributos e Encapsulamento)
                            ├── repository/
                            │   ├── ProdutoRepository.java       # Interface (Contrato de Persistência)
                            │   └── ProdutoMemoryRepository.java # Implementação Concreta (Lista em RAM)
                            ├── service/
                            │   └── ProdutoService.java       # Lógica e Regras de Negócio
                            ├── controller/
                            │   └── ProdutoController.java    # Orquestrador de Chamadas
                            ├── view/
                            │   └── ProdutoConsoleView.java   # Interface do Usuário (IHM Console)
                            └── Main.java                     # Ponto de Entrada e Injeção de Dependências
```

---

## 🔄 Fluxo de Comunicação entre Camadas

As dependências são invertidas e injetadas em cadeia pelo arquivo `Main.java`, garantindo que nenhuma camada conheça os detalhes internos da outra:

```text
 ┌──────────────────────┐
 │ ProdutoConsoleView   │ (Interface de Usuário)
 └──────────┬───────────┘
            │ Chama
            ▼
 ┌──────────────────────┐
 │  ProdutoController   │ (Intermediário de Chamadas)
 └──────────┬───────────┘
            │ Chama
            ▼
 ┌──────────────────────┐
 │    ProdutoService    │ (Regras de Negócio & Validações)
 └──────────┬───────────┘
            │ Usa Contrato (Interface)
            ▼
 ┌──────────────────────┐
 │  ProdutoRepository   │ (Interface / Abstração)
 └──────────▲───────────┘
            │ Implementa
 ┌──────────┴────────────┐
 │ProdutoMemoryRepository│ (Armazenamento em Memória RAM)
 └───────────────────────┘
            ▲
            │ Trafega
 ┌──────────┴───────────┐
 │       Produto        │ (Entidade do Domínio / Model)
 └──────────────────────┘
```

---

## 🛠️ Tecnologias e Recursos do Java 25 Utilizados

| Recurso | Aplicação no Projeto |
| :--- | :--- |
| **Java SE 25 (JDK 25)** | Linguagem base e ambiente de execução moderno. |
| **BigDecimal** | Precisão absoluta para valores monetários, evitando erros de ponto flutuante (`double`/`float`). |
| **Text Blocks (`"""`)** | Construção de menus no terminal de forma legível e sem concatenações manuais. |
| **Switch Expressions (`->`)** | Tratamento limpo e direto da escolha de opções do usuário. |
| **`Optional<T>`** | Busca defensiva de produtos evitando o clássico erro `NullPointerException`. |
| **Inversão de Dependência (DIP)** | O Controller e a Service dependem da interface `ProdutoRepository`, facilitando a migração para MySQL/PostgreSQL. |

---

## 📊 Arquitetura Limpa vs. Código Macarronada

| Critério | Código Macarronada (Arquivo Único) | Arquitetura Atual (MVC + Service + Repo) |
| :--- | :--- | :--- |
| **Organização** | Métodos, menus e vetores misturados. | 5 camadas com responsabilidade única. |
| **Manutenibilidade** | Qualquer alteração pode quebrar o sistema todo. | Alterar a tela não afeta a regra de negócio nem os dados. |
| **Persistência** | Usa vetores paralelos propensos à dessincronização. | Encapsulado em Repositório via coleções de objetos. |
| **Troca de Banco de Dados** | Exige reescrever 100% do código. | Troca-se apenas 1 linha no arquivo `Main.java`. |

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* **JDK 25** (ou superior) instalado e configurado no PATH.
* IDE **IntelliJ IDEA**, **Eclipse** ou terminal/prompt de comando.

### Passos via Terminal

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/techbyte-estoque.git
   cd techbyte-estoque
   ```

2. **Compile as classes:**
   ```bash
   javac -d bin src/main/java/br/com/senai/techbyte/**/*.java src/main/java/br/com/senai/techbyte/*.java
   ```

3. **Execute a aplicação:**
   ```bash
   java -cp bin br.com.senai.techbyte.Main
   ```

---

## 🧪 Roteiro de Testes para Validação

1. **Cadastrar Produto Comum:**
   * **Nome:** `Memória RAM DDR5 16GB`
   * **Descrição:** `6000MHz CL30`
   * **Preço:** `499,90`

2. **Cadastrar Produto de Alto Valor:**
   * **Nome:** `Placa de Vídeo RTX 4080`
   * **Descrição:** `16GB GDDR6X`
   * **Preço:** `7500,00` *(Observe a adição automática da tag `[ALTO VALOR]`)*

3. **Listar Produtos:**
   * Selecione a **opção 2** e confira a formatação da tabela no console.

4. **Validar Exceções:**
   * Tente cadastrar um produto com **preço negativo** (`-50,00`) ou **nome com menos de 3 caracteres** para conferir o disparo das regras de negócio.

---

## 📝 Licença

Este projeto foi desenvolvido para fins educacionais. Distribuído sob a licença **MIT**.
