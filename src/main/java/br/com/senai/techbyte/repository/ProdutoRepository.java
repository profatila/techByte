package br.com.senai.techbyte.repository;

import br.com.senai.techbyte.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {
    Produto salvar(Produto produto);
    List<Produto> listarTodos();
    Optional<Produto> buscarPorId(Long id);
    boolean atualizar(Produto produto);
    boolean deletar(Long id);
}