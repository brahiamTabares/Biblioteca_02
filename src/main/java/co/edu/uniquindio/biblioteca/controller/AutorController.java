package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.*;
import co.edu.uniquindio.biblioteca.servicios.AutorServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autor")
@AllArgsConstructor
public class AutorController {

    private final AutorServicio autorServicio;

    @PostMapping
    public ResponseEntity<Respuesta<AutorGet>> save(@RequestBody AutorPost autor){
        return ResponseEntity.status(HttpStatus.CREATED).body( new Respuesta<>("Autor creado correctamente", autorServicio.save(autor)) );
    }
    @GetMapping("/{idAutor}")
    public ResponseEntity<Respuesta<AutorGet>> findById(@PathVariable long idAutor){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", autorServicio.findById(idAutor)) );
    }
    @GetMapping
    public ResponseEntity<Respuesta<List<AutorGet>>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", autorServicio.findAll()) );

    }
    @DeleteMapping("/{idAutor}")
    public ResponseEntity<Respuesta<String>> delete(@PathVariable long idAutor){
        autorServicio.delete(idAutor);
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("Se eliminó correctamente") );
    }

    @PutMapping("/{idAutor}")
    public ResponseEntity<Respuesta<AutorGet>> update(@PathVariable long idAutor, @RequestBody AutorPost autor){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("El cliente se modificó correctamente", autorServicio.update(idAutor, autor)) );
    }

}
