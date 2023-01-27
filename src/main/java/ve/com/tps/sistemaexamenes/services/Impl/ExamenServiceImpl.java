package ve.com.tps.sistemaexamenes.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ve.com.tps.sistemaexamenes.entity.Categoria;
import ve.com.tps.sistemaexamenes.entity.Examen;
import ve.com.tps.sistemaexamenes.entity.Preguntas;
import ve.com.tps.sistemaexamenes.repository.ExamenesRepository;
import ve.com.tps.sistemaexamenes.repository.PreguntasRepository;
import ve.com.tps.sistemaexamenes.services.ExamenService;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class ExamenServiceImpl implements ExamenService {

    @Autowired
    private ExamenesRepository examenesRepository;

    @Autowired
    private PreguntasRepository preguntasRepository;

    @Override
    public Examen agregarExamen(Examen e) {

        return examenesRepository.save(e);
    }

    @Override
    public Examen modificarExamen(Examen e) {

        return examenesRepository.save(e);
    }

    @Override
    public List<Examen> listarExamenes() {

        return examenesRepository.findAll();
    }

    @Override
    public void eliminarExamen(Integer id) {

        examenesRepository.deleteById(id);
    }

    @Override
    public Examen buscarExamenPorId(Integer id) {

        return examenesRepository.findById(id).get();
    }

    @Override
    public Set<Preguntas> buscarPreguntasDeExamen(Examen e) {


        Set<Preguntas> preguntas = preguntasRepository.findAllByExamen(e).get();

        return preguntas;
    }

    @Override
    public Set<Examen> buscarExamenPorCategoria(Categoria c) {

        Set<Examen> examenes = examenesRepository.findAllByCategoria(c);

        return examenes;
    }
}
