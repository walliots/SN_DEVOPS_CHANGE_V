package com.example.demo.controller;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    // POST /api/produtos
    @PostMapping
    public ResponseEntity<ProdutoDTO.Response> criar(@Valid @RequestBody ProdutoDTO.Request request) {
        ProdutoDTO.Response response = produtoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/produtos
    @GetMapping
    public ResponseEntity<List<ProdutoDTO.Response>> listarTodos(
            @RequestParam(required = false) String nome) {

        if (nome != null && !nome.isBlank()) {
            return ResponseEntity.ok(produtoService.buscarPorNome(nome));
        }
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    // GET /api/produtos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    // PUT /api/produtos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO.Response> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoDTO.Request request) {
        return ResponseEntity.ok(produtoService.atualizar(id, request));
    }

    // DELETE /api/produtos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
