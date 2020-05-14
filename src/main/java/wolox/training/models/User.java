package wolox.training.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import wolox.training.exceptions.BookAlreadyOwnedException;

@Entity
@Data(staticConstructor="of")
@NoArgsConstructor
@ToString(includeFieldNames=true)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private long id;

	@NotNull
	@Size(min = 6, message="Debe ser de al menos 6 caracteres de longitud")
	private String userName;
	
	@NotNull
	private String name;
	
	@NotNull
	@Past(message = "No puede ser una fecha futura!")
	private LocalDate birthDate;
	
	@ManyToMany(cascade={CascadeType.REFRESH, CascadeType.MERGE})
	@JoinTable(name = "USER_BOOKS",
	joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name = "bookId", referencedColumnName = "id"))
	//This means @Data will not produce a getter for this field so have to explicitly define it
	@Getter(AccessLevel.NONE)
	private List<Book> books = new ArrayList<Book>();

	public List<Book> getBooks() {
		return (List<Book>) Collections.unmodifiableList(books);
	}

	public User(@NotNull @Size(min = 6, message = "Debe ser de al menos 6 caracteres de longitud") String userName, @NotNull String name, @NotNull @Past(message = "No puede ser una fecha futura!") LocalDate birthDate) {
		this.userName = userName;
		this.name = name;
		this.birthDate = birthDate;
	}

	public List<Book> addBook(Book userBook) throws BookAlreadyOwnedException {
		if (books.contains(userBook)) {
			throw new BookAlreadyOwnedException();
		} else {
			this.books.add(userBook);
		}
		return (List<Book>) Collections.unmodifiableList(books);
	}
	
	public void removeBook(Book userBook) {
		this.books.remove(userBook);
	}
	
}
