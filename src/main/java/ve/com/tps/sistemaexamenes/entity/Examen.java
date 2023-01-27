package ve.com.tps.sistemaexamenes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="examen")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_examen;

   private Integer activo;

   private String descripcion;

   private Integer numero_de_preguntas;

   private Integer puntos_maximos;

   private String titulo;

   //AL REALIZAR RELACIONES DE LLAVES FORANEAS, SE COLOCA EL FETH EN EAGER, Y EL JOIN COLUMN
   //EN LA ENTIDAD QUE TIENE LA LLAVE FORÁNEA.
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "categoria",referencedColumnName = "id_categoria")
   private Categoria categoria;

   //Y EN LA ENTIDAD QUE RECIBA LA COMUNICACIÓN POR LLAVE FORÁNEA, INDICAMOS EL CASCADE Y
   //MUY IMPORTANTE EL JSON IGNORE, PARA PODER ENVIAR LOS CAMPOS VINCULADOS, SI NO, NO LOS ENVIARÁ
   //O INDICARÁ UN ERROR DE CICLO INFINITO
   @OneToMany(mappedBy = "examen",cascade = CascadeType.ALL)
   @JsonIgnore
   private Set<Preguntas> preguntas = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Examen examen = (Examen) o;
        return id_examen != null && Objects.equals(id_examen, examen.id_examen);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
