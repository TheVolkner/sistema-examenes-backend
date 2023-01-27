package ve.com.tps.sistemaexamenes.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ve.com.tps.sistemaexamenes.entity.Categoria;
import ve.com.tps.sistemaexamenes.repository.CategoriaRepository;
import ve.com.tps.sistemaexamenes.services.CategoriaService;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listarCategorias() {

        return categoriaRepository.findAll();
    }

    @Override
    public Categoria agregarCategoria(Categoria c) {

        return categoriaRepository.save(c);
    }

    @Override
    public Categoria modificarCategoria(Categoria c) {
        return categoriaRepository.save(c);
    }

    @Override
    public void eliminarCategoria(Integer id) {

        categoriaRepository.deleteById(id);
    }

    @Override
    public Categoria buscarCategoriaPorId(Integer id) {

        return categoriaRepository.findById(id).get();
    }
}
