# 💻 TechByte Solutions — Sistema de Controle de Estoque

![Java Version](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Architecture](https://img.shields.io/badge/Arquitetura-MVC%20%2B%20Service%20%2B%20Repository-blue?style=for-the-badge)
![Methodology](https://img.shields.io/badge/Metodologia-MSEP%20SENAI-red?style=for-the-badge)
![License](https://img.shields.io/badge/Licen%C3%A7a-MIT-green?style=for-the-badge)

> **Situação-Problema (MSEP SENAI):** Solução de gerenciamento de inventário de produtos de informática para o terminal local da TechByte Solutions, desenvolvida com boas práticas de Engenharia de Software, desacoplamento total de camadas e os recursos mais recentes do Java SE 25.

---

## 📌 Sobre o Projeto

A **TechByte Solutions**, especializada em suporte e montagem de computadores de alta performance, enfrentava divergências no controle de estoque de hardware (processadores, placas de vídeo, SSDs e memórias). O controle por planilhas causava duplicidade e perdas financeiras.

Este projeto entrega uma **Aplicação Console Robusta** organizada sob os padrões **MVC + Service + Repository**, aplicando o princípio da **Inversão de Dependência (DIP)** do SOLID para garantir baixo acoplamento e facilidade de evolução para futuras interfaces gráficas ou bancos de dados relacionais.

---

## 🎯 Funcionalidades (CRUD Completo)

- [x] **Cadastrar Produto:** Atribuição automática de ID, validação do nome/preço e enriquecimento de dados (*Tag de Alto Valor* para itens $\ge$ R$ 5.000,00).
- [x] **Listar Estoque:** Exibição do inventário em formato de tabela tabulada no terminal.
- [x] **Buscar por ID:** Consulta individual de detalhes de produtos com tratamento seguro via `Optional<T>`.
- [x] **Atualizar Produto:** Edição segura de atributos com reaplicação das regras de negócio.
- [x] **Remover Produto:** Deleção segura pelo ID da peça.
- [x] **Tratamento Defensivo de Erros:** Prevenção contra preço negativo, IDs inválidos e erros de digitação via terminal.

---

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
                            │   └── Produto.java               # Entidade POJO (Atributos e Encapsulamento)
                            ├── repository/
                            │   ├── ProdutoRepository.java         # Interface (Contrato de Persistência)
                            │   └── ProdutoMemoryRepository.java   # Implementação Concreta (Lista em RAM)
                            ├── service/
                            │   └── ProdutoService.java           # Lógica e Regras de Negócio
                            ├── controller/
                            │   └── ProdutoController.java         # Orquestrador de Chamadas
                            ├── view/
                            │   └── ProdutoConsoleView.java        # Interface do Usuário (IHM Console)
                            └── Main.java                          # Ponto de Entrada e Injeção de Dependências
