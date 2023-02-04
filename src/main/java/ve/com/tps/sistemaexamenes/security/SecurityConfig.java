package ve.com.tps.sistemaexamenes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ve.com.tps.sistemaexamenes.autentication.JWTAuthenticationEntryPoint;
import ve.com.tps.sistemaexamenes.autentication.JWTAuthenticationFilter;
import ve.com.tps.sistemaexamenes.services.validation.CustomUserDetailsService;

//ESTA CLASE SE ENCARGARÁ DE CONFIGURAR LAS RUTAS Y APLICAR LOS PERMISOS PARA CADA PETICION HTTP
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //INYECTAMOS PARA ACCEDER A LOS DATOS DEL USUARIO LOGEADO
    @Autowired
    private CustomUserDetailsService userDetailsService;

    //INYECTAMOS LA CLASE QUE MANEJARÁ LOS ERRORES DE JWT VINCULADOS AL ENTRYPOINT
    @Autowired
    private JWTAuthenticationEntryPoint authenticationEntryPoint;

    //INYECTAMOS LA CLASE QUE ENCARGARÁ DE FILTRAR LAS BUSQUEDAS Y COMPROBAR QUE EL TOKEN ESTÁ ASOCIADO AL USUARIO
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    //RETORNAMOS UNA INSTANCIA DE BCRYPTPASSWORDENCODER PARA ENCRIPTAR LA CONTRASEÑA DEL USUARIO
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //ESTE BEAN SE ENCARGARÁ DE ACCEDER A LA AUTENTICACIÓN DEL USUARIO, PROCESAR LA CONTRASEÑA, ENCRIPTARLA Y COMPROBARLA
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    //PROTEGEMOS LOS ENDPOINTS DE LA APLICACIÓN E INDICAMOS QUE HACER CON LAS PETICIONES HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //ACÁ INDICAMOS QUE DESACTIVAMOS EL CSRF,AUTORIZAMOS LAS PETICIONES HTTP,INDICAMOS QUE LAS PETICIONES AL PATH /auth/ Y SUB PATHS,
        //SERÁN PERMITIDAS, TODAS LAS DEMÁS PETICIONES DEBÉRAN NECESITAR AUTENTICACIÓN, INDICAMOS EL HTTP BASIC, Y UNA SESIÓN STATELESS

        //ES MUY IMPORTANTE DESACTIVAR EL CORS, PARA PODER HACER LAS PETICIONES HTTP DESDE EL FRONT-END,
        //ADEMAS, PERMITIR LAS PETICIONES HTTP DE TIPO OPTION, QUE SON LAS QUE REALIZA EL CLIENTE

        //LUEGO, EN EL CONTEXTO DE JWT INDICAMOS EL MANEJO DE EXCEPCIONES, ASIGNANDO UN AUTHENTICATION ENTRY POINT
        //Y UN FILTRO DE BUSQUEDA ANTES DE CADA PETICIÓN EL CÚAL SE ENCARGARÁ DE COMPROBAR QUE EL TOKEN JWT SEA DEL USUARIO LOGEADO
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**","/api/usuarios/")
                .permitAll()
                .requestMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }





}