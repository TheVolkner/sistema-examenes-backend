package ve.com.tps.sistemaexamenes.services;

import org.springframework.stereotype.Service;
import ve.com.tps.sistemaexamenes.entity.Examen;
import ve.com.tps.sistemaexamenes.entity.Preguntas;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface PreguntasService {

    List<Preguntas> listarPreguntas();

    Preguntas agregarPregunta(Preguntas p);

    Preguntas modificarPregunta(Preguntas p);

    void eliminarPregunta(Integer id);

    Preguntas buscarPreguntaPorId(Integer id);

    Set<Preguntas> buscarPreguntasPorExamen(Examen e) throws Exception;
}
