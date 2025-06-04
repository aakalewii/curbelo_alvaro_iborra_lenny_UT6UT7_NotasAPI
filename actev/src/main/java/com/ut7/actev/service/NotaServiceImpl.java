package com.ut7.actev.service;

import org.springframework.stereotype.Service;

import com.ut7.actev.model.Nota;
import com.ut7.actev.repository.NotaRepository;

@Service
public class NotaServiceImpl extends AbstractCrudService<Nota, Long> implements NotaService {

    public NotaServiceImpl(NotaRepository notaRepository) {
        super(notaRepository);
    }
}