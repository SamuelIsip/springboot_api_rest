package com.sistema.blog.servicio;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.entidades.Comentario;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.BlogAppException;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repositorio.ComentarioRepositorio;
import com.sistema.blog.repositorio.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario = mapearEntidad(comentarioDTO);
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        comentario.setPublicacion(publicacion);

        return mapearDTO(comentarioRepositorio.save(comentario));
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId) {
        List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);
        return comentarios.stream().map(this::mapearDTO).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(long publicacionId, long comentarioId) {
        return mapearDTO(comprobacionesPublicacionComentario(publicacionId, comentarioId));
    }

    @Override
    public ComentarioDTO actualizarComentario(long publicacionId, long comentarioId, ComentarioDTO solicitudDeComentario) {
        Comentario comentario = comprobacionesPublicacionComentario(publicacionId, comentarioId);

        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());

        return mapearDTO(comentarioRepositorio.save(comentario));
    }

    /**
     * Comprobamos si existe la publicación y el comentario
     * y se el comentario pertenece a la publicación
     *
     * @param publicacionId
     * @param comentarioId
     * @return el comentario
     */
    private Comentario comprobacionesPublicacionComentario(long publicacionId, long comentarioId) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentario comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        return comentario;
    }

    @Override
    public void eliminarComentario(long publicacionId, long comentarioId) {
        comentarioRepositorio.delete(comprobacionesPublicacionComentario(publicacionId, comentarioId));
    }

    /**
     * Convertir entidad a DTO
     *
     * @param comentario
     * @return
     */
    private ComentarioDTO mapearDTO(Comentario comentario) {
        return mapper.map(comentario, ComentarioDTO.class);
    }

    /**
     * Convertir DTO a entidad
     *
     * @param comentarioDto
     * @return
     */
    private Comentario mapearEntidad(ComentarioDTO comentarioDto) {
        return mapper.map(comentarioDto, Comentario.class);
    }
}
