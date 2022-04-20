package com.sistema.blog.servicio;

import com.sistema.blog.dto.ComentarioDTO;

import java.util.List;


public interface ComentarioServicio {

    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO);

    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId);

    public ComentarioDTO obtenerComentarioPorId(long publicacionId, long comentarioId);

    public ComentarioDTO actualizarComentario(long publicacionId, long comentarioId, ComentarioDTO solicitudDeComentario);

    public void eliminarComentario(long publicacionId, long comentarioId);

}
