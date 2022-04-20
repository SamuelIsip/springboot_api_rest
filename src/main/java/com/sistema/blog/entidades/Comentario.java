package com.sistema.blog.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "cuerpo", nullable = false)
    private String cuerpo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicaciones_id", nullable = false)
    private Publicacion publicacion;
}
