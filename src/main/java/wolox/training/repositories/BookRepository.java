package wolox.training.repositories;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import wolox.training.models.Book;


public interface BookRepository extends CrudRepository<Book, Long> {
    
    
    //Comentarios adrede para mantener conocimientos y ayuda memoria de la capacitacion 
    //Book findByAuthor(@NotBlank String author);
    //Book findFirstByAuthor(@NotBlank String author);    
    //Book findByAuthorAndSort(@NotBlank String author, Sort sort);    
    //Book findByAuthorOrderByYearDesc(@NotBlank String author);
    //Book findFirstByAuthorOrderByYearDesc(@NotBlank String author);    
    
	//consulta de primer libro de un autor ordenado por año asc
	Optional<Book> findFirstByAuthorOrderByYear(@NotBlank String author);
	//consulta de ultimo libro de un autor ordenado por año desc
	Optional<Book> findFirstByAuthorOrderByYearDesc(@NotBlank String author);
    //Listado de todos los libro de un autor ordenados por año ascendente
    List<Book> findByAuthorOrderByYear(@NotBlank String author);
    //Consulta de libro por titulo
    Optional<Book> findByTitle(@NotBlank String title);
}
