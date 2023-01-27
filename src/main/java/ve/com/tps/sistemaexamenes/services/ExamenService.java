package ve.com.tps.sistemaexamenes.services;

import org.springframework.stereotype.Service;
import ve.com.tps.sistemaexamenes.entity.Categoria;
import ve.com.tps.sistemaexamenes.entity.Examen;
import ve.com.tps.sistemaexamenes.entity.Preguntas;

import java.util.List;
import java.util.Set;


public interface ExamenService {

    Examen agregarExamen(Examen e);

    Examen modificarExamen(Examen e);

    List<Examen> listarExamenes();

    void eliminarExamen(Integer id);

    Examen buscarExamenPorId(Integer id);

    Set<Preguntas> buscarPreguntasDeExamen(Examen e);

    Set<Examen> buscarExamenPorCategoria(Categoria c);
}
