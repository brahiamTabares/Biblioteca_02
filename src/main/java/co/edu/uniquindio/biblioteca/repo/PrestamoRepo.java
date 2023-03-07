package co.edu.uniquindio.biblioteca.repo;

import co.edu.uniquindio.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepo extends JpaRepository<Prestamo, Long> {

    @Query("SELECT p FROM Prestamo p WHERE p.codigo = :id AND p.estaActivo = :estaActivo")
    Optional<Prestamo> findByEstaActivo(@Param("id") Long id, @Param("estaActivo") boolean estaActivo);

    @Query("SELECT p FROM Prestamo p WHERE p.estaActivo = :estaActivo")
    List<Prestamo> findByAllPrestamosActivos(@Param("estaActivo") boolean estaActivo);

    @Query("SELECT p FROM Prestamo p WHERE DATE (p.fechaPrestamo) = DATE (:fechaPrestamo)")
    List<Prestamo> findPrestamoFecha(@Param("fechaPrestamo") LocalDateTime fechaPrestamo);

    @Query("SELECT p FROM Prestamo p WHERE DATE (p.fechaPrestamo) = DATE (:fechaDevolucion)")
    List<Prestamo> findPrestamoFechaDevolucion(@Param("fechaDevolucion") LocalDateTime fechaDevolucion);

    @Query("SELECT COUNT(p) FROM Prestamo p JOIN p.libros l WHERE l.isbn = :isbn")
    Long getPrestamoByLibro(@Param("isbn") String isbn);

    @Query("SELECT p FROM Prestamo p WHERE p.cliente.codigo = :idCliente")
    List<Prestamo> getPrestamoByCliente(@Param("idCliente") Long idCliente);
}
