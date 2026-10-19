💻 TechByte Solutions — Sistema de Gestão de EstoqueAplicação Console de Gestão de Produtos de Informática desenvolvida sob a Metodologia SENAI de Educação Profissional (MSEP). O projeto aplica os padrões de arquitetura corporativa MVC (Model-View-Controller), Service Layer e Repository Pattern, priorizando alto desacoplamento, baixo acoplamento e os princípios SOLID.📌 Contexto da Situação-Problema (MSEP SENAI)A TechByte Solutions, empresa especializada em montagem de hardware e suporte técnico, enfrentava falhas no controle de estoque devido ao uso de planilhas despadronizadas e anotações manuais. O resultado era inconsistência de preços, duplicidade de peças e falta de rastreabilidade em horários de pico.A Solução: Desenvolvimento de um sistema de terminal (console) leve, seguro e modular para gerenciar o inventário de produtos de informática (IDs, Nomes, Descrições e Preços), pronto para ser executado no servidor local e preparado para futuras migrações para banco de dados ou APIs REST.🚀 Recursos e Diferenciais TécnicosCRUD Completo: Cadastro, Listagem em Tabela, Consulta por ID, Atualização e Remoção.Inversão de Dependência (DIP / SOLID): Camada de serviço conversa com a interface do repositório, permitindo trocar o armazenamento em memória por MySQL/PostgreSQL sem alterar a regra de negócio.Tratamento Financeiro Correto: Uso de BigDecimal em vez de double ou float, eliminando erros de arredondamento monetário.Camada de Serviço Dedicada (Service): Centralização de validações (mínimo de caracteres, preços não negativos) e regras de domínio (etiquetagem automatizada [ALTO VALOR] para itens $\ge$ R$ 5.000,00).Recursos Modernos do Java (JDK 21+ / 25): Uso de Text Blocks (""") para menus, Switch Expressions (->) e Streams API.Arquitetura "De Dentro para Fora": Estruturado no modelo Bottom-Up para facilitar a assimilação pedagógica e testes unitários futuros.🏛️ Arquitetura do SistemaA aplicação está organizada em camadas bem delimitadas, garantindo que cada classe possua uma única responsabilidade (Single Responsibility Principle - SRP).Plaintexttechbyte-estoque/
└── src/
    └── main/
        └── java/
            └── br/
                └── com/
                    └── senai/
                        └── techbyte/
                            ├── model/
                            │   └── Produto.java               <-- Entidade do Domínio (POJO)
                            ├── repository/
                            │   ├── ProdutoRepository.java         <-- Interface (Contrato)
                            │   └── ProdutoMemoryRepository.java   <-- Implementação em RAM
                            ├── service/
                            │   └── ProdutoService.java           <-- Validações e Regras de Negócio
                            ├── controller/
                            │   └── ProdutoController.java        <-- Orquestrador de Chamadas
                            ├── view/
                            │   └── ProdutoConsoleView.java       <-- Interface Homem-Máquina (IHM)
                            └── Main.java                         <-- Injeção de Dependências & Start
🔄 Fluxo de Comunicação entre CamadasAbaixo está o ciclo de vida de uma operação de cadastro com regra de negócio acionada:Plaintext ┌────────────────────────┐
 │  ProdutoConsoleView    │  1. Lê os dados do teclado via Scanner
 └───────────┬────────────┘
             │ (Parâmetros brutos)
             ▼
 ┌────────────────────────┐
 │   ProdutoController    │  2. Repassa a requisição para a Service
 └───────────┬────────────┘
             │ (Valores)
             ▼
 ┌────────────────────────┐
 │     ProdutoService     │  3. Valida dados + Aplica tag [ALTO VALOR] (se R$ >= 5000)
 └───────────┬────────────┘
             │ (Objeto Produto montado)
             ▼
 ┌────────────────────────┐
 │  ProdutoRepository     │  4. Interface genérica acessada pela Service
 └───────────┬────────────┘
             │ (Contrato de chamada)
             ▼
 ┌────────────────────────┐
 │ProdutoMemoryRepository │  5. Atribui ID autoincrementado e grava na List<Produto>
 └────────────────────────┘
🏗️ Guia de Construção ("De Dentro para Fora")Para recriar este projeto do zero sem erros de compilação, siga a ordem lógica de desenvolvimento:PassosCamada / ArquivoFunção / Por quê criar nesta ordem?1ºmodel.ProdutoEntidade Base: Não depende de ninguém. Define o envelope de dados que trafega no sistema.2ºrepository.ProdutoRepositoryContrato (Interface): Define o que pode ser feito no banco de dados sem se preocupar com como.3ºrepository.ProdutoMemoryRepositoryImplementação Concreta: Implementa a persistência inicial em memória RAM (List<Produto>).4ºservice.ProdutoServiceRegras de Negócio: Centraliza validações de domínio e conecta a entidade à interface do repositório.5ºcontroller.ProdutoControllerOrquestrador: Recebe comandos da interface e aciona a camada de serviço.6ºview.ProdutoConsoleViewInterface Visual: Constrói menus, formata tabelas e gerencia entradas do usuário no terminal.7ºMainInjetor de Dependências: Conecta todas as instâncias em cadeia e dispara a aplicação.🛠️ Como Executar o ProjetoPré-requisitosJava JDK 21 ou superior (Preparado e testado no JDK 25).Git instalado.IDE de sua preferência (IntelliJ IDEA, VS Code ou Eclipse).Passos para Execução via TerminalClone o repositório:Bashgit clone https://github.com/seu-usuario/techbyte-estoque.git
cd techbyte-estoque
Navegue até a pasta de código-fonte:Bashcd src/main/java
Compile todas as classes:Bashjavac br/com/senai/techbyte/Main.java
Execute a aplicação:Bashjava br.com.senai.techbyte.Main
🧪 Roteiro de Teste de BancadaAo iniciar o programa, utilize o checklist de testes no terminal:Plaintext==========================================
  TECHBYTE SOLUTIONS - CONTROLE DE ESTOQUE
==========================================
1. Cadastrar Produto
2. Listar Todos os Produtos
3. Buscar Produto por ID
4. Atualizar Produto
5. Remover Produto
0. Sair
==========================================
Teste da Regra de Negócio de Alto Valor:Cadastre um produto com Preço = 7500.00 (ex: Placa de Vídeo RTX 4080).Verifique na listagem (Opção 2) se a descrição foi automaticamente prefixada com [ALTO VALOR].Teste de Validação de Preço:Tente cadastrar um produto com Preço = -150.00.Confirme se o sistema exibe o aviso da camada de serviço: ⚠️ Regra de Negócio: O preço do produto não pode ser negativo.Teste de Formatação Financeira:Digite o preço utilizando vírgula (ex: 499,90).Verifique se o parser da View sanitizou o dado e o formatou como R$ 499.90.🎓 Alinhamento Pedagógico (MSEP SENAI)Este projeto foi estruturado para desenvolver as seguintes Capacidades Técnicas e Socioemocionais:Análise e Solução de Problemas: Traduzir uma dor real de estoque empresarial em uma arquitetura de código sustentável.Domínio da Linguagem Java: Aplicação de orientação a objetos avançada, polimorfismo por interfaces, tratamento de exceções e manipulação de coleções concorrentes/imutáveis.Padrões de Mercado: Preparação direta para o ecossistema Spring Boot, que utiliza exatamente a mesma divisão de camadas (@RestController ➔ @Service ➔ @Repository).📄 LicençaEste projeto está sob a licença MIT — sinta-se livre para utilizar, modificar e distribuir para fins educacionais e comerciais.
