package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Hotel;
import java.util.List;

public interface RepositorioHotel {
    Boolean reservar(Hotel hotel);
    List<Hotel> buscarReserva(Long idUsuario);
    void eliminarReserva(Long idUsuario, String nameHotel);
    Hotel buscarPorUsuarioYNombre(Long usuarioId, Long idHotel);
    void actualizar(Hotel hotel);
    List<Hotel> obtenerTodos();
    Hotel buscarPorId(Long id);
    void pagarHoteles(List<Hotel> hoteles);
    List<Hotel> buscarHotelesPagados(Long id);
}