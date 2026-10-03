package SystemITR.ApiJosue2B.Empleados.Service;

import SystemITR.ApiJosue2B.Departamentos.Entity.DepartamentosEntity;
import SystemITR.ApiJosue2B.Empleados.DTO.EmpleadoDTO;
import SystemITR.ApiJosue2B.Empleados.Entity.EmpleadosEntity;
import SystemITR.ApiJosue2B.Empleados.Repository.EmpleadosRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmpleadosService {

    private final EmpleadosRepository repo;

    public EmpleadoDTO crearEmpleado(@Valid EmpleadoDTO dto){
        if (repo.existsByEmail(dto.getEmail())){
            throw new EmpleadoCorreoDuplicadoExeption("El correo ya esta asociado con otro empleado, verifique la informacion");
        }
        //Registrar informacion
        EmpleadosEntity dataEntity = ConvertToEntity(dto);
    }

    /**
     *
     * @param dto
     * @return objeto de tipo EmpleadosEntity
     */
    private EmpleadosEntity ConvertToEntity(@Valid EmpleadoDTO dto) {
        //1. Crea la instancia que se retornara el metodo
        EmpleadosEntity instanciaEntity = new EmpleadosEntity();
        //2. Realiza la conversion de datos
        instanciaEntity.setNombre(dto.getNombre());
        instanciaEntity.setApellido(dto.getApellido());
        instanciaEntity.setEmail(dto.getEmail());
        instanciaEntity.setFechaIngreso(dto.getFechaIngreso());
        instanciaEntity.setSalario(dto.getSalario());

        //Se toma el ID del departamento y se verifica la existencia del departamento y
        // que departamento es.
        if(dto.getIdDepartamento() != null){
            DepartamentosEntity objDepartamento = repoDepartamento.findById(dto.getIdDepartamento())
                    .orElseThrow(() -> new DataNotFoundException("Datos no encontrados."));
            instanciaEntity.setDepartamento(objDepartamento);
        }
        return instanciaEntity;
    }
}
