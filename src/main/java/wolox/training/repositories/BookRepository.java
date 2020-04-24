package wolox.training.repositories;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import wolox.training.models.Book;


public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
    
    //Book findByAuthor(@NotBlank String author);
    //Book findFirstByAuthor(@NotBlank String author);
    Book findFirstByAuthorOrderByYearDesc(@NotBlank String author);
    //Book findByAuthorAndSort(@NotBlank String author, Sort sort);    
    //Book findByAuthorOrderByYearDesc(@NotBlank String author);
    //Optional<Book> findByAuthorOrderByYearDesc(@NotBlank String author);
}
