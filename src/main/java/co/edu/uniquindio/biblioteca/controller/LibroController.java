package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.LibroDTO;
import co.edu.uniquindio.biblioteca.dto.Respuesta;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.servicios.LibroServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CRUD para recibir las diferentes peticiones que realiza un libro
 */
@RestController
@RequestMapping("/api/libro")
@AllArgsConstructor
public class LibroController {

    private final LibroServicio libroServicio;

    //Crear un nuevo libro
    @PostMapping
    public ResponseEntity<Respuesta<Libro>> save(@RequestBody LibroDTO libroDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body( new Respuesta<>("Libro creado correctamente", libroServicio.save(libroDTO)) );
    }

    //Obtener todos los libros existentes en la base de datos
    @GetMapping
    public ResponseEntity<Respuesta<List<Libro>>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", libroServicio.findAll()) );
    }

    //Obtener un libro por medio del isbn
    @GetMapping("/{isbnLibro}")
    public ResponseEntity<Respuesta<Libro>> findAll(@PathVariable String isbnLibro){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", libroServicio.findById(isbnLibro)) );
    }

    //Eliminar un libro por medio del isbn
    @DeleteMapping ("/{isbnLibro}")
    public String delete(@PathVariable String isbnLibro){

        libroServicio.delete(isbnLibro);

        return  "se elimino";
    }

    //Modificar un libro por medio del isbn
    @PutMapping("/{isbnLibro}")
    public ResponseEntity<Respuesta<Libro>> update(@PathVariable String isbnLibro, @RequestBody LibroDTO libro){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("El Libro se modificó correctamente", libroServicio.update(isbnLibro,libro)) );
    }
}
