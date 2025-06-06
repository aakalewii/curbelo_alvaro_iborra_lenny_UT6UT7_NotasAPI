package com.ut7.actev.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


import com.ut7.actev.model.Nota;
import com.ut7.actev.model.Usuario;
import com.ut7.actev.repository.NotaRepository;
import com.ut7.actev.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/notas")
public class NotaController {

    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;

    public NotaController(NotaRepository notaRepository, UsuarioRepository usuarioRepository) {
        this.notaRepository = notaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // GET /notas
    @GetMapping
    public ResponseEntity<List<Nota>> getNotas(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order) {

        Sort sort = Sort.unsorted();
        if (sortBy != null && order != null) {
            Sort.Direction dir = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            sort = Sort.by(dir, sortBy);
        }

        List<Nota> notas;
        if (usuarioId != null) {
            notas = notaRepository.findByUsuarioId(usuarioId, sort);
        } else {
            notas = sort.isUnsorted() ? notaRepository.findAll() : notaRepository.findAll(sort);
        }
        return ResponseEntity.ok(notas);
    }

    // GET /notas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Nota> getNotaById(@PathVariable Long id) {
        return notaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /notas?usuarioId={id}
    @PostMapping
    public ResponseEntity<Nota> createNota(@Valid @RequestBody Nota nota, @RequestParam Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        nota.setUsuario(usuario);
        Nota created = notaRepository.save(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /notas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Nota> updateNota(@PathVariable Long id,@Valid @RequestBody Nota nota) {
    return notaRepository.findById(id)
            .map(existente -> {
                existente.setTitulo(nota.getTitulo());
                existente.setContenido(nota.getContenido());
                existente.setFechaCreacion(nota.getFechaCreacion());
                Nota actualizada = notaRepository.save(existente);
                return ResponseEntity.ok(actualizada);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /notas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNota(@PathVariable Long id) {
        return notaRepository.findById(id)
                .map(nota -> {
                    notaRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}