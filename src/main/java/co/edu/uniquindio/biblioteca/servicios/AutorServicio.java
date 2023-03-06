package co.edu.uniquindio.biblioteca.servicios;

import co.edu.uniquindio.biblioteca.dto.AutorGet;
import co.edu.uniquindio.biblioteca.dto.AutorPost;
import co.edu.uniquindio.biblioteca.dto.ClienteGet;
import co.edu.uniquindio.biblioteca.dto.ClientePost;
import co.edu.uniquindio.biblioteca.entity.Autor;
import co.edu.uniquindio.biblioteca.entity.Cliente;
import co.edu.uniquindio.biblioteca.repo.AutorRepo;
import co.edu.uniquindio.biblioteca.servicios.excepciones.AutorNoEncontradoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AutorServicio {

    private  final AutorRepo autorRepo;

    public AutorGet save(AutorPost autor){
        return convertir( autorRepo.save( convertir(autor) ) );
    }


    public AutorGet findById (Long idAutor){
        Autor autor = obtenerAutor(idAutor);
        return convertir(autor);
    }

    public void delete(long idAutor){
        obtenerAutor(idAutor);
        autorRepo.deleteById(idAutor);
    }

    public AutorGet update(long  idAutor, AutorPost nuevoAutor){
        obtenerAutor(idAutor);

        Autor nuevo = convertir(nuevoAutor);
        nuevo.setId(idAutor);
        return convertir( autorRepo.save(nuevo));
    }

    public List<AutorGet> findAll(){
        return autorRepo.findAll()
                .stream()
                .map(c -> convertir(c))
                .collect(Collectors.toList());
    }

    private Autor obtenerAutor(Long idAutor){

        return autorRepo.findById(idAutor).orElseThrow( () -> new AutorNoEncontradoException("El Autor no existe") );
    }

    private AutorGet convertir(Autor autor){
        return new AutorGet(autor.getId(), autor.getNombre());
    }

    private Autor convertir(AutorPost autor){

        return Autor.builder()
                .nombre(autor.nombre()).build();
    }

}
