package com.sistema.blog.dto;

import com.sistema.blog.entidades.Comentario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionDTO {

    private Long id;
    @NotEmpty
    @Size(min = 2, message = "El titulo de la publicacion debería tener al menos 2 caracteres")
    private String titulo;
    @NotEmpty
    @Size(min = 10, message = "El descripcion de la publicacion debería tener al menos 10 caracteres")
    private String descripcion;
    @NotEmpty
    private String contenido;
    private Set<Comentario> comentarios;

}
