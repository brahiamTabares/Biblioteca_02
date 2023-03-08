package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.PrestamoDTO;
import co.edu.uniquindio.biblioteca.dto.PrestamoGet;
import co.edu.uniquindio.biblioteca.dto.Respuesta;
import co.edu.uniquindio.biblioteca.servicios.PrestamoServicio;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<Respuesta<PrestamoGet>> findById(@PathVariable long idCliente) {
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", prestamoServicio.findById(idCliente)) );
    }

    @GetMapping("/{clienteID}")
    public ResponseEntity<Respuesta<List<PrestamoDTO>>> obtenerClientesPrestamo(@PathVariable Long clienteID,PrestamoDTO prestamoDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Prestamos encontrados", prestamoServicio.obtenerClientesPrestamo(clienteID,prestamoDTO)));
    }

    @GetMapping("/ObtenerPrestamoLibroIsbn/{isbn}")
    public ResponseEntity<Respuesta<Long>> obtenerPrestamoLibroIsbn(@PathVariable String isbn) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Prestamos encontrados", prestamoServicio.obtenerPrestamoLibroIsbn(isbn)));
    }
    @DeleteMapping ("/{codigoPrestamo}")
    public ResponseEntity<Respuesta<String>> delete(@PathVariable Long codigoPrestamo) {
        prestamoServicio.delete(codigoPrestamo);
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Prestamos eliminado exitosamente"));
    }
    @PutMapping("/{prestamoId}")
    public ResponseEntity<Respuesta<PrestamoDTO>> update(@PathVariable long codigoPrestamo, @RequestBody PrestamoDTO  prestamoDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Prestamos actualizado exitosamente", prestamoServicio.update(codigoPrestamo, prestamoDTO)));
    }
    @GetMapping("/ obtenerPrestamoFecha/{date}")
    public ResponseEntity<Respuesta<List<PrestamoGet>>>  obtenerPrestamoFecha(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Prestamos encontrados", prestamoServicio.obtenerPrestamoFecha(date)));
    }
}


