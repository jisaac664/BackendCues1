package SystemITR.ApiJosue2B.Departamentos.Entity;

import SystemITR.ApiJosue2B.Empleados.Entity.EmpleadosEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Es una entidad la anotacion @Entity es obligatoria
 */

@Entity
@Getter @Setter @ToString
@Table(name = "TBDEPARTAMENTOS")
public class DepartamentosEntity {

    @Id //-> La anotación Id indica quien es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTAMENTO_ID")
    private Long id;

    @Column(name = "NOMBRE_DEPT")
    private String nombreDepto;

    @Column(name = "ABREVIATURA")
    private String abreviatura;

    @Column(name = "UBICACION")
    private String ubicacion;

    //Campo adicional
    @OneToMany(mappedBy = "idDepartamento")
    private List<EmpleadosEntity> empleados = new ArrayList<>();
}
