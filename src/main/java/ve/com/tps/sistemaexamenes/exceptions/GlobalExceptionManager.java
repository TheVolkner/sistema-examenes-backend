package ve.com.tps.sistemaexamenes.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ve.com.tps.sistemaexamenes.DTO.ErrorDetalles;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
//ESTA CLASE NOS SERVIRÁ PARA CONTROLAR LAS EXCEPCIONES QUE SE DISPAREN EN LA APLICACIÓN, Y ENVIAR UNA RESPUESTA JSON AL CLIENTE.
public class GlobalExceptionManager extends ResponseEntityExceptionHandler {

    //CONTRLAMOS RESOURCE NOT FOUND EXCEPTION, GENERADO EN LOS CONTORLADORES AL BUSCAR UN CAMPO
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(ResourceNotFoundException resource, WebRequest webRequest){

        //GENRAMOS UN OBJETO DE USER DETALLES Y LE ENVIAMOS LA FECHA ACTUAL, CUANDO SE DISPARÓ LA EXCEPCIÓN.
        //ENVIAMOS EL MENSAJE DE LA EXCEPCIÓN, Y POR ULTIMO LA DESCRIPCIÓN DE LA PETICIÓN DEL USUARIO.
        ErrorDetalles detalles = new ErrorDetalles(LocalDateTime.now(),resource.getMessage(),webRequest.getDescription(false));

        return new ResponseEntity<>(detalles, HttpStatus.NOT_FOUND);
    }

    //CONTROLAMOS USERNAME NOT FOUND EXCEPTION, POSIBLEMENTE GENERADO EN EL CUSTOM USER DETAILS SERVICE.
    @ExceptionHandler(UsernameNotFoundException.class)
        public ResponseEntity<ErrorDetalles> manejarUsernameNotFoundException(UsernameNotFoundException resource,WebRequest webRequest){

        ErrorDetalles detalles = new ErrorDetalles(LocalDateTime.now(),resource.getMessage(),webRequest.getDescription(false));

        return new ResponseEntity<>(detalles,HttpStatus.NOT_FOUND);

    }

    //ESTA LA COLOCAREMOS DE ULTIMO, Y SERÁ PARA CONTROLAR CUALQUIER EXCEPCIÓN QUE SE DISPARE PERO NO ESPECIFICANDO
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarGlobalException(Exception resource, WebRequest webRequest){

        ErrorDetalles detalles = new ErrorDetalles(LocalDateTime.now(),resource.getMessage(),webRequest.getDescription(false));


        return new ResponseEntity<>(detalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    //CONTROL DE LOS ARGUMENTOS NO VÁLIDOS EN MÉTODOS
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        //HACEMOS UN MAPA PARA OBTENER EL NOMBRE Y LA DESCRIPCIÓN DEL ARGUMENTO NO VALIDO
        Map<String,String> errores = new HashMap<>();

        //DE LA EXCEPCIÓN OBTENEMOS LOS RESULTADOS Y TODOS LOS ERRORES, LUEGO LEEMOS CADA UNO
        ex.getBindingResult().getAllErrors().forEach(error -> {
            //OBTENEMOS EL NOMBRE DEL ERROR Y LA DESCRIPCIÓN DEL MISMO.
            String nombreError = ((FieldError) error).getField();
            String mensajeError = error.getDefaultMessage();

            //GUARDAMOS LOS CAMPOS EN EL MAPA
            errores.put(nombreError,mensajeError);
        });

        //RETORNAMOS AL USUARIO EL RESPONSE CON EL MAPA DE LOS ERRORES GENERADOS POR LOS PARÁMETROS QUE ENVIÓ.
        return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
    }
}
