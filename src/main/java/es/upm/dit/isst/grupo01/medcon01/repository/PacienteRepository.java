
package es.upm.dit.isst.grupo01.medcon01.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.upm.dit.isst.grupo01.medcon01.model.Paciente;

public interface PacienteRepository extends CrudRepository<Paciente, String> {
    // aquí se añadirán las cosas que pueda devolver el repositorio de paciente
    Paciente findByDni (String dni);
    Paciente findByNumTarjeta (int numTarjeta);
    
}


