package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.LibroDTO;
import co.edu.uniquindio.biblioteca.dto.PrestamoDTO;
import co.edu.uniquindio.biblioteca.dto.Respuesta;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import co.edu.uniquindio.biblioteca.servicios.PrestamoServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamo")
@AllArgsConstructor
public class PrestamoController {
    private final PrestamoServicio prestamoServicio;

    @PostMapping
    public ResponseEntity<Respuesta<Prestamo>> save(@RequestBody PrestamoDTO prestamoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Respuesta<>("Prestamo creado correctamente", prestamoServicio.save(prestamoDTO)));
    }

    @GetMapping
    public ResponseEntity<Respuesta<List<Prestamo>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", prestamoServicio.findAll()) );
    }

    @GetMapping("/{clienteID}")
    public ResponseEntity<Respuesta<Prestamo>> findAll(@PathVariable long clienteID){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", prestamoServicio.findById(clienteID)) );
    }

    @DeleteMapping ("/{clienteID}")
    public String delete(@PathVariable long clienteID){

       prestamoServicio.delete(clienteID);

        return  "se elimino";
    }

    @PutMapping
    public ResponseEntity<Respuesta<Prestamo>> Update(@RequestBody PrestamoDTO prestamoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Respuesta<>("Prestamo actualizado correctamente", prestamoServicio.save(prestamoDTO)));
    }
}


