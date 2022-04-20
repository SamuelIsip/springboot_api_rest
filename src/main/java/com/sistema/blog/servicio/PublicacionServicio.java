package com.sistema.blog.servicio;


import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;

public interface PublicacionServicio {

    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDto);

    public PublicacionRespuesta obtenerTodasPublicaciones(int numeroPagina, int medidaPagina, String ordenarPor, String sortDir);

    public PublicacionDTO obtenerPublicacionPorID(long id);

    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDto, long id);

    public void eliminarPublicacion(long id);

}
