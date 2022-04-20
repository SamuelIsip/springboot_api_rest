package com.sistema.blog.servicio;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repositorio.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServcioImpl implements PublicacionServicio {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDto) {
        // Convertimos DTO a entidad e insertamos
        Publicacion nuevaPublicacion = publicacionRepositorio.save(mapearEntidad(publicacionDto));

        // Convertimos Entidad a DTO y devolvemos
        return mapearDTO(nuevaPublicacion);
    }

    @Override
    public PublicacionRespuesta obtenerTodasPublicaciones(int numeroPagina, int medidaPagina, String ordenarPor, String sortDir) {
        // Paginamos resultados
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();

        Pageable pageable = PageRequest.of(numeroPagina, medidaPagina, sort);
        Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);

        List<PublicacionDTO> contenido = publicaciones.getContent().stream()
                .map(this::mapearDTO)
                .collect(Collectors.toList());

        return PublicacionRespuesta.builder()
                .contenido(contenido)
                .numeroPagina(numeroPagina)
                .medidaPagina(medidaPagina)
                .totalElementos(publicaciones.getTotalElements())
                .totalPaginas(publicaciones.getTotalPages())
                .ultima(publicaciones.isLast())
                .build();

    }

    /**
     * Convertir entidad a DTO
     *
     * @param publicacion
     * @return
     */
    private PublicacionDTO mapearDTO(Publicacion publicacion) {
        return mapper.map(publicacion, PublicacionDTO.class);
    }

    /**
     * Convertir DTO a entidad
     *
     * @param publicacionDto
     * @return
     */
    private Publicacion mapearEntidad(PublicacionDTO publicacionDto) {
        return mapper.map(publicacionDto, Publicacion.class);
    }

    @Override
    public PublicacionDTO obtenerPublicacionPorID(long id) {
        return mapearDTO(publicacionRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id)));
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDto, long id) {
        PublicacionDTO publicacionActualizar = obtenerPublicacionPorID(id);
        publicacionActualizar.setTitulo(publicacionDto.getTitulo());
        publicacionActualizar.setDescripcion(publicacionDto.getDescripcion());
        publicacionActualizar.setContenido(publicacionDto.getContenido());
        return mapearDTO(publicacionRepositorio.save(mapearEntidad(publicacionActualizar)));
    }

    @Override
    public void eliminarPublicacion(long id) {
        PublicacionDTO publicacionEliminar = obtenerPublicacionPorID(id);
        publicacionRepositorio.delete(mapearEntidad(publicacionEliminar));
    }

}
