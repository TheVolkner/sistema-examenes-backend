package ve.com.tps.sistemaexamenes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.tps.sistemaexamenes.entity.Examen;
import ve.com.tps.sistemaexamenes.entity.Preguntas;

import java.util.Optional;
import java.util.Set;

public interface PreguntasRepository extends JpaRepository<Preguntas,Integer> {

    Optional<Set<Preguntas>> findAllByExamen(Examen e);


}
