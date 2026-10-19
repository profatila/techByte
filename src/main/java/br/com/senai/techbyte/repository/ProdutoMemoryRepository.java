package br.com.senai.techbyte.repository;

import br.com.senai.techbyte.model.Produto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProdutoMemoryRepository implements ProdutoRepository {
    private final List<Produto> produtos = new ArrayList<>();
    private long proximoId = 1;

    @Override
    public Produto salvar(Produto produto) {
        Produto novo = new Produto(
                proximoId++,
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco()
        );
        produtos.add(novo);
        return novo;
    }

    @Override
    public List<Produto> listarTodos() {
        return Collections.unmodifiableList(produtos);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean atualizar(Produto produtoAtualizado) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId().equals(produtoAtualizado.getId())) {
                produtos.set(i, produtoAtualizado);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletar(Long id) {
        return produtos.removeIf(p -> p.getId().equals(id));
    }
}