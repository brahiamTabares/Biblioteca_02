package co.edu.uniquindio.biblioteca.servicios;

import co.edu.uniquindio.biblioteca.dto.ClientePost;
import co.edu.uniquindio.biblioteca.dto.LibroDTO;
import co.edu.uniquindio.biblioteca.dto.PrestamoDTO;
import co.edu.uniquindio.biblioteca.dto.PrestamoPost;
import co.edu.uniquindio.biblioteca.entity.Cliente;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import co.edu.uniquindio.biblioteca.repo.ClienteRepo;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrestamoServicio {

    private final PrestamoRepo prestamoRepo;
    private final ClienteRepo clienteRepo;

    public Prestamo save(PrestamoDTO prestamoDTO) {

        long codigoCliente = prestamoDTO.clienteID();
        Optional<Cliente> consulta = clienteRepo.findById(codigoCliente);

        if (consulta.isEmpty()) {
            throw new ClienteNoEncontradoException("El cliente No Tiene prestamos existentes");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(consulta.get());
        prestamo.setFechaPrestamo(LocalDateTime.now());

        List<String> codigosLibros = prestamoDTO.isbnLibros();
        List<Libro> libros = new ArrayList<>();

        /*Optional<Libro> buscado libroRepo.findById(codigosLibros[0]);

        if(buscado.isEmpty()){
            throw new LibroNoExiste("El libro no existe");
        }

        libros.add( buscado );*/

        //TODO Completar la parte de los libros
        prestamo.setLibros(libros);
        prestamo.setFechaDevolucion(prestamoDTO.fechaDevolucion());

        return prestamoRepo.save(prestamo);
    }


    //TODO Completar

    private PrestamoPost convertir(PrestamoPost prestamo) {
        return new PrestamoPost(prestamo.clienteID(),prestamo.isbnLibros(),prestamo.fechaPrestamo(),prestamo.fechaDevolucion());
    }


    public Prestamo findById( long clienteID) {
        return prestamoRepo.findById(clienteID).orElseThrow(() -> new PrestamoNoEncontradoException("No existe"));
    }
    public List<Prestamo> findAll(){
        return prestamoRepo.findAll();
    }


    public void delete(long clienteID){
        prestamoRepo.findById(clienteID).orElseThrow(() -> new PrestamoNoEncontradoException("No existe un prestamo registrado a este cliente"));
        prestamoRepo.deleteById(clienteID);
    }

    public Prestamo update(PrestamoDTO prestamo){

        Optional<Prestamo>  actualizar = prestamoRepo.findById(prestamo.clienteID());

        if(!actualizar.isPresent() ) {
            throw new PrestamoNoEncontradoException("El prestamo asociado al id" + prestamo.clienteID() + "no existe");
        }

        return prestamoRepo.save( convertir(prestamo));
    }

    private Prestamo convertir(PrestamoDTO prestamo){
        List<Cliente> clientes = clienteRepo.findAllById();
        Prestamo nuevo = Prestamo.builder()
                .cliente(clientes)
                .libros(prestamo.isbnLibros())
                .fechaPrestamo(prestamo.fechaPrestamo())
                .fechaDevolucion(prestamo.fechaDevolucion()).build();
        return  nuevo;
    }
}
