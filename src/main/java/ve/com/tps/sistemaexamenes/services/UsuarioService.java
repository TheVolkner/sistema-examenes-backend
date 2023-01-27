package ve.com.tps.sistemaexamenes.services;

import ve.com.tps.sistemaexamenes.entity.UsuarioRol;
import ve.com.tps.sistemaexamenes.entity.Usuarios;

import java.util.Set;

public interface UsuarioService {

    public Usuarios guardarUsuario(Usuarios usuario, Set<UsuarioRol> usuarioRoles) throws Exception;

    public Usuarios obtenerUsuario(String username);

    public void eliminarUsuario(Integer id);



}
