package SystemITR.ApiJosue2B.Departamentos.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class DepartamentoDTO {

    private Long id;
    @NotBlank
    @Size(max = 50, message = "Longitud del departamento incorrecta.")
    private String nombreDepto;
    @NotBlank
    @Size(max = 5, message = "La abreviatura no puede exceder de cinco caracteres.")
    private String abreviatura;
    @NotBlank
    private String ubicacion;
}
