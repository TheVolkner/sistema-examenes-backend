package ve.com.tps.sistemaexamenes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.sistemaexamenes.DTO.JWTResponseDTO;
import ve.com.tps.sistemaexamenes.autentication.JwtTokenProvider;
import ve.com.tps.sistemaexamenes.DTO.Login;
import ve.com.tps.sistemaexamenes.entity.Usuarios;
import ve.com.tps.sistemaexamenes.exceptions.ResourceNotFoundException;
import ve.com.tps.sistemaexamenes.services.UsuarioService;
import ve.com.tps.sistemaexamenes.services.validation.CustomUserDetailsService;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@Slf4j
//ACEPTAMOS PETICIONES DEL FRONT-END
@CrossOrigin("*")
public class AuthController {

    //INYECTAMOS EL USUARIO SERVICE PARA ACCEDER AL SERVICIO DE USUARIO
    @Autowired
    private UsuarioService usuarioService;

    //INYECTAMOS EL AUTHENTICATION MANAGER PARA MANIPULAR LOS DATOS DE SESIÓN DEL USUARIO
    @Autowired
    private AuthenticationManager authenticationManager;

    //ACCEDEMOS A LOS DATOS DEL USUARIO LOGEADO
    @Autowired
    private CustomUserDetailsService userDetailsService;

    //JWT SE ENCARGARÁ DE CREARLE UN TOKEN DE SESIÓN AL USUARIO
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    //METODO PARA LOGEARSE
    public ResponseEntity<JWTResponseDTO> login(@RequestBody Login login){

        log.info("Usuario: " + login.getUsername() + " Password: " + login.getPassword());
        log.info("PROCEDIENDO A AUTENTICAR");
        //SOLICITAMOS AL AUTHENTICATION MANAGER QUE COMPRUEBE EL USERNAME Y EL PASSWORD CREANDO UN OBJETO AUTHENTICATION TOKEN DE SPRING SECURITY
        //Y ENVIANDOLO A VALIDAR
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword()));

        log.info("AUTENTICACIÓN VÁLIDA: " + authentication.isAuthenticated());

        //COMPROBAMOS EL RESULTADO DE LA AUTENTICACIÓN
        if(authentication.isAuthenticated()){

            //SI SE LOGEO CORRECTAMENTE, LE INDICAMOS LA NUEVA SEGURIDAD AL CONTEXTO DE LA APLICACIÓN
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //LE CREAMOS UN TOKEN AL USUARIO CON AYUDA DE JWT
            String token = jwtTokenProvider.generarToken(authentication);

            //RETORNAMOS LA AUTENTICACIÓN VALIDA Y EL TOKEN CREADO
           return new ResponseEntity<>(new JWTResponseDTO(token), HttpStatus.OK);
        }

        //SI HUBO UN ERROR LO DEVOLVEMOS
        return new ResponseEntity<>(new JWTResponseDTO("SESIÓN NO AUTENTICADA, NO SE ASIGNÓ UN TOKEN."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //OBTENEMOS EL USUARIO ACTUAL LOGEADO
    @GetMapping("/user-actual")
    public ResponseEntity<UserDetails> obtenerUsuarioActual(Principal principal) throws UsernameNotFoundException{

        //ENVIAMOS A LLAMAR LOS DATOS DEL USUARIO CON EL USER DETAILS SERVICE Y SU METODO
        //ENVIANDOLE EL USERNAME PARÁMETRO DE PRINCIPAL, PRINCIPAL ES UNA REPRESENTACIÓN DEL USUARIO LOGEADO ACTUALMENTE
       UserDetails userDetails = this.userDetailsService.loadUserByUsername(principal.getName());
       //VERIFICAMOS LA RESPUESTA
       if(userDetails != null){

           //SI DEVOLVIÓ UN USUARIO,DEVOLVEMOS EL RESULTADO
           return new ResponseEntity<>(userDetails,HttpStatus.OK);

       } else {
           //DE LO CONTRARIO, INDICAMOS UN ERROR
           throw new UsernameNotFoundException("El usuario " + principal.getName() + " no existe en la base de datos.");
       }


    }

    @GetMapping("/{username}")
    //BUSCAMOS UN USUARIO SEGUN SU USER ENVIADO EN EL PATH
    public ResponseEntity<Usuarios> obtenerUsuario(@PathVariable String username) throws ResourceNotFoundException {

        //EJECUTAMOS LA BUSQUEDA
        Usuarios uFound = usuarioService.obtenerUsuario(username);

        //PROCESAMOS LA RESPUESTA
        if(uFound != null){

            return new ResponseEntity<>(uFound, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("El usuario solicitado no se encuentra disponible");
        }


    }



}
