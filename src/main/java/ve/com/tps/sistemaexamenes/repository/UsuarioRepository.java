package ve.com.tps.sistemaexamenes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.tps.sistemaexamenes.entity.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios,Integer> {

    public Usuarios findByUsername(String username);

}
