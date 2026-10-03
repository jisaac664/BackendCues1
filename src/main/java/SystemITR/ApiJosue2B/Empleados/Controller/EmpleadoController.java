package SystemITR.ApiJosue2B.Empleados.Controller;

import SystemITR.ApiJosue2B.Empleados.DTO.EmpleadoDTO;
import SystemITR.ApiJosue2B.Empleados.Service.EmpleadosService;
import SystemITR.ApiJosue2B.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin
public class EmpleadoController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);
    private final EmpleadosService service;

    public EmpleadoController(EmpleadosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmpleadoDTO>> nuevoEmpleado(@Valid @RequestBody EmpleadoDTO json){
        try {
            EmpleadoDTO dto = service.nuevoEmpleado(json);
            ApiResponse<EmpleadoDTO> response = new ApiResponse<>(true, "Proceso completado", json);
            log.info("Nuevo empleado registrado: " + dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("No se pudo completar el proceso de inserción.");
            ApiResponse<EmpleadoDTO> respuesta = new ApiResponse<>(false,"No se pudo completar el proceso e insercion",json);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmpleadoDTO>>> obtenerDatos(){
        try {
            List<EmpleadoDTO> lista = service.obtenerTodo();
            ApiResponse<List<EmpleadoDTO>> respuestaExitosa = new ApiResponse<>(true,"Proceso completado", lista);
            return ResponseEntity.ok(respuestaExitosa);
        }
        catch (Exception e){
            e.printStackTrace();
            ApiResponse<List<EmpleadoDTO>> respuestaError = new ApiResponse<>(false, "No se pudo obtener los datos de empleados",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @GetMapping ("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoDTO>> obtenerDatosPorId(@PathVariable Long id){
        try {
            EmpleadoDTO valor = service.obtenerPorId(id);
            if (valor != null){
                ApiResponse<EmpleadoDTO> respuestaExitosa = new ApiResponse<>(true,"Proceso completado", valor);
                return ResponseEntity.ok(respuestaExitosa);
            }
            ApiResponse<EmpleadoDTO> noEncontrado = new ApiResponse<>(false, "No se encontraron datos sociados al ID: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noEncontrado);
        }catch(Exception e){
            e.printStackTrace();
            ApiResponse<EmpleadoDTO> respuestaError = new ApiResponse<>(false, "No se pudo obtener el empleado con ID: " + id, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDatos(@PathVariable Long id){
        try {
            boolean respuesta = service.eliminarEmpleado(id);
            if (respuesta){
                ApiResponse<Void> respuestaExitosa = new ApiResponse<>(true, "Empleado con ID: "+id+"ha sido eliminado");
                log.info("Empleado con ID: "+id+" ha sido eliminado");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuestaExitosa);
            }
            ApiResponse<Void> respuestaNoRealizado = new ApiResponse<>(false, "No se pudo completar el proceso de eliminacion");
            log.warn("No se pudo completar el proceso de eliminacion");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaNoRealizado);
        }catch (Exception e){
            log.error("Error, critico en la eliminacion de empleado con ID, consulte con el adnimistrador.");
            //Catch
            e.printStackTrace();
            ApiResponse<Void> respuestaError = new ApiResponse<>(false, "Error critico en la eliminacion de empleado");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoDTO>> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoDTO dto){
        try {
            EmpleadoDTO data = service.actualizarData(dto, id);
            if (data != null){
                ApiResponse<EmpleadoDTO> respuestaExito = new ApiResponse<>(true, "Proceso completado", data);
                log.info("El empleado con ID" + id + ", fue actualizado.");
                return ResponseEntity.ok(respuestaExito);
            }
            ApiResponse<EmpleadoDTO> respuestaFallida = new ApiResponse<>(true, "Proceso no completado");
            log.info("El empleado con ID" + id + ", no se pudo actualizar");
            return ResponseEntity.ok(respuestaFallida);
        } catch (Exception e){
            log.error("Error, critico en la actualizacon de empleado con ID, consulte con el adnimistrador.");
            //Catch
            e.printStackTrace();
            ApiResponse<EmpleadoDTO> respuestaError = new ApiResponse<>(false, "Error critico en la actualizacion de departamento");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }
}
