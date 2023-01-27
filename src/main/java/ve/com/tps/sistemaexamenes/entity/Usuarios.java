package ve.com.tps.sistemaexamenes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="usuarios")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String nombre;

    private String apellido;

    private String email;

    private String telefono;

    private Integer enabled = 1;

    private String perfil;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "usuario")
    @JsonIgnore
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Usuarios usuarios = (Usuarios) o;
        return id != null && Objects.equals(id, usuarios.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }



}
