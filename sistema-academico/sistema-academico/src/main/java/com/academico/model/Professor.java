package com.academico.model;

import jakarta.persistence.*;

@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "perfil_id", unique = true)
    private PerfilProfessor perfil;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public PerfilProfessor getPerfil() { return perfil; }
    public void setPerfil(PerfilProfessor perfil) {
        this.perfil = perfil;
        if (perfil != null) perfil.setProfessor(this);
    }
}
