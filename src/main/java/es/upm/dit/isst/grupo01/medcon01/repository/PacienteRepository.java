
package es.upm.dit.isst.grupo01.medcon01.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.grupo01.medcon01.model.Paciente;

public interface PacienteRepository extends CrudRepository<Paciente, String> {
    // aquí se añadirán las cosas que pueda devolver el repositorio de paciente
    Paciente findByIdpaciente (String idpaciente);
    Paciente findByDni (String dni);
    Paciente findByNtarjeta (String ntarjeta);
}


