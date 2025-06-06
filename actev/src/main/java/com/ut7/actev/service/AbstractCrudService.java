package com.ut7.actev.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;

@Transactional

public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID> {
    protected final JpaRepository<T, ID> repo;

    protected AbstractCrudService(JpaRepository<T, ID> repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> getAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<T> getById(ID id) {
        return repo.findById(id);
    }

    @Override
    public T save(T ent) {
        return repo.save(ent);
    }

    @Transactional
    @Override
    public T update(ID id, T ent) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("La entidad con ID " + id + " no existe.");
        }

        T existing = repo.findById(id).orElseThrow();
      
        BeanUtils.copyProperties(ent, existing, "id");
        return repo.save(existing);
    }

    @Override
    public void deleteById(ID id) {
        repo.deleteById(id);
    }
}
