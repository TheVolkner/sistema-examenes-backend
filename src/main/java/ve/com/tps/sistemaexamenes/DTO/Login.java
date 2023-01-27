package ve.com.tps.sistemaexamenes.DTO;

import lombok.Data;

@Data
//OBJETO DTO QUE RECIBE LOS DATOS JSON DEL BODY PARA EL LOGIN
public class Login {

    private String username;
    private String password;
}
