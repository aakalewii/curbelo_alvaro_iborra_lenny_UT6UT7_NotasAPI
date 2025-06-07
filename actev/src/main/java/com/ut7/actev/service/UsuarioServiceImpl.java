package com.ut7.actev.service;

import com.ut7.actev.model.Usuario;
import com.ut7.actev.repository.UsuarioRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends AbstractCrudService<Usuario, Long> implements UsuarioService {

        private final UsuarioRepository usuarioRepository;


    public UsuarioServiceImpl(UsuarioRepository usuarioRepo) {
        super(usuarioRepo);
        this.usuarioRepository = usuarioRepo;
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getPasswordHash() != null && usuario.getPasswordHash().length() < 64) {
            usuario.hashPassword();
        }
        return super.save(usuario);
    }

    @Override
    @Transactional
    public Usuario update(Long id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalStateException("Usuario no encontrado");
        }
        Usuario existing = usuarioRepository.findById(id).orElseThrow();
        org.springframework.beans.BeanUtils.copyProperties(usuario, existing, "id", "notas");
        if (usuario.getPasswordHash() != null && usuario.getPasswordHash().length() < 64) {
            existing.setPasswordHash(usuario.getPasswordHash());
            existing.hashPassword();
        }
        return usuarioRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalStateException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

}