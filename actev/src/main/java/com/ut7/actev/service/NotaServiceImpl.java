package com.ut7.actev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut7.actev.model.Nota;
import com.ut7.actev.repository.NotaRepository;

@Service
@Transactional
public class NotaServiceImpl extends AbstractCrudService<Nota, Long> implements NotaService {

    private final NotaRepository notaRepository;

    public NotaServiceImpl(NotaRepository notaRepository) {
        super(notaRepository);
        this.notaRepository = notaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Nota> getById(Long id) {
        Optional<Nota> nota = notaRepository.findById(id);
        if (nota.isEmpty()) {
            throw new IllegalStateException("Nota no encontrada");
        }
        return nota;
    }

    @Override
    @Transactional
    public Nota update(Long id, Nota nota) {
        if (!notaRepository.existsById(id)) {
            throw new IllegalStateException("Nota no encontrada");
        }
        return super.update(id, nota);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!notaRepository.existsById(id)) {
            throw new IllegalStateException("Nota no encontrada");
        }
        notaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Nota> findByUsuarioId(Long usuarioId, Sort sort) {
        if (usuarioId == null) {
            throw new IllegalStateException("El usuarioId es obligatorio");
        }
        return notaRepository.findByUsuarioId(usuarioId, sort);
    }
}