package com.sistema.blog.controlador;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.servicio.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @PostMapping("/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(value = "publicacionId") long publicacionId, @Valid @RequestBody ComentarioDTO comentarioDTO) {
        return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacion(@PathVariable(value = "publicacionId") long publicacionId) {
        return comentarioServicio.obtenerComentariosPorPublicacionId(publicacionId);
    }

    @GetMapping("/{publicacionId}/comentario/{id}")
    public ComentarioDTO obtenerComentario(@PathVariable(value = "publicacionId") long publicacionId, @PathVariable(value = "id") long comentarioId) {
        return comentarioServicio.obtenerComentarioPorId(publicacionId, comentarioId);
    }

    @PutMapping("/{publicacionId}/comentario/{id}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable(value = "publicacionId") long publicacionId,
                                                              @PathVariable(value = "id") long comentarioId, @Valid @RequestBody ComentarioDTO comentarioDTO) {

        return new ResponseEntity<>(comentarioServicio.actualizarComentario(publicacionId, comentarioId, comentarioDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{publicacionId}/comentario/{id}")
    public ResponseEntity<String> eliminarComentario(@PathVariable(value = "publicacionId") long publicacionId,
                                                     @PathVariable(value = "id") long comentarioId) {

        comentarioServicio.eliminarComentario(publicacionId, comentarioId);

        return new ResponseEntity<>("Comentario eliminado con éxito", HttpStatus.OK);
    }
}
