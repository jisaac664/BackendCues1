package SystemITR.ApiJosue2B.Empleados.Repository;

import SystemITR.ApiJosue2B.Empleados.Entity.EmpleadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadosRepository extends JpaRepository<EmpleadosEntity, Long> {
    boolean existsByEmail(String email);
}
