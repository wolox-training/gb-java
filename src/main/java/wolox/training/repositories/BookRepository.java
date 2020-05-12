package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
    
    /**
     * Consulta de libro por isbn, devuelve el primero si hay varios
     * 
     * @param isbn              {@link String} que representa el <b>isbn</b> del libro a buscar
     * @return Optional<Book>   El libro de isbn=<b>isbn</b> si existe.
     */
    Optional<Book> findFirstByIsbn(@NotBlank String isbn);
    
    /**
     * Consulta de libros por publisher, genre y year.
     * En caso de que uno de los parámetros sea null no se tiene en cuenta en la comparación.
     * 
     * @param publisher {@link String} que representa el <b>nombre</b> de la editorial del libro 
     * @param genre     {@link String} que representa el <b>genero</b> del libro
     * @param year      {@link String} que representa el <b>año</b> de publicación del libro
     * @return          La lista de libros que cumple con las condiciones
     */
    @Query("SELECT b FROM Book b WHERE (:publisher is null or b.publisher = :publisher)"
            + " and (:genre is null or b.genre = :genre)"
            + " and (:year is null or b.year = :year)")
    List<Book> findBookByPublisherAndGenreAndYear(@Param("publisher") String publisher, @Param("genre") String genre, @Param("year") String year);

    
    /**
     * Devuelte todos los libros que cumplan con publisher igual a {@code publisher}, genre igual a {@code genre},  
     * year igual a {@code year}, que su autor contenga lo que indica {@code author},  
     * que su title contenga lo que indica {@code title}
     * y que su subtitle contenga lo que indica {@code subtitle}.
     * En caso de que uno de los parámetros sea null no se tiene en cuenta en la comparación.
     *
     * @param author    El {@link String} que indica la cadena a buscar como parte del autor
     * @param title     El {@link String} que indica la cadena a buscar como parte del title
     * @param subtitle  El {@link String} que indica la cadena a buscar como parte del subtitle
     * @param publisher {@link String} que representa el <b>nombre</b> de la editorial del libro 
     * @param genre     {@link String} que representa el <b>genero</b> del libro
     * @param year      {@link String} que representa el <b>año</b> de publicación del libro
     * @return          La lista de libros que cumple con las condiciones
     */
    @Query("SELECT b FROM Book b WHERE (:publisher is null or lower(b.publisher) like lower(concat('%', :publisher,'%')))"
            + " and (:genre is null or lower(b.genre) like lower(concat('%', :genre,'%')))"
            + " and (:year is null or b.year = :year)"
            + " and (:author is null or lower(b.author) like lower(concat('%', :author,'%')))"
            + " and (:title is null or lower(b.title) like lower(concat('%', :title,'%')))"
            + " and (:subtitle is null or lower(b.subtitle) like lower(concat('%', :subtitle,'%')))"
            )
    List<Book> findAllFiltered(@Param("publisher") String publisher, @Param("genre") String genre, @Param("year") String year
                            ,@Param("author") String author, @Param("title") String title, @Param("subtitle") String subtitle);
    
}
