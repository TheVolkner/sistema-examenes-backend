package ve.com.tps.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UsuarioRolid;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuarios usuario;

    @ManyToOne
    private Rol rol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsuarioRol that = (UsuarioRol) o;
        return UsuarioRolid != null && Objects.equals(UsuarioRolid, that.UsuarioRolid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
