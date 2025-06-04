package com.ut7.actev.service;

import com.ut7.actev.model.Usuario;
import com.ut7.actev.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends AbstractCrudService<Usuario, Long> implements UsuarioService {


    public UsuarioServiceImpl(UsuarioRepository usuarioRepo) {
        super(usuarioRepo);
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
        // Opcional: hashear solo si la contraseña ha cambiado y no está ya hasheada
        if (usuario.getPasswordHash() != null && usuario.getPasswordHash().length() < 64) {
            usuario.hashPassword();
        }
        return super.update(id, usuario);
    }
}