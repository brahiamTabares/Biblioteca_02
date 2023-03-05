package co.edu.uniquindio.biblioteca.controller.excepciones;

public class LibroNoEncontradoException extends RuntimeException{

    public LibroNoEncontradoException(String mensaje){
        super(mensaje);
    }

}
