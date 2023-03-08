package co.edu.uniquindio.biblioteca.servicios;

import co.edu.uniquindio.biblioteca.dto.*;
import co.edu.uniquindio.biblioteca.entity.Cliente;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import co.edu.uniquindio.biblioteca.repo.ClienteRepo;
import co.edu.uniquindio.biblioteca.repo.LibroRepo;
import co.edu.uniquindio.biblioteca.repo.PrestamoRepo;
import co.edu.uniquindio.biblioteca.servicios.excepciones.ClienteNoEncontradoException;
import co.edu.uniquindio.biblioteca.servicios.excepciones.LibroNoEncontradoException;
import co.edu.uniquindio.biblioteca.servicios.excepciones.PrestamoNoEncontradoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrestamoServicio {

    private final PrestamoRepo prestamoRepo;
    private final ClienteRepo clienteRepo;

    private final LibroRepo libroRepo;



    public PrestamoDTO save(PrestamoDTO prestamoDTO) {

        long idCliente = prestamoDTO.clienteID();

        Optional<Cliente> consulta = clienteRepo.findById(idCliente);

           validarClienteExistente(prestamoDTO.clienteID());

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(consulta.get());
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setEstaActivo(true);
        List<String> codigosLibros = prestamoDTO.isbnLibros();
        List<Libro> libros = new ArrayList<>();
        for(String isbn:codigosLibros){

            Optional<Libro> libro=libroRepo.findById(isbn);
            if(libro.isEmpty()){
                throw new LibroNoEncontradoException("El libro no existe");

            }
            libros.add(libro.get());
        }
        prestamo.setLibros(libros);
        prestamo.setFechaDevolucion(prestamoDTO.fechaDevolucion());

        return convertirDTO(prestamoRepo.save(prestamo));
    }


    public PrestamoGet findById( long clienteID) {
        boolean prestamoActivo= true;

        return convertir(prestamoRepo.findByEstaActivo(clienteID,prestamoActivo).orElseThrow(() -> new PrestamoNoEncontradoException("No existe")));
    }
    public List<PrestamoDTO> findAll(){
        boolean prestamosActivos= true;
        return listarPrestamo(prestamoRepo.findByAllPrestamosActivos(prestamosActivos));
    }

    public Long obtenerPrestamoLibroIsbn(String isbn){

        return prestamoRepo.obtenerPrestamoLibroIsbn(isbn);
    }


    public void delete(long codigoPrestamo){
        Prestamo prestamo = obtener(codigoPrestamo);
        prestamoRepo.findById(codigoPrestamo).orElseThrow(() -> new PrestamoNoEncontradoException("No existe un prestamo registrado a este cliente"));
        prestamo.setEstaActivo(false);
    }
 public List<PrestamoGet> obtenerPrestamoFecha(LocalDateTime date){
        return modificarLista(prestamoRepo.busquedaPrestamoFecha(date));
    }
    public List<PrestamoGet> modificarLista(List<Prestamo> listaPrestamo) {
        return listaPrestamo.stream().map(this::convertir).toList();
    }
    public PrestamoDTO update(long codigoPrestamo, PrestamoDTO prestamoDTO) {
        validarPrestamoExistencia(codigoPrestamo);
        Prestamo prestamo = validarPrestamo(prestamoDTO,codigoPrestamo);
        return convertirDTO(prestamoRepo.save(prestamo));
    }
  // Metodo para convertir

    public PrestamoGet convertir(Prestamo prestamo) {
        return new PrestamoGet(prestamo.getCodigo(), prestamo.getCliente().getCodigo(), prestamo.getLibros().stream().map(Libro::getIsbn).toList(), prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion());
    }
    public PrestamoDTO convertirDTO(Prestamo prestamo) {
        List<String>  librosIsbn = new ArrayList<>();

        for (Libro libro : prestamo.getLibros()) {
            librosIsbn.add(libro.getIsbn());
        }
        return new PrestamoDTO(prestamo.getCodigo(),librosIsbn,prestamo.getFechaDevolucion());
    }
   // Metodos para validar prestamo y cliente
    public void validarPrestamoExistencia(Long codigo) {
        prestamoRepo.findById(codigo).orElseThrow(() -> new co.edu.uniquindio.biblioteca.controller.excepciones.PrestamoNoEncontradoException("El Prestamo no existe."));
    }

    public  void  validarClienteExistente( long  idcliente ) {
        Optional<Cliente> cliente = clienteRepo.findById(idcliente);

        if ( cliente.isEmpty()) {
            throw  new ClienteNoEncontradoException ("El cliente no existe" );
        }
    }
    public Prestamo  validarPrestamo(PrestamoDTO prestamoDTO, long codigoPrestamo) {
        List<Libro> libros = libroRepo.findAllById(prestamoDTO.isbnLibros());
        Optional<Cliente> cliente = clienteRepo.findById(prestamoDTO.clienteID());

        if (libros.size() != prestamoDTO.isbnLibros().size()) {
            throw new LibroNoEncontradoException("Hay libros que no existen en la base de datos");
        }

        return Prestamo.builder().codigo(codigoPrestamo).cliente(cliente.get()).fechaPrestamo(LocalDateTime.now()).fechaDevolucion(prestamoDTO.fechaDevolucion()).libros(libros).build();
    }


    public Prestamo obtener(Long codigoPrestamo) {
        Prestamo encontrado = prestamoRepo.findById(codigoPrestamo).orElseThrow(() -> new PrestamoNoEncontradoException("El Prestamo no existe."));
        return encontrado;
    }
    public List<PrestamoDTO>obtenerClientesPrestamo(Long clienteId,PrestamoDTO prestamoDTO) {

        clienteRepo.findById(clienteId);
        List<Prestamo> prestamos = prestamoRepo.obtenerPrestamoCliente(clienteId);

        return listarPrestamo(prestamos);
    }
    public List<PrestamoDTO> listarPrestamo(List<Prestamo> listaPrestamo) {
        return listaPrestamo.stream().map(this::convertirDTO).toList();
    }

}
