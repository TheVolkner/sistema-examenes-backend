package ve.com.tps.sistemaexamenes.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String recurso){

        super(recurso);
    }
}
