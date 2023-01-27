package ve.com.tps.sistemaexamenes.autentication;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

//ESTA CLASE SE ENCARGA DE CREAR EL TOKEN JWT.
@Component
@Slf4j
public class JwtTokenProvider {

    //CREAMOS DOS VARIABLES QUE CON LA ANOTACION VALUE SE ASOCIAN CON LAS PROPIEDADES DE LA APP.
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationinMs;

    //GENERAMOS EL TOKEN CON APOYO DEL OBJETO AUTHENTICATION.
    public String generarToken(Authentication authentication){

        String user = authentication.getName();

        Date fechaActual = new Date();

        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationinMs);
        log.info("JWT metodo crear iniciando");
        //INICIAMOS EL TOKEN CON EL USERNAME, LA FECHA DE CREACION DEL TOKEN, FECHA DE EXPIRACION,FIRMA DE TOKEN Y CLAVE SECRETA.
        String token = Jwts
                .builder()
                .setSubject(user)
                .setIssuedAt(fechaActual)
                .setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
        log.info("JWT metodo crear exitoso");
        return token;
    }

    //RETORNAMOS EL SUBJECT (EL USERNAME DEL USUARIO)
    public String obtenerUsername(String token){

        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return claims.getSubject();

    }

    //VALIDAR EL TOKEN Y VERIFICAR QUE NO TENGA NINGUN ERROR, Y SI TIENE LANZAR UNA EXCEPCION
    public boolean validarToken(String token){

        try {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            return true;

        }catch(SignatureException ex){

            throw new SignatureException("Firma JWT no valida.");

        } catch(MalformedJwtException ex){

            throw new SignatureException("Token JWT no valido.");

        } catch(ExpiredJwtException ex){

            throw new SignatureException("Token JWT caducado.");

        } catch(UnsupportedJwtException ex){

            throw new SignatureException("Token JWT no soportado.");

        } catch(IllegalArgumentException ex){

            throw new SignatureException("La cadena claims esta vacia.");
        }
    }
}
