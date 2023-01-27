package ve.com.tps.sistemaexamenes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;


@Entity
@Table(name="preguntas")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Preguntas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pregunta_id;

    private String contenido;

    private String imagen;

    private String opcion1;

    private String opcion2;

    private String opcion3;

    private String opcion4;

    private String respuesta;

    //AL TENER ESTA ANOTACIÓN, ESTE ATRIBUTO NO SE PERSISTE EN LA TABLA DE LA BBDD
    @Transient
    private String respuestaDada;

    //AL REALIZAR RELACIONES DE LLAVES FORANEAS, SE COLOCA EL FETH EN EAGER, Y EL JOIN COLUMN
    //EN LA ENTIDAD QUE TIENE LA LLAVE FORÁNEA.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examen",referencedColumnName = "id_examen")
    private Examen examen;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Preguntas preguntas = (Preguntas) o;
        return pregunta_id != null && Objects.equals(pregunta_id, preguntas.pregunta_id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
