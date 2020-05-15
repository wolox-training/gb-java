package wolox.training.controllers;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import wolox.training.models.Book;
import wolox.training.services.OpenLibraryService;


@SpringBootTest
@ContextConfiguration(classes = {OpenLibraryService.class})
public class BookTest {

    @Autowired
    OpenLibraryService openLibraryService;

    @Test
    public void WhenGetBookInfo_ThenCorrectBookInfoIsReturned() throws Exception {
        Optional<Book> book = openLibraryService.bookInfo("0330258648");
        assertThat("Douglas Adams".equals(book.get().getAuthor())).isTrue();
    }

}
