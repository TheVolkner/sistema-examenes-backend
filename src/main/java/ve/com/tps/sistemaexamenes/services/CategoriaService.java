package ve.com.tps.sistemaexamenes.services;

import org.springframework.stereotype.Service;
import ve.com.tps.sistemaexamenes.entity.Categoria;
import ve.com.tps.sistemaexamenes.entity.Examen;

import java.util.List;
import java.util.Set;


public interface CategoriaService {

    List<Categoria> listarCategorias();

    Categoria agregarCategoria(Categoria c);

    Categoria modificarCategoria(Categoria c);

    void eliminarCategoria(Integer id);

    Categoria buscarCategoriaPorId(Integer id);

}
