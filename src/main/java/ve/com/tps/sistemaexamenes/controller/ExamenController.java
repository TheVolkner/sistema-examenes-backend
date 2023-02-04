package ve.com.tps.sistemaexamenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.sistemaexamenes.entity.Categoria;
import ve.com.tps.sistemaexamenes.entity.Examen;
import ve.com.tps.sistemaexamenes.entity.Preguntas;
import ve.com.tps.sistemaexamenes.services.CategoriaService;
import ve.com.tps.sistemaexamenes.services.ExamenService;
import ve.com.tps.sistemaexamenes.services.PreguntasService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/examen")
@CrossOrigin("*")
public class ExamenController {

    @Autowired
    private ExamenService examenService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PreguntasService preguntasService;

    @GetMapping()
    public ResponseEntity<List<Examen>> listarExamenes() throws Exception{

        List<Examen> listaExamenes = examenService.listarExamenes();

        if(listaExamenes != null){

            return new ResponseEntity<>(listaExamenes, HttpStatus.OK);
        } else {
            throw new Exception("Ha ocurrido un error al listar los examenes desde el servidor");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Examen> buscarExamenPorId(@PathVariable Integer id) throws Exception{

        Examen eFound = examenService.buscarExamenPorId(id);

        if(eFound != null){

            return new ResponseEntity<>(eFound,HttpStatus.OK);
        } else {

            throw new Exception("Ha ocurrido un error al buscar un examen por su ID en el servidor");
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<Examen> agregarExamen(@RequestBody Examen e) throws Exception{

        Categoria cCheck = categoriaService.buscarCategoriaPorId(e.getCategoria().getId_categoria());

        if(cCheck != null){

            e.setCategoria(cCheck);
            Examen eSaved = examenService.agregarExamen(e);
            return new ResponseEntity<>(eSaved,HttpStatus.CREATED);
        } else {

            throw new Exception("La categoría a la cúal intenta agregar el exámen no existe.");
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<Examen> editarExamen(@RequestBody Examen e) throws Exception{

        Categoria cCheck = categoriaService.buscarCategoriaPorId(e.getCategoria().getId_categoria());
        Set<Preguntas> preguntas = preguntasService.buscarPreguntasPorExamen(e);

        if(cCheck != null){

            Examen eCheck = examenService.buscarExamenPorId(e.getId_examen());

            if(eCheck != null){

                e.setPreguntas(preguntas);
                e.setCategoria(cCheck);
                Examen eSaved = examenService.modificarExamen(e);
                return new ResponseEntity<>(eSaved,HttpStatus.CREATED);

            } else {

                throw new Exception("El exámen que intenta actualizar no existe.");
            }

        } else {

            throw new Exception("La categoría a la cúal intenta agregar el exámen no existe.");
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Examen> eliminarExamen(@PathVariable Integer id) throws  Exception{

        Examen eCheck = examenService.buscarExamenPorId(id);

        if(eCheck != null){

            examenService.eliminarExamen(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {

            throw new Exception("El exámen que intenta eliminar no existe.");
        }
    }

    @GetMapping("/{id}/preguntas")
    public ResponseEntity<Set<Preguntas>> buscarPreguntasDeExamen(@PathVariable Integer id) throws Exception{

        Examen eFound = examenService.buscarExamenPorId(id);

        if(eFound != null){

            Set<Preguntas> preguntas = examenService.buscarPreguntasDeExamen(eFound);

            return new ResponseEntity<>(preguntas,HttpStatus.OK);

        } else {

            throw new Exception("Ha ocurrido un error al buscar las preguntas del examen por su ID en el servidor");
        }

    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<Set<Examen>> buscarExamenesPorCategoria(@PathVariable Integer id) throws Exception{

        Categoria cFound = categoriaService.buscarCategoriaPorId(id);

        if(cFound != null){

            Set<Examen> examenes = examenService.buscarExamenPorCategoria(cFound);

            if(examenes != null && !examenes.isEmpty()){

                return new ResponseEntity<>(examenes,HttpStatus.OK);

            } else {

                //EN CASO DE QUE NO HAYA EXÁMENES EN ESA CATEGORÍA, LE INDICAMOS AL FRONT END
                //QUE CON ESTE ARRAYLIST VACIO MUESTRE QUE NO HAY ELEMENTOS AL CLIENTE.
                return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
            }

        } else {

            throw new Exception("La categoría indicada no existe en el servidor");
        }
    }
}
