package ve.com.tps.sistemaexamenes.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ve.com.tps.sistemaexamenes.entity.UsuarioRol;
import ve.com.tps.sistemaexamenes.entity.Usuarios;
import ve.com.tps.sistemaexamenes.repository.RolRepository;
import ve.com.tps.sistemaexamenes.repository.UsuarioRepository;
import ve.com.tps.sistemaexamenes.services.UsuarioService;

import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    //METODO PARA GUARDAR UN USUARIO

    public Usuarios guardarUsuario(Usuarios usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
       Usuarios usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());

       //VERIFICAMOS SI NO EXISTE YA ESE USUARIO.
       if(usuarioLocal != null){

           System.out.println("El usuario que intenta registrar ya existe.");
           throw new Exception("El usuario ya existe");
       } else {

           //GUARDAMOS CADA ROL ASOCIADO CON EL USUARIO EN LA TABLA DE ROLES.
           for (UsuarioRol urol: usuarioRoles ) {

               rolRepository.save(urol.getRol());
           }

           //LUEGO DE INICIALIZAR LOS ROLES EN LA TABLA, SE LOS ASIGNAMOS AL USUARIO
           //addAll FUNCIONA COMO ACTUALIZADOR,ANEXAS LOS ROLES FALTANTES AL SET DE ROLES DEL USUARIO
           usuario.getUsuarioRoles().addAll(usuarioRoles);

           //GUARDAMOS EL USUARIO
          usuarioLocal =  usuarioRepository.save(usuario);
          return usuarioLocal;
       }

    }

    @Override
    public Usuarios obtenerUsuario(String username) {

        return usuarioRepository.findByUsername(username);
    }

    @Override
    public void eliminarUsuario(Integer id) {

        usuarioRepository.deleteById(id);
    }
}
