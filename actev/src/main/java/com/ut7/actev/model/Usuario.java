package com.ut7.actev.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import jakarta.validation.constraints.*;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message= "El nombre es obligatorio.")
    private String nombre;
    
    @Email
    @NotBlank(message= "El E-mail es obligatorio.")
    @Column(unique = true)
    private String email;
    
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    @NotBlank(message= "La contraseña es obligatoria.")
    private String passwordHash;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas;
    
    public Usuario() {
    }

    public Usuario(String nombre, String email, String passwordHash) {
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    

    public void hashPassword() {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(this.passwordHash.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        this.passwordHash = sb.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("Error al hashear la contraseña", e);
    }

    }
    
}
