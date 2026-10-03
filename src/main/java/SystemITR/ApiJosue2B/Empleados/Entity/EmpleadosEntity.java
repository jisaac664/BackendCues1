package SystemITR.ApiJosue2B.Empleados.Entity;

import SystemITR.ApiJosue2B.Departamentos.Entity.DepartamentosEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "TBEMPLEADOS")
@Getter @Setter
public class EmpleadosEntity {

    @Id //<- Llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLEADO_ID")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "FECHA_INGRESO")
    private LocalDate fechaIngreso;

    @Column(name = "SALARIO")
    private double salario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTAMENTO_ID")
    private DepartamentosEntity departamento;
}