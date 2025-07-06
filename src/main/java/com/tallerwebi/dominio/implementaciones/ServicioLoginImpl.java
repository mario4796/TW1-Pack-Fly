package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.presentacion.dtos.HotelDto;
import com.tallerwebi.presentacion.dtos.ResumenPagoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);
    }

    @Override
    public void modificarUsuario(Usuario usuario) {
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public ResumenPagoDto obtenerDeudaDelUsuario(Long idUsuario, List<HotelDto> hoteles, List<Vuelo> vuelos, List<Excursion> excursiones) {
        double subtotal = 0.0;
        double impuestos = 0.0;
        double descuentos = 0.0;
        double cargosServicio = 0.0;

        // Suma precios de vuelos
        if (vuelos != null) {
            for (Vuelo vuelo : vuelos) {
                if (vuelo.getPrecio() != null) {
                    subtotal += vuelo.getPrecio();
                }
            }
        }

        // Suma precios de hoteles
        if (hoteles != null) {
            for (HotelDto hotel : hoteles) {
                if (hotel.getPrecio() != null) {
                    subtotal += hotel.getPrecio();
                }
            }
        }

        // Suma precios de excursiones
        if (excursiones != null) {
            for (Excursion excursion : excursiones) {
                if (excursion.getPrecio() != null) {
                    subtotal += excursion.getPrecio();
                }
            }
        }

        // 21% de impuestos
        impuestos = subtotal * 0.21;

        // 10% de descuento si hay más de 3 reservas
        int cantidadReservas = (vuelos != null ? vuelos.size() : 0)
                + (hoteles != null ? hoteles.size() : 0)
                + (excursiones != null ? excursiones.size() : 0);
        if (cantidadReservas >= 3) {
            descuentos = subtotal * 0.10;
        }

        // 5% de cargos de servicio sobre el subtotal
        cargosServicio = subtotal * 0.05;

        double total = subtotal + impuestos + cargosServicio - descuentos;

        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuario.setApagar(total);
        repositorioUsuario.guardar(usuario);
        return new ResumenPagoDto(subtotal, impuestos, cargosServicio, descuentos, total);
    }

    @Override
    public void modificarPassword(Long idUsuario, String password) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuario.setPassword(password); // Ideal: encriptarla
        repositorioUsuario.guardar(usuario);
    }

    @Override
    public void modificarEmail(Long idUsuario, String email) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuario.setEmail(email);
        repositorioUsuario.guardar(usuario);
    }

    @Override
    public void modificarNombreYApellido(Long idUsuario, String nombre, String apellido) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        repositorioUsuario.guardar(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long idUsuario) {
        return repositorioUsuario.buscarUsuarioPorId(idUsuario);
    }


}

