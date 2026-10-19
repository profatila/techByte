import java.util.ArrayList;
import java.util.Scanner;

public class MacarronadaEstoque {

    // Listas paralelas globais para simular a tabela (Zero POO/Model)
    static ArrayList<Long> ids = new ArrayList<>();
    static ArrayList<String> nomes = new ArrayList<>();
    static ArrayList<String> descricoes = new ArrayList<>();
    static ArrayList<Double> precos = new ArrayList<>(); // Ponto flutuante para dinheiro!
    static long contadorId = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("  TECHBYTE - ESTOQUE MACARRONADA (MONOLITO)");
            System.out.println("==========================================");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Buscar Produto por ID");
            System.out.println("4. Atualizar Produto");
            System.out.println("5. Remover Produto");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine().trim();

            if (opcao.equals("1")) {
                // --- CADASTRAR (UI + Validação + Regra + Persistência juntas) ---
                System.out.println("\n--- Cadastrar Produto ---");
                System.out.print("Nome: ");
                String nome = scanner.nextLine();

                System.out.print("Descrição: ");
                String desc = scanner.nextLine();

                System.out.print("Preço: ");
                double preco = 0;
                try {
                    preco = Double.parseDouble(scanner.nextLine().replace(",", "."));
                } catch (Exception e) {
                    System.out.println("❌ Preço inválido!");
                    continue;
                }

                // Validações de regra de negócio misturadas com a tela
                if (nome == null || nome.trim().length() < 3) {
                    System.out.println("❌ Nome deve ter no mínimo 3 caracteres!");
                } else if (preco < 0) {
                    System.out.println("❌ Preço não pode ser negativo!");
                } else {
                    // Regra de negócio de "Alto Valor" misturada aqui no meio
                    if (preco >= 5000 && !desc.contains("[ALTO VALOR]")) {
                        desc = "[ALTO VALOR] " + desc;
                    }

                    // Salvando diretamente nas listas paralelas globais
                    ids.add(contadorId);
                    nomes.add(nome.trim());
                    descricoes.add(desc.trim());
                    precos.add(preco);

                    System.out.println("✅ Produto salvo com ID: " + contadorId);
                    contadorId++;
                }

            } else if (opcao.equals("2")) {
                // --- LISTAR (UI + Acesso aos Dados juntos) ---
                System.out.println("\n--- Lista de Produtos ---");
                if (ids.size() == 0) {
                    System.out.println("Nenhum produto cadastrado.");
                } else {
                    System.out.printf("%-5s | %-20s | %-30s | %-10s\n", "ID", "NOME", "DESCRIÇÃO", "PREÇO");
                    System.out.println("-".repeat(75));
                    for (int i = 0; i < ids.size(); i++) {
                        System.out.printf("%-5d | %-20s | %-30s | R$ %-8.2f\n",
                                ids.get(i), nomes.get(i), descricoes.get(i), precos.get(i));
                    }
                }

            } else if (opcao.equals("3")) {
                // --- BUSCAR POR ID ---
                System.out.println("\n--- Buscar Produto ---");
                System.out.print("Digite o ID: ");
                try {
                    long idBusca = Long.parseLong(scanner.nextLine());
                    boolean achou = false;

                    for (int i = 0; i < ids.size(); i++) {
                        if (ids.get(i) == idBusca) {
                            System.out.println("📦 Encontrado:");
                            System.out.println("   ID: " + ids.get(i));
                            System.out.println("   Nome: " + nomes.get(i));
                            System.out.println("   Descrição: " + descricoes.get(i));
                            System.out.printf("   Preço: R$ %.2f\n", precos.get(i));
                            achou = true;
                            break;
                        }
                    }

                    if (!achou) {
                        System.out.println("⚠️ Produto não encontrado.");
                    }
                } catch (Exception e) {
                    System.out.println("❌ ID inválido!");
                }

            } else if (opcao.equals("4")) {
                // --- ATUALIZAR ---
                System.out.println("\n--- Atualizar Produto ---");
                System.out.print("Digite o ID do produto: ");
                try {
                    long idUp = Long.parseLong(scanner.nextLine());
                    int posicao = -1;

                    for (int i = 0; i < ids.size(); i++) {
                        if (ids.get(i) == idUp) {
                            posicao = i;
                            break;
                        }
                    }

                    if (posicao != -1) {
                        System.out.print("Novo Nome: ");
                        String novoNome = scanner.nextLine();

                        System.out.print("Nova Descrição: ");
                        String novaDesc = scanner.nextLine();

                        System.out.print("Novo Preço: ");
                        double novoPreco = Double.parseDouble(scanner.nextLine().replace(",", "."));

                        if (novoNome.trim().length() < 3 || novoPreco < 0) {
                            System.out.println("❌ Dados inválidos! Operação cancelada.");
                        } else {
                            if (novoPreco >= 5000 && !novaDesc.contains("[ALTO VALOR]")) {
                                novaDesc = "[ALTO VALOR] " + novaDesc;
                            }

                            // Modificando os vetores paralelos pelos índices
                            nomes.set(posicao, novoNome.trim());
                            descricoes.set(posicao, novaDesc.trim());
                            precos.set(posicao, novoPreco);

                            System.out.println("✅ Produto atualizado!");
                        }
                    } else {
                        System.out.println("⚠️ ID não encontrado.");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Erro ao atualizar: " + e.getMessage());
                }

            } else if (opcao.equals("5")) {
                // --- DELETAR ---
                System.out.println("\n--- Remover Produto ---");
                System.out.print("Digite o ID para remover: ");
                try {
                    long idDel = Long.parseLong(scanner.nextLine());
                    int posDel = -1;

                    for (int i = 0; i < ids.size(); i++) {
                        if (ids.get(i) == idDel) {
                            posDel = i;
                            break;
                        }
                    }

                    if (posDel != -1) {
                        // Desincronizar um desses remoções quebra o banco de dados inteiro!
                        ids.remove(posDel);
                        nomes.remove(posDel);
                        descricoes.remove(posDel);
                        precos.remove(posDel);

                        System.out.println("✅ Produto removido!");
                    } else {
                        System.out.println("⚠️ ID não encontrado.");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Digite um número válido!");
                }

            } else if (opcao.equals("0")) {
                System.out.println("Saindo do sistema...");
                break;
            } else {
                System.out.println("⚠️ Opção inválida!");
            }
        }
    }
}
