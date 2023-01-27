package ve.com.tps.sistemaexamenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.sistemaexamenes.entity.Rol;
import ve.com.tps.sistemaexamenes.entity.UsuarioRol;
import ve.com.tps.sistemaexamenes.entity.Usuarios;
import ve.com.tps.sistemaexamenes.exceptions.ResourceNotFoundException;
import ve.com.tps.sistemaexamenes.services.UsuarioService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
//ACEPTAMOS PETICIONES DEL FRONT-END
@CrossOrigin("*")
public class UsuarioController {

    //ACCEDEMOS AL SERVICIO DE USUARIOS PARA COMUNICARNOS CON LA BBDD
    @Autowired
    private UsuarioService usuarioService;

    //ACCEDEMOS AL BEAN DEL BCRYPT PASSWORD ENCODER DE SECURITY CONFIG
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //GUARDAMOS UN USUARIO MEDIANTE EL CONTROLADOR
    @PostMapping("/")
    public ResponseEntity<Usuarios> guardarUsuario(@RequestBody Usuarios u) throws Exception{

        //ASIGNAMOS UNA FOTO DE PERFIL POR DEFECTO Y ACTIVAMOS SU PERFIL
        u.setPerfil("default.png");
        u.setEnabled(1);

        //ENCRIPTAMOS LA CONTRASEÑA DEL USUARIO QUE RECIBIMOS DEL HEADER
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        //CREAMOS MANUALMENTE EL SET DE USUARIOS CON ROLES
        Set<UsuarioRol> roles = new HashSet<>();

        //CREAMOS EL ROL QUE LE QUERAMOS ASIGNAR AL USUARIO
        Rol rol = new Rol();
        rol.setRol_id(2);
        rol.setNombre("USER");

        //CREAMOS UN OBJETO USUARIO CON ROL, LO INICIALIZAMOS Y AGREGAMOS AL SET
        UsuarioRol usuarioRol = new UsuarioRol();

        usuarioRol.setUsuario(u);
        usuarioRol.setRol(rol);

        //AGREGAMOS EL OBJETO USUARIOROL AL SET
        roles.add(usuarioRol);

        //PROCEDEMOS A GUARDAR EL USUARIO Y ENVIAMOS SUS ROLES
        Usuarios uSaved = usuarioService.guardarUsuario(u,roles);

        //PROCESAMOS LA RESPUESTA
        if(uSaved != null){

            return new ResponseEntity<>(uSaved, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}")
    //BUSCAMOS UN USUARIO SEGUN SU USER ENVIADO EN EL PATH
    public ResponseEntity<Usuarios> obtenerUsuario(@PathVariable String username) throws ResourceNotFoundException{

        //EJECUTAMOS LA BUSQUEDA
        Usuarios uFound = usuarioService.obtenerUsuario(username);

        //PROCESAMOS LA RESPUESTA
        if(uFound != null){

            return new ResponseEntity<>(uFound, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("El usuario solicitado no se encuentra disponible");
        }


        }


    @DeleteMapping("/{id}")
    //ELIMINAMOS UN USUARIO SEGUN EL ID ENVIADO AL PATH
    public ResponseEntity<Usuarios> eliminarUsuario(@PathVariable Integer id){

        usuarioService.eliminarUsuario(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}



