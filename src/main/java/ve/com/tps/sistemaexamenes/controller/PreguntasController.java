package ve.com.tps.sistemaexamenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.sistemaexamenes.entity.Examen;
import ve.com.tps.sistemaexamenes.entity.Preguntas;
import ve.com.tps.sistemaexamenes.services.ExamenService;
import ve.com.tps.sistemaexamenes.services.PreguntasService;

import java.util.*;

@RestController
@RequestMapping("/preguntas")
@CrossOrigin("*")
public class PreguntasController {

    @Autowired
    private PreguntasService preguntasService;

    @Autowired
    private ExamenService examenService;


    @PostMapping("/agregar")
    public ResponseEntity<Preguntas> agregarPregunta(@RequestBody Preguntas p) throws Exception{

        //SOLICITAMOS EL EXAMEN SOBRE EL CUAL SE QUIEREN GUARDAR LAS PREGUNTAS
        Examen eCheck = examenService.buscarExamenPorId(p.getExamen().getId_examen());

        //COMPROBAMOS SI EL EXAMEN EXISTE
        if(eCheck != null){

            //LE ASIGNAMOS EL EXAMEN A LA PREGUNTA Y ACTUALIZAMOS
            p.setExamen(eCheck);
            Preguntas pSaved = preguntasService.agregarPregunta(p);

            if(pSaved != null){

                return new ResponseEntity<>(pSaved, HttpStatus.CREATED);
            } else {

                throw new Exception("Ha ocurrido un error al actualizar la pregunta en el servidor");
            }
        } else {
            throw new Exception("El examen que está intentando asignarle a la pregunta no existe.");
        }


    }

    @GetMapping()
    public ResponseEntity<List<Preguntas>> listarPreguntas() throws Exception{

        List<Preguntas> listadoPreguntas = preguntasService.listarPreguntas();

        if(listadoPreguntas != null){

            return new ResponseEntity<>(listadoPreguntas, HttpStatus.OK);
        } else {

            throw new Exception("Ha ocurrido un error al listar las preguntas del servidor");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Preguntas> buscarPreguntaPorId(@PathVariable Integer id) throws Exception{

        Preguntas pFound = preguntasService.buscarPreguntaPorId(id);

        if(pFound != null){

            return new ResponseEntity<>(pFound, HttpStatus.CREATED);
        } else {

            throw new Exception("Ha ocurrido un error al buscar una preguntar por su ID en el servidor");
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<Preguntas> actualizarPregunta(@RequestBody Preguntas p) throws Exception{

        //SOLICITAMOS EL EXAMEN SOBRE EL CUAL SE QUIEREN GUARDAR LAS PREGUNTAS
        Examen eCheck = examenService.buscarExamenPorId(p.getExamen().getId_examen());

        //COMPROBAMOS SI EL EXAMEN EXISTE
        if(eCheck != null){

            Preguntas pCheck = preguntasService.buscarPreguntaPorId(p.getPregunta_id());

            if(pCheck != null){

                //LE ASIGNAMOS EL EXAMEN A LA PREGUNTA Y ACTUALIZAMOS
                p.setExamen(eCheck);
                Preguntas pSaved = preguntasService.modificarPregunta(p);

                if(pSaved != null){

                    return new ResponseEntity<>(pSaved, HttpStatus.CREATED);

            } else {

                    throw new Exception("La pregunta que intenta actualizar no existe");
                }

            } else {

                throw new Exception("Ha ocurrido un error al actualizar la pregunta en el servidor");
            }
        } else {
            throw new Exception("El examen que está intentando asignarle a la pregunta no existe.");
        }

    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Preguntas> borrarPregunta(@PathVariable Integer id) throws Exception{

        Preguntas pCheck = preguntasService.buscarPreguntaPorId(id);

        if(pCheck != null){

            preguntasService.eliminarPregunta(id);

            return new ResponseEntity<>(HttpStatus.OK);

        } else {

            throw new Exception("La pregunta que intenta eliminar no existe.");
        }

    }

    @GetMapping("/examen/{id}")
    public ResponseEntity<Set<Preguntas>> listarPreguntasPorExamen(@PathVariable Integer id) throws Exception{

        //BUSCAMOS EL EXAMEN SEGUN EL ID RECIBIDO POR PARÁMETRO
        Examen eFound = examenService.buscarExamenPorId(id);

        //VERIFICAMOS SI EL EXAMEN EXISTE
        if(eFound != null){

            Set<Preguntas> preguntas = preguntasService.buscarPreguntasPorExamen(eFound);

            //COMPROBAMOS QUE LA CANTIDAD DE PREGUNTAS ASOCIADAS A ESE EXAMEN, NO SUPERE SU LIMITE
            if(preguntas.size() <= eFound.getNumero_de_preguntas()){

                return new ResponseEntity<>(preguntas,HttpStatus.OK);

            } else {

                throw new Exception("La cantidad de preguntas asociadas a este exámen en el servidor es mayor a su límite. Contacte al Administador");
            }

        } else {

            throw new Exception("No existe un Exámen asociado a ese ID.");
        }
    }

    //ESTE MÉTODO SE ENCARGA DE VALIDAR LAS PREGUNTAS DEL EXÁMEN DEL CLIENTE
    @PostMapping("/evaluar-examen")
    public ResponseEntity<Map<String,Double>> evaluarExamen(@RequestBody List<Preguntas> preguntas){

        //CREAMOS LOS ATRIBUTOS PARA ENUMERAR GUARDAN LOS PUNTOS MÁXIMOS Y LAS RESPUESTAS ACERTADAS
        Double puntosMaximos = 0.0;
        Integer respuestasCorrectas = 0;


        //CICLAMOS LAS PREGUNTAS PARA EMPEZAR LA VALIDACIÓN
        for(Preguntas p : preguntas){
            //COMPROBAMOS CADA PREGUNTA SOLICITANDOLA A A LA BBDD
            Preguntas pFound = preguntasService.buscarPreguntaPorId(p.getPregunta_id());

            Double puntosExamen = Double.valueOf(pFound.getExamen().getPuntos_maximos());
            Double preguntasTotales = Double.valueOf(preguntas.size());

            //COMPROBAMOS SI LA RESPUESTA DADA CON LA DE LA PREGUNTA DE LA BBDD
            if(pFound.getRespuesta().equals(p.getRespuestaDada())){

                //SI COINCIDEN,SE LE SUMAN PUNTOS Y LE SUMA UNA RESPUESTA ACERTADA
                Double puntos = puntosExamen / preguntasTotales;
                puntosMaximos += puntos;
                respuestasCorrectas++;
            }
        }

        //CREAMOS UN MAPA DONDE INDICAMOS LOS RESULTADOS Y LO DEVOLVEMOS
        Map<String,Double> resultado = new HashMap<>();

        resultado.put("puntosMaximos",puntosMaximos);
        resultado.put("respuestasCorrectas",(double) respuestasCorrectas);
        return new ResponseEntity<>(resultado,HttpStatus.OK);

    }



}
