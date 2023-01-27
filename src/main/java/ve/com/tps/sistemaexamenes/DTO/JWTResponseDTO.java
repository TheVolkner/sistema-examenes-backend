package ve.com.tps.sistemaexamenes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//OBJETO DTO PARA DEVOLVER EL TOKEN JWT
public class JWTResponseDTO {

    String token;
}
