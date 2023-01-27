package ve.com.tps.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="roles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Rol {

    @Id
    private Integer rol_id;

    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "rol")
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Rol rol = (Rol) o;
        return rol_id != null && Objects.equals(rol_id, rol.rol_id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
