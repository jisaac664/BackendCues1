package SystemITR.ApiJosue2B.Departamentos.Service;

import SystemITR.ApiJosue2B.Departamentos.DTO.DepartamentoDTO;
import SystemITR.ApiJosue2B.Departamentos.Entity.DepartamentosEntity;
import SystemITR.ApiJosue2B.Departamentos.Exeptions.ErrorException;
import SystemITR.ApiJosue2B.Departamentos.Repository.DepartamentosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Toda service necesita obligatoriamente la anotacion @Service, ya que esta clase permitira invocar a los
 * metodos del JpaRepository que han sido heredado a nuestra interface.
 */

@Slf4j
@Service
public class DepartamentosService {
    /**
     * Inyectando la interface al Service
     */
    private final DepartamentosRepository repo;
    public DepartamentosService(DepartamentosRepository repo) {
        this.repo = repo;
    }
    /**
     * La funcion nuevoDepartamento recibe un objeto de tipo DepartamentoDTO
     * @param dto
     * @return
     * La funcion retorna/devuelve un objeto de tipo DepartamentoDTO
     */
    public DepartamentoDTO nuevoDepartamento(DepartamentoDTO dto){
        DepartamentosEntity datosConvertidos = convertirAEntity(dto);
        /**
         * Realizar la inserción a la base de datos utilizando los metodos del JpaRepository
         */
        DepartamentosEntity respuesta = repo.save(datosConvertidos);
        return convertirADTO(respuesta);
    }

    private DepartamentoDTO convertirADTO(DepartamentosEntity respuesta) {
        DepartamentoDTO valor = new DepartamentoDTO();
        valor.setId(respuesta.getId());
        valor.setNombreDepto(respuesta.getNombreDepto());
        valor.setAbreviatura(respuesta.getAbreviatura());
        valor.setUbicacion(respuesta.getUbicacion());
        return valor;
    }

    /**
     * Conversion de datos, donde se recibe un objeto DTO
     * @param dto
     * @return se retorna un objeto de tipo Entity
     */
    private DepartamentosEntity convertirAEntity(DepartamentoDTO dto) {
        DepartamentosEntity valor = new DepartamentosEntity();
        valor.setNombreDepto(dto.getNombreDepto());
        valor.setAbreviatura(dto.getAbreviatura());
        valor.setUbicacion(dto.getUbicacion());
        return valor;
    }

    public List<DepartamentoDTO> obtenerTodo() {
        //Paso 1. Consultar a la base
        List<DepartamentosEntity> entidades = repo.findAll();
        //Paso 2. Convertir el listado de entidades a dtos
        List<DepartamentoDTO> dtos = new ArrayList<>();
        for (DepartamentosEntity entity: entidades){
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }

    public DepartamentoDTO obtenerPorId(Long id) {
        //Crear un objeto opcional
        Optional<DepartamentosEntity> entidadOpcional = repo.findById(id);
        //Validar si existe, en caso fuera cierto lo convertimos a DTO, caso contrario retornamos null
        if (entidadOpcional.isPresent()){
            return convertirADTO(entidadOpcional.get());
        }
        return null;
    }

    public boolean eliminarDepartamento(Long id) {
        if(repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public DepartamentoDTO actualizarData(DepartamentoDTO dto, Long id) {
        try{
            //1. Buscamos si el departamento realmente existe
            Optional<DepartamentosEntity> registroExistente = repo.findById(id);
            //2. Se verifica si el objeto contiene valores
            if (registroExistente.isPresent()){
                //3. Se verifica si el objeto contiene valores
                DepartamentosEntity entidad = registroExistente.get();
                //4. Convertir y asignar los dtos (nuevos valores) a la entidad
                entidad.setNombreDepto(dto.getNombreDepto());
                entidad.setAbreviatura(dto.getAbreviatura());
                entidad.setUbicacion(dto.getUbicacion());
                //5. Actualizar datos en la base de datos
                DepartamentosEntity datosGuardados = repo.save(entidad);
                //6. Retornar la data convertir a DTO
                return convertirADTO(datosGuardados);
            }
            return null;
        }catch (Exception e){
            log.error("Ooops, ocurrio un error al procesar la informacion");
            return null;
        }
    }
}
