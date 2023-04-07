package es.upm.dit.isst.grupo01.medcon01.controller;

import java.time.LocalDate;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;  

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import es.upm.dit.isst.grupo01.medcon01.model.Paciente;
import es.upm.dit.isst.grupo01.medcon01.repository.PacienteRepository;

@RestController
public class PacienteController {
    private PacienteRepository PacienteRepository;
    public PacienteController(PacienteRepository pacienteRepository){
        this.PacienteRepository = pacienteRepository;
    }
    private MedicoController medicoController;
    
    @GetMapping("/inicio_kiosko")
    public String showInicioKiosko() {
        return "inicio_kiosko";
    }
  
    @GetMapping("/login_DNI")
    public String showLoginDNI() {
        return "login_DNI";
    }

    @GetMapping("/login_tarjeta")
    public String showLoginTarjeta() {
        return "login_tarjeta";
    }

    @GetMapping("/error_cita")
    public String showErrorCita() {
        return "error_cita";
    }

    @GetMapping("/informacion_cita")
    public String showInformacionCita() {
        return "informacion_cita";
    }
    /*sin consulta a la base de datos */
    @PostMapping("/login_DNI")
    public String registrarPacienteDNI(@RequestParam("dni") String dni, Model model) {
     if ( dni.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
        Paciente paciente = new Paciente();
        // marcamos el paciente como presente
           paciente.setPresente(true);
           paciente = PacienteRepository.save(paciente);
           // Agregamos al paciente a la lista del médico
           medicoController.addPaciente(paciente);
           // Agregamos los datos del paciente a la vista
         model.addAttribute("dni", dni);
         model.addAttribute("nombre", paciente.getNombre());
         model.addAttribute("cita", paciente.getCita());
         model.addAttribute("paciente", paciente);
        
          return "redirect:/informacion_cita";     
     } else {  
        return "redirect:/error_cita";
         
     } 
}
/* sin consulta a la base de datos  */
    @PostMapping("/login_tarjeta")
    public String registrarPacienteTarjeta(@RequestParam("nTarjeta") String nTarjeta, Model model ) {
    // Aquí va el código para validar el numero
    if (nTarjeta.length() == 10 && nTarjeta.matches("[0-9]+")) {
        Paciente paciente = new Paciente();
        // Marcamos al paciente como presente
    paciente.setPresente(true);
    paciente = PacienteRepository.save(paciente);
    // Agregamos al paciente a la lista del médico
    medicoController.addPaciente(paciente);
    model.addAttribute("paciente", paciente);
        return "redirect:/informacion_cita";
    } else {
        return "redirect:/error_cita";
    }
}
   
    /* buscando en la base de datos 
    @PostMapping("/login_DNI")
   public String registrarPacienteDNI(@RequestParam("dni") String dni, Model model) {
    // Buscamos el paciente en la base de datos
    Paciente paciente = PacienteRepository.findByDni(dni);
    
    if (paciente == null) {
        model.addAttribute("error", "El paciente no está registrado.");
        return "redirect:/error_cita";
    } else if (!dni.matches("\\d{8}[A-HJ-NP-TV-Z]") || !paciente.getDni().equals(dni)) {
        return "redirect:/error_cita";
    } else {
        // Marcamos al paciente como presente
        paciente.setPresente(true);
        paciente = PacienteRepository.save(paciente);
        // Agregamos al paciente a la lista del médico
        medicoController.addPaciente(paciente);
         
         // Agregamos los datos del paciente a la vista
         model.addAttribute("dni", dni);
         model.addAttribute("nombre", paciente.getNombre());
         model.addAttribute("cita", paciente.getCita());
         model.addAttribute("paciente", paciente);
        
       return "redirect:/informacion_cita";
       // return "redirect:/medico/welcome";
        
    } 
}
    */

/* consultando a la base de datos  
    @PostMapping("/login_tarjeta")
    public String registrarPacienteTarjeta(@RequestParam("nTarjeta") String nTarjeta, Model model ) {
        Paciente paciente = PacienteRepository.findByNTarjeta(nTarjeta);
        if (paciente == null) {
            model.addAttribute("error", "El paciente ya está registrado.");
            return "redirect:/error_registro";
        }
        // Aquí va el código para validar el numero
        if (nTarjeta.length() == 12 && nTarjeta.matches("[0-9]+")) {
           // Marcamos al paciente como presente
        paciente.setPresente(true);
        paciente = PacienteRepository.save(paciente);
        // Agregamos al paciente a la lista del médico
        medicoController.addPaciente(paciente);
        model.addAttribute("paciente", paciente);
            return "redirect:/informacion_cita";
        } else {
            return "redirect:/error_cita";
        }
    }
    */

    @PostMapping("/comparar_hora_llegada")
    public String compararHoraLlegada(@RequestParam("id") String id, @RequestParam("horaLlegada") String horaLlegada, Model model) {
        // Obtener el paciente de la base de datos por su ID
        Paciente paciente = PacienteRepository.findById(id).orElse(null);
    
        if (paciente != null) {
            // Obtener la hora de la cita del paciente
            LocalDate horaCita = paciente.getHoraCita();
    
            // Obtener la hora de llegada del paciente
            LocalDateTime horaLlegadaParsed = LocalDateTime.parse(horaLlegada, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    
            // Calcular la diferencia de tiempo entre la hora de llegada y la hora de la cita
            Duration diferenciaTiempo = Duration.between((Temporal) horaCita, horaLlegadaParsed);
            long minutosDiferencia = diferenciaTiempo.toMinutes();
    
            if (minutosDiferencia <= 0) {
                // Si la hora de llegada es anterior o igual a la hora de la cita, se coloca en la cola como el paciente inmediatamente siguiente al último que esté en la cola
                Paciente ultimoPaciente = PacienteRepository.findTopByOrderByOrdenDesc();
                int orden = ultimoPaciente != null ? ultimoPaciente.getOrden() + 1 : 1;
                paciente.setOrden(orden);

            } else if (minutosDiferencia <= 14) {
                // Si la hora de llegada del paciente es entre 1 y 14 minutos (inclusive) después de la hora de la cita,
                // el paciente se coloca inmediatamente después del paciente que se encuentre en la cola con hora de cita más próxima a la del paciente que acaba de llegar.
                Paciente pacienteMasProximo = PacienteRepository.findTop1ByCita_HoraAfterOrderByCita_HoraAsc(horaLlegadaParsed);
                int orden = pacienteMasProximo != null ? pacienteMasProximo.getOrden() + 1 : 1;
                paciente.setOrden(orden);

            } else {
                // Si la hora de llegada es 15 minutos posterior a la hora de la cita, se coloca al paciente en el último lugar de la lista aunque su hora de cita sea anterior.
                Paciente ultimoPaciente = PacienteRepository.findTopByOrderByOrdenDesc();
                int orden = ultimoPaciente != null ? ultimoPaciente.getOrden() + 1 : 1;
                paciente.setOrden(orden);
            }
    
            // Guardar los cambios en la base de datos
            PacienteRepository.save(paciente);
    
            // Redirigir a la página de información de la cita
            model.addAttribute("paciente", paciente);
            return "redirect:/informacion_cita";
            
        } else {
            // Si no se encuentra el paciente, redirigir a la página de error de cita
            return "redirect:/error_cita";
        }
    }
}

  




