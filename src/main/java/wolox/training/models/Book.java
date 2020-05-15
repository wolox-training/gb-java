package wolox.training.models;

import java.util.List;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Data(staticConstructor="of")
@NoArgsConstructor
@ToString(includeFieldNames=true)
public class Book {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
    private long id;
	
	@NotNull
	private String genre; 

	@NotNull
    private String author;
	
	@NotNull
    private String image;
	
	@NotNull
    private String title;
	
	@NotNull
	private String subtitle;

	@NotNull
	private String publisher;
	
	@NotNull
	private String year;
	
	@NotNull
	private int pages = 0;
	
	@NotNull
	private String isbn;
	
	@JsonIgnore
	@ToString.Exclude
	@ManyToMany(mappedBy = "books")
    private List<User> users;

	public Book(@NotNull String genre, @NotNull String author, @NotNull String image, @NotNull String title, @NotNull String subtitle, @NotNull String publisher, @NotNull String year, @NotNull int pages, @NotNull String isbn) {
		this.genre = genre;
		this.author = author;
		this.image = image;
		this.title = title;
		this.subtitle = subtitle;
		this.publisher = publisher;
		this.year = year;
		this.pages = pages;
		this.isbn = isbn;
	}
}
