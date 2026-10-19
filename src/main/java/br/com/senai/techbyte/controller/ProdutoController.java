package br.com.senai.techbyte.controller;

import br.com.senai.techbyte.model.Produto;
import br.com.senai.techbyte.service.ProdutoService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Controller simplificado: Apenas repassa a solicitação para a camada Service
 * e gerencia os retornos/exceções.
 */
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    public Produto cadastrarProduto(String nome, String descricao, BigDecimal preco) {
        return service.cadastrarProduto(nome, descricao, preco);
    }

    public List<Produto> listarProdutos() {
        return service.listarProdutos();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return service.buscarPorId(id);
    }

    public boolean atualizarProduto(Long id, String nome, String descricao, BigDecimal preco) {
        return service.atualizarProduto(id, nome, descricao, preco);
    }

    public boolean removerProduto(Long id) {
        return service.removerProduto(id);
    }
}