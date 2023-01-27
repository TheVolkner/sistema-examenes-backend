package ve.com.tps.sistemaexamenes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="categoria")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_categoria;

    private String descripcion;

    private String titulo;

    //Y EN LA ENTIDAD QUE RECIBA LA COMUNICACIÓN POR LLAVE FORÁNEA, INDICAMOS EL CASCADE Y
    //MUY IMPORTANTE EL JSON IGNORE, PARA PODER ENVIAR LOS CAMPOS VINCULADOS, SI NO, NO LOS ENVIARÁ
    //O INDICARÁ UN ERROR DE CICLO INFINITO
    @OneToMany(mappedBy = "categoria",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Examen> examenes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Categoria categoria = (Categoria) o;
        return id_categoria != null && Objects.equals(id_categoria, categoria.id_categoria);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
