package com.example.demo.service;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    // ─── CREATE ────────────────────────────────────────────────────────────────

    @Transactional
    public ProdutoDTO.Response criar(ProdutoDTO.Request request) {
        Produto produto = mapToEntity(request);
        Produto salvo = produtoRepository.save(produto);
        return mapToResponse(salvo);
    }

    // ─── READ ALL ──────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ProdutoDTO.Response> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ─── READ BY ID ────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public ProdutoDTO.Response buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
        return mapToResponse(produto);
    }

    // ─── SEARCH BY NAME ────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ProdutoDTO.Response> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ─── UPDATE ────────────────────────────────────────────────────────────────

    @Transactional
    public ProdutoDTO.Response atualizar(Long id, ProdutoDTO.Request request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        produto.setEstoque(request.getEstoque());

        Produto atualizado = produtoRepository.save(produto);
        return mapToResponse(atualizado);
    }

    // ─── DELETE ────────────────────────────────────────────────────────────────

    @Transactional
    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
        produtoRepository.delete(produto);
    }

    // ─── MAPPERS ───────────────────────────────────────────────────────────────

    private Produto mapToEntity(ProdutoDTO.Request request) {
        Produto produto = new Produto();
        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        produto.setEstoque(request.getEstoque());
        return produto;
    }

    private ProdutoDTO.Response mapToResponse(Produto produto) {
        return new ProdutoDTO.Response(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getCriadoEm(),
                produto.getAtualizadoEm()
        );
    }
}
