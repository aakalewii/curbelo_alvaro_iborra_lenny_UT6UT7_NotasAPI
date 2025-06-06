package com.ut7.actev.service;

import com.ut7.actev.model.Usuario;
import com.ut7.actev.repository.UsuarioRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
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
    public Usuario update(Long id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado");
        }
        if (usuario.getPasswordHash() != null && usuario.getPasswordHash().length() < 64) {
            usuario.hashPassword();
        }
        return super.update(id, usuario);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

}