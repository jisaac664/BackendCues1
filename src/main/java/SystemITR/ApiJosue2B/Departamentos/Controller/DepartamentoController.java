package SystemITR.ApiJosue2B.Departamentos.Controller;

import SystemITR.ApiJosue2B.Departamentos.DTO.DepartamentoDTO;
import SystemITR.ApiJosue2B.Departamentos.Service.DepartamentosService;
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
@RequestMapping("/api/departamentos")
@CrossOrigin
public class DepartamentoController {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoController.class);
    private final DepartamentosService service;

    public DepartamentoController(DepartamentosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartamentoDTO>> nuevoDepartamento(@Valid @RequestBody DepartamentoDTO json){
        try {
            DepartamentoDTO dto = service.nuevoDepartamento(json);
            ApiResponse<DepartamentoDTO> response = new ApiResponse<>(true, "Proceso completado", json);
            log.info("Nuevo departamento registrado: " + dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            // El error de log se muetra en el historial de acciones en el servidor
            log.error("No se pudo completar el proceso de inserción.");
            // La respuesta de ApiResponse es la que el usuario verá.
            ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(false,"No se pudo completar el proceso e insercion",json);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartamentoDTO>>> obtenerDatos(){
        try {
            //Intentar hacer el proceso
            //El bloque try se ejecuta para obtener los datos, en dado ocurra un error, automaticamente se ejecutara el bloque catch
            List<DepartamentoDTO> lista = service.obtenerTodo();
            ApiResponse<List<DepartamentoDTO>> respuestaExitosa = new ApiResponse<>(true,"Proceso completado", lista);
            return ResponseEntity.ok(respuestaExitosa);
        }
        catch (Exception e){
            //Catch se ejecuta cuando ocurre una exepcion (error) no controlada.
            e.printStackTrace();
            ApiResponse<List<DepartamentoDTO>> respuestaError = new ApiResponse<>(false, "No se pudo obtener los datos de departamentos",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @GetMapping ("/{id}")
        public ResponseEntity<ApiResponse<DepartamentoDTO>> obtenerDatosPorId(@PathVariable Long id){
        try {
           //Catch se ejecuta cuando ocurre una exepcion (error) no controlada.
            DepartamentoDTO valor = service.obtenerPorId(id);
            if (valor != null){
                //Armar nuestra respuesta
                ApiResponse<DepartamentoDTO> respuestaExitosa = new ApiResponse<>(true,"Proceso completado", valor);
                return ResponseEntity.ok(respuestaExitosa);
            }
            ApiResponse<DepartamentoDTO> noEncontrado = new ApiResponse<>(false, "No se encontraron datos sociados al ID: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noEncontrado);
        }catch(Exception e){
            //Catch se ejecuta cuando ocurre una exepcion (error) no controlada.
            e.printStackTrace();
            ApiResponse<DepartamentoDTO> respuestaError = new ApiResponse<>(false, "No se pudo obtener el departamento con ID: " + id, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDatos(@PathVariable Long id){
        try {
            boolean respuesta = service.eliminarDepartamento(id);
            if (respuesta){
                ApiResponse<Void> respuestaExitosa = new ApiResponse<>(true, "Departamento con ID: "+id+"ha sido eliminado");
                log.info("Departamento con ID: "+id+" ha sido eliminado");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuestaExitosa);
            }
            ApiResponse<Void> respuestaNoRealizado = new ApiResponse<>(false, "No se pudo completar el proceso de eliminacion");
            log.warn("No se pudo completar el proceso de eliminacion");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaNoRealizado);
        }catch (Exception e){
            log.error("Error, critico en la eliminacion de departamento con ID, consulte con el adnimistrador."); //-> Accion queda registrada en el servidor.
            //Catch
            e.printStackTrace();
            ApiResponse<Void> respuestaError = new ApiResponse<>(false, "Error critico en la eliminacion de departamento");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartamentoDTO>> actualizarDepartamento(@PathVariable Long id, @RequestBody DepartamentoDTO dto){
        try {
            DepartamentoDTO data = service.actualizarData(dto, id);
            if (data != null){
                ApiResponse<DepartamentoDTO> respuestaExito = new ApiResponse<>(true, "Proceso completado", data);
                log.info("El departamento con ID" + id + ", fue actualizado.");
                return ResponseEntity.ok(respuestaExito);
            }
            ApiResponse<DepartamentoDTO> respuestaFallida = new ApiResponse<>(true, "Proceso no completado");
            log.info("El departamento con ID" + id + ", no se pudo actualizar");
            return ResponseEntity.ok(respuestaFallida);
        } catch (Exception e){
            log.error("Error, critico en la actualizacon de departamento con ID, consulte con el adnimistrador."); //-> Accion queda registrada en el servidor.
            //Catch
            e.printStackTrace();
            ApiResponse<DepartamentoDTO> respuestaError = new ApiResponse<>(false, "Error critico en la actualizacion de departamento");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }
}
