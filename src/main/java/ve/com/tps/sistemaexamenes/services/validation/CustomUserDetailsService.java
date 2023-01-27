package ve.com.tps.sistemaexamenes.services.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ve.com.tps.sistemaexamenes.entity.UsuarioRol;
import ve.com.tps.sistemaexamenes.entity.Usuarios;
import ve.com.tps.sistemaexamenes.repository.UsuarioRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

//ESTA CLASE SE ENCARGA DE VALIDAR LAS CREDENCIALES DEL USUARIO QUE ESTÁ LOGEANDO Y OBTENER
//SU INFORMACIÓN DESDE LA BASE DE DATOS, CREANDO UN OBJETO DE USUARIO PARA SPRING SECURITY CON SUS ROLES
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {


    //INYECTAMOS LA DEPENDENCIA DEL REPOSITORIO PARA LOS USUARIOS
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //OBTENEMOS EL USUARIO DE LA BASE DE DATOS ASOCIADO A ESE USERNAME
        Usuarios user = usuarioRepository.findByUsername(username);

        log.info("USUARIO OBTENIDO: " + user.getUsername());
        //COMPROBAMOS LA RESPUESTA
        if(user == null){
             log.info("USUARIO INEXISTENTE");
            //SI NO EXISTE, INDICAMOS EL ERROR
            throw new UsernameNotFoundException("El usuario " + username + " no existe en la base de datos.");
        } else {
            log.info("DEVOLVIENDO USUARIO ENCONTRADO...");
            //SI EXISTE, DEVOLVEMOS EL OBJETO DE TIPO USER DE SPRING SECURITY CON EL USERNAME,PASSWORD Y ROLES CONVERTIDOS LLAMANDO AL MÉTODO
            return new User(user.getUsername(),user.getPassword(),mapearRoles(user.getUsuarioRoles()));
        }

    }

    //ESTE MÉTODO SE ENCARGA DE CONVERTIR TODOS LOS ROLES ASOCIADOS CON EL USUARIO A GRANTED AUTHORITY, LA CÚAL ES UNA CLASE
    //QUE ES VÁLIDA PARA INDICARLE UN ROL AL OBJETO USER DE SPRING.
    private Collection<? extends GrantedAuthority> mapearRoles(Set<UsuarioRol> roles){

        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getRol().getNombre())).collect(Collectors.toList());
    }
}
