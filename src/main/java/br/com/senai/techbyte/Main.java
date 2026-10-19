package br.com.senai.techbyte;

import br.com.senai.techbyte.controller.ProdutoController;
import br.com.senai.techbyte.repository.ProdutoMemoryRepository;
import br.com.senai.techbyte.repository.ProdutoRepository;
import br.com.senai.techbyte.service.ProdutoService;
import br.com.senai.techbyte.view.ProdutoConsoleView;

public class Main {
    public static void main(String[] args) {
        // 1. Instancia o repositório concreto de dados em memória
        ProdutoRepository repository = new ProdutoMemoryRepository();

        // 2. Injeta o repositório na camada Service (regras de negócio)
        ProdutoService service = new ProdutoService(repository);

        // 3. Injeta a Service no Controller
        ProdutoController controller = new ProdutoController(service);

        // 4. Injeta o Controller na View e dispara a aplicação
        ProdutoConsoleView view = new ProdutoConsoleView(controller);


        view.iniciar();
    }
}