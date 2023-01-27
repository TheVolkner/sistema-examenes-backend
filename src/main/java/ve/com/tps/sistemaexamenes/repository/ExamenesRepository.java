package ve.com.tps.sistemaexamenes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.tps.sistemaexamenes.entity.Categoria;
import ve.com.tps.sistemaexamenes.entity.Examen;

import java.util.Set;

public interface ExamenesRepository extends JpaRepository<Examen,Integer> {

    Set<Examen> findAllByCategoria(Categoria c);
}
