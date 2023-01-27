package ve.com.tps.sistemaexamenes.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ve.com.tps.sistemaexamenes.entity.Examen;
import ve.com.tps.sistemaexamenes.entity.Preguntas;
import ve.com.tps.sistemaexamenes.repository.PreguntasRepository;
import ve.com.tps.sistemaexamenes.services.PreguntasService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PreguntasServiceImpl implements PreguntasService {

    @Autowired
    private PreguntasRepository preguntasRepository;

    @Override
    public List<Preguntas> listarPreguntas() {

        return preguntasRepository.findAll();
    }

    @Override
    public Preguntas agregarPregunta(Preguntas p) {

        return preguntasRepository.save(p);
    }

    @Override
    public Preguntas modificarPregunta(Preguntas p) {

        return preguntasRepository.save(p);
    }

    @Override
    public void eliminarPregunta(Integer id) {

        preguntasRepository.deleteById(id);
    }

    @Override
    public Preguntas buscarPreguntaPorId(Integer id) {

        return preguntasRepository.findById(id).get();
    }

    @Override
    //LISTAMOS TODAS LAS PREGUNTAS SEGÚN EL ID DEL EXAMEN AL CÚAL ESTÁN ASIGNADAS
    public Set<Preguntas> buscarPreguntasPorExamen(Examen e) throws Exception {

       Optional<Set<Preguntas>> preguntas = preguntasRepository.findAllByExamen(e);

        if(preguntas.isEmpty() || preguntas.get().isEmpty()){

            throw new Exception("No se han encontrado preguntas según el ID Exámen enviado al servidor.");
        } else {

            return preguntas.get();
        }
    }


}
