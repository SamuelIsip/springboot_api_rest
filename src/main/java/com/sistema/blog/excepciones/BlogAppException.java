package com.sistema.blog.excepciones;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class BlogAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus estado;

    private String mensaje;

    public BlogAppException(HttpStatus estado, String mensaje) {
        super(String.format("%s", mensaje));
        this.estado = estado;
        this.mensaje = mensaje;
    }
}
