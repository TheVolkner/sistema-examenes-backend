package ve.com.tps.sistemaexamenes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
//ESTA CLASE SERVIRÁ COMO OBJETO PARA ALMACENAR UN MENSAJE JSON QUE SE ENVIARÁ COMO RESPUESTA EN EXCEPCIONES
//UTILIZADO EN EL GLOBAL EXCEPTION MANAGER.
public class ErrorDetalles {

    private LocalDateTime marcaTiempo;
    private String mensaje;
    private String detalles;


}
