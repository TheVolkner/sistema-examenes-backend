package ve.com.tps.sistemaexamenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.sistemaexamenes.entity.Categoria;
import ve.com.tps.sistemaexamenes.entity.Examen;
import ve.com.tps.sistemaexamenes.services.CategoriaService;
import ve.com.tps.sistemaexamenes.services.ExamenService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/categoria")
@CrossOrigin("*")
public class CategoriaController {


    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ExamenService examenService;


    @GetMapping()
    public ResponseEntity<List<Categoria>> listarCategorias() throws Exception{
        
        List<Categoria> listaCategorias = categoriaService.listarCategorias();
        
        if(listaCategorias != null){

            return new ResponseEntity<>(listaCategorias, HttpStatus.OK);
        } else {

            throw new Exception("Ha ocurrido un error al listar las categorías del servidor");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Integer id) throws Exception{

        Categoria cFound = categoriaService.buscarCategoriaPorId(id);

        if(cFound != null){

            return new ResponseEntity<>(cFound,HttpStatus.OK);
        }

        throw new Exception("Ha ocurrido un error al buscar la categoría en el servidor");
    }

    @PostMapping("/agregar")
    public ResponseEntity<Categoria> agregarCategoria(@RequestBody Categoria c) throws Exception{

        Categoria cSaved = categoriaService.agregarCategoria(c);

        if(cSaved != null){

            return new ResponseEntity<>(cSaved,HttpStatus.CREATED);
        } else {

            throw new Exception("Ha ocurrido un error al agregar la categoría al servidor");
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<Categoria> editarCategoria(@RequestBody Categoria c) throws Exception{

        Categoria cCheck = categoriaService.buscarCategoriaPorId(c.getId_categoria());

        if(cCheck != null){

            Categoria cSaved = categoriaService.modificarCategoria(c);

            if(cSaved != null) {

                return new ResponseEntity<>(cSaved, HttpStatus.OK);
            } else {

                throw new Exception("Ha ocurrido un error al actualizar la categoria en el servidor");
        }

        } else {

            throw new Exception("La categoria que intenta actualizar no existe en el servidor.");
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Integer id) throws Exception{

        Categoria cCheck = categoriaService.buscarCategoriaPorId(id);

        if(cCheck != null){

            categoriaService.eliminarCategoria(id);
            return new ResponseEntity<>("Categoría Elimanada",HttpStatus.OK);
        } else {

            throw new Exception("La categoria que intenta eliminar no existe en el servidor.");
        }
    }

    @GetMapping("/{id}/examenes")
    public ResponseEntity<Set<Examen>> buscarExamenesPorCategoria(@PathVariable Integer id) throws Exception {

        Categoria categoriaId = categoriaService.buscarCategoriaPorId(id);

        if (categoriaId != null) {

            Set<Examen> examenes = examenService.buscarExamenPorCategoria(categoriaId);

            if (examenes != null && !examenes.isEmpty()) {

                return new ResponseEntity<>(examenes, HttpStatus.OK);
            } else {
                throw new Exception("No se han encontrado examenes correspondientes a la categoría enviada");
            }
        } else {
            throw new Exception("La categoría indicada no existe");
        }
    }

}
