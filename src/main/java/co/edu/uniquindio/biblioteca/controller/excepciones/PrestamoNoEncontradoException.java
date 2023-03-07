package co.edu.uniquindio.biblioteca.controller.excepciones;

public class PrestamoNoEncontradoException extends RuntimeException{
    public PrestamoNoEncontradoException(String message) {
        super(message);
    }
}