package ve.com.tps.sistemaexamenes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.tps.sistemaexamenes.entity.Categoria;
import ve.com.tps.sistemaexamenes.entity.Examen;
import ve.com.tps.sistemaexamenes.entity.Preguntas;

import java.util.Set;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {


}
