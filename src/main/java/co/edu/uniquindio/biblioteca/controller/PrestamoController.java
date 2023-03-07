package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.PrestamoDTO;
import co.edu.uniquindio.biblioteca.dto.Respuesta;
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
    public ResponseEntity<Respuesta<PrestamoDTO>> save(@RequestBody PrestamoDTO prestamoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Respuesta<>("Prestamo creado correctamente", prestamoServicio.save(prestamoDTO)));
    }

    @GetMapping
    public ResponseEntity<Respuesta<List<PrestamoDTO>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", prestamoServicio.findAll()) );
    }

    @GetMapping("/{clienteID}")
    public ResponseEntity<Respuesta<List<PrestamoDTO>>> obtenerClientesPrestamo(@PathVariable Long clienteID,PrestamoDTO prestamoDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Prestamos encontrados", prestamoServicio.obtenerClientesPrestamo(clienteID,prestamoDTO)));
    }

    @DeleteMapping ("/{clienteID}")
    public String delete(@PathVariable long clienteID){

       prestamoServicio.delete(clienteID);

        return  "se elimino";
    }

    @PutMapping("/prestamoId}")
    public ResponseEntity<Respuesta<PrestamoDTO>> update(@PathVariable long codigoPrestamo, @RequestBody PrestamoDTO  prestamoDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Prestamos actualizado exitosamente", prestamoServicio.update(codigoPrestamo, prestamoDTO)));
    }
}


