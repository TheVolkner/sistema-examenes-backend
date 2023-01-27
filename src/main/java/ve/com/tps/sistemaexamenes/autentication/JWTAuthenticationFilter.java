package ve.com.tps.sistemaexamenes.autentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ve.com.tps.sistemaexamenes.services.validation.CustomUserDetailsService;

import java.io.IOException;

//ESTA CLASE SE ENCARGARÁ DE VALIDAR EL USUARIO LOGEADO Y COMPROBAR SU TOKEN JWT EN CADA PETICIÓN
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {


    //INYECTAMOS EL ACCESO AL LOS DATOS DEL USUARIO LOGEADO SEGUN SU USERNAME
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //INYECTAMOS LA CLASE PARA VALIDAR EL TOKEN
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //SOBREESCRIBIENDO ESTE MÉTODO DE ONCE PER REQUEST FILTER
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //OBTENEMOS EL TOKEN QUE YA DEBERIA ESTAR CREADO Y ASOCIADO AL HEADER DE LAS PETICIONES HTTP
        String bearerToken = request.getHeader("Authorization");

        //COMPROBAMOS QUE EL TOKEN DEL HEADER EXISTA Y QUE INICIE CON LA PALABRA BEARER
        if(bearerToken != null && bearerToken.startsWith("Bearer")){

            //DIVIDIMOS LA PALABRA DEL TOKEN Y LA GUARDAMOS EL TOKEN SOLO EN OTRA VARIABLE
            String token = bearerToken.substring(7);

            //ENVIAMOS A LA CLASE DE JWT EL TOKEN PARA OBTENER EL USERNAME ASOCIADO
            String username = jwtTokenProvider.obtenerUsername(token);

            //CON AYUDA DEL USERNAME ENVIAMOS A BUSCAR EL USUARIO Y LO GUARDAMOS COMO UN OBJETO USER DETAILS
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            //CREAMOS EL OBJETO DE SESIÓN PARA SECURITY
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());

            //LE ASIGNAMOS LOS DATOS DE LA PETICION
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //ASIGNAMOS LA SEGURIDAD A LA APLICACION
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //LE INDICAMOS AL FILTER CHAIN DE SECURITY QUE EJECUTE ESTE FILTRO.
        filterChain.doFilter(request,response);


    }
}
