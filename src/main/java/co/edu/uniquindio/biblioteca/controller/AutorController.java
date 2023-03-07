package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.*;
import co.edu.uniquindio.biblioteca.servicios.AutorServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CRUD para recibir las diferentes peticiones que realiza un autor
 */
@RestController
@RequestMapping("/api/autor")
@AllArgsConstructor
public class AutorController {

    private final AutorServicio autorServicio;

    //Crear un nuevo autor
    @PostMapping
    public ResponseEntity<Respuesta<AutorGet>> save(@RequestBody AutorPost autor){
        return ResponseEntity.status(HttpStatus.CREATED).body( new Respuesta<>("Autor creado correctamente", autorServicio.save(autor)) );
    }
    //Obtener un autor por medio del ID
    @GetMapping("/{idAutor}")
    public ResponseEntity<Respuesta<AutorGet>> findById(@PathVariable long idAutor){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", autorServicio.findById(idAutor)) );
    }
    //Obtener todos los autores existentes en la base de datos
    @GetMapping
    public ResponseEntity<Respuesta<List<AutorGet>>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", autorServicio.findAll()) );

    }
    //Eliminar autor por medio del ID
    @DeleteMapping("/{idAutor}")
    public ResponseEntity<Respuesta<String>> delete(@PathVariable long idAutor){
        autorServicio.delete(idAutor);
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("Se eliminó correctamente") );
    }

    //Actualizar autor por medio del id
    @PutMapping("/{idAutor}")
    public ResponseEntity<Respuesta<AutorGet>> update(@PathVariable long idAutor, @RequestBody AutorPost autor){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("El cliente se modificó correctamente", autorServicio.update(idAutor, autor)) );
    }

}
