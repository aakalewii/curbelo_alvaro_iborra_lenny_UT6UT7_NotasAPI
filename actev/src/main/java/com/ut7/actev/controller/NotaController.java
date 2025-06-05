package com.ut7.actev.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Nota> getNotas(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order) {

        Sort sort = Sort.unsorted();
        if (sortBy != null && order != null) {
            Sort.Direction dir = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            sort = Sort.by(dir, sortBy);
        }

        if (usuarioId != null) {
            return notaRepository.findByUsuarioId(usuarioId, sort);
        } else {
            return sort.isUnsorted() ? notaRepository.findAll() : notaRepository.findAll(sort);
        }
    }

    // GET /notas/{id}
    @GetMapping("/{id}")
    public Optional<Nota> getNotaById(@PathVariable Long id) {
        return notaRepository.findById(id);
    }

    // POST /notas?usuarioId={id}
    @PostMapping
    public Nota createNota(@RequestBody Nota nota, @RequestParam Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        nota.setUsuario(usuario);
        return notaRepository.save(nota);
    }

    // PUT /notas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Nota> updateNota(@PathVariable Long id, @RequestBody Nota nota) {
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
    public void deleteNota(@PathVariable Long id) {
        notaRepository.deleteById(id);
    }
}