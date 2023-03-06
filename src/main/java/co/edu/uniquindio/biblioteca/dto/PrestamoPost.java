package co.edu.uniquindio.biblioteca.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PrestamoPost (long clienteID, List<String> isbnLibros, LocalDateTime fechaPrestamo, LocalDateTime fechaDevolucion){
    }

