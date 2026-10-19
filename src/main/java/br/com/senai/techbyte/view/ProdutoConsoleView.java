package br.com.senai.techbyte.view;

import br.com.senai.techbyte.controller.ProdutoController;
import br.com.senai.techbyte.model.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProdutoConsoleView {
    private final ProdutoController controller;
    private final Scanner scanner;

    public ProdutoConsoleView(ProdutoController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean executando = true;

        while (executando) {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> cadastrar();
                case "2" -> listar();
                case "3" -> buscarPorId();
                case "4" -> atualizar();
                case "5" -> remover();
                case "0" -> {
                    System.out.println("\nEncerrando o sistema TechByte... Até logo!");
                    executando = false;
                }
                default -> System.out.println("⚠️ Opção inválida! Tente novamente.");
            }
            System.out.println();
        }
    }

    private void exibirMenu() {
        String menu = """
            ==========================================
              TECHBYTE SOLUTIONS - CONTROLE DE ESTOQUE
            ==========================================
            1. Cadastrar Produto
            2. Listar Todos os Produtos
            3. Buscar Produto por ID
            4. Atualizar Produto
            5. Remover Produto
            0. Sair
            ==========================================
            """;
        System.out.print(menu);
    }

    private void cadastrar() {
        System.out.println("\n--- Cadastrar Novo Produto ---");
        try {
            System.out.print("Nome do produto: ");
            String nome = scanner.nextLine();

            System.out.print("Descrição/Especificações: ");
            String descricao = scanner.nextLine();

            System.out.print("Preço (R$): ");
            BigDecimal preco = new BigDecimal(scanner.nextLine().replace(",", "."));

            Produto criado = controller.cadastrarProduto(nome, descricao, preco);
            System.out.printf("✅ Produto registrado com sucesso! ID Gerado: %d\n", criado.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Regra de Negócio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n--- Inventário de Produtos ---");
        List<Produto> produtos = controller.listarProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado até o momento.");
            return;
        }

        System.out.printf("%-5s | %-25s | %-35s | %-12s\n", "ID", "NOME", "DESCRIÇÃO", "PREÇO");
        System.out.println("-".repeat(85));

        for (Produto p : produtos) {
            System.out.printf("%-5d | %-25s | %-35s | R$ %-9.2f\n",
                    p.getId(), p.getNome(), p.getDescricao(), p.getPreco());
        }
    }

    private void buscarPorId() {
        System.out.println("\n--- Consulta de Produto por ID ---");
        try {
            System.out.print("Informe o ID do produto: ");
            Long id = Long.parseLong(scanner.nextLine());

            Optional<Produto> produtoOpt = controller.buscarPorId(id);

            if (produtoOpt.isPresent()) {
                Produto p = produtoOpt.get();
                System.out.println("📦 Detalhes do Produto:");
                System.out.println("   ID: " + p.getId());
                System.out.println("   Nome: " + p.getNome());
                System.out.println("   Descrição: " + p.getDescricao());
                System.out.printf("   Preço: R$ %.2f\n", p.getPreco());
            } else {
                System.out.println("⚠️ Produto não encontrado para o ID informado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida. Por favor, digite um número de ID válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Validação: " + e.getMessage());
        }
    }

    private void atualizar() {
        System.out.println("\n--- Atualização de Produto ---");
        try {
            System.out.print("Informe o ID do produto a ser modificado: ");
            Long id = Long.parseLong(scanner.nextLine());

            Optional<Produto> existente = controller.buscarPorId(id);
            if (existente.isEmpty()) {
                System.out.println("⚠️ Produto não localizado.");
                return;
            }

            System.out.print("Novo Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Nova Descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("Novo Preço (R$): ");
            BigDecimal preco = new BigDecimal(scanner.nextLine().replace(",", "."));

            boolean alterado = controller.atualizarProduto(id, nome, descricao, preco);
            if (alterado) {
                System.out.println("✅ Produto atualizado com sucesso!");
            } else {
                System.out.println("❌ Não foi possível atualizar o produto.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Regra de Negócio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Erro no preenchimento dos dados: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\n--- Remoção de Produto ---");
        try {
            System.out.print("Informe o ID do produto que deseja remover: ");
            Long id = Long.parseLong(scanner.nextLine());

            boolean removido = controller.removerProduto(id);
            if (removido) {
                System.out.println("✅ Produto removido do estoque!");
            } else {
                System.out.println("⚠️ Produto não localizado para remoção.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Informe um número de ID válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Validação: " + e.getMessage());
        }
    }
}