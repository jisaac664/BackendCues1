package SystemITR.ApiJosue2B.Departamentos.Repository;

import SystemITR.ApiJosue2B.Departamentos.Entity.DepartamentosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * En un repositorio la anotacion @Repository es obligatoria
 * ¿Porque? por que permite que nuestra propia interface accede a los metodos del CRUD
 * contenidos en el JpaRepository
 */
@Repository
public interface DepartamentosRepository extends JpaRepository<DepartamentosEntity, Long> {
}
