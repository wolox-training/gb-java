package wolox.training.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OpenLibraryBook implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String title;
	private String subtitle;
	private List<PublisherDTO> publishers;
	private String publishDate;
	private int numberOfPages;
	private List<AuthorDTO> authors;
	private CoverDTO cover;
	private List<SubjectDTO> subjects;
	
	public OpenLibraryBook() {		
	}
	
	public OpenLibraryBook(String title, String subtitle, List<PublisherDTO> publishers,
			String publish_date, int number_of_pages, List<AuthorDTO> authors, CoverDTO cover, List<SubjectDTO> subjects) {
		this.title = title;
		this.subtitle = subtitle;
		this.publishers = publishers;
		this.publishDate = publish_date;
		this.numberOfPages = number_of_pages;
		this.authors = authors;
		this.cover = cover;
		this.subjects = subjects;
	}
	public List<AuthorDTO> getAuthors() {
		return authors;
	}
	public CoverDTO getCover() {
		return cover;
	}
	public int getNumberOfPages() {
		return numberOfPages;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public List<PublisherDTO> getPublishers() {
		return publishers;
	}
	public List<SubjectDTO> getSubjects() {
		return subjects;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public String getTitle() {
		return title;
	}
	public void setAuthors(List<AuthorDTO> authors) {
		this.authors = authors;
	}
	public void setCover(CoverDTO cover) {
		this.cover = cover;
	}
	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	
	public void setPublishers(List<PublisherDTO> publishers) {
		this.publishers = publishers;
	}

	public void setSubjects(List<SubjectDTO> subjects) {
		this.subjects = subjects;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return  "', Title: '"+ this.title 
				+ "', Subtitle: '" + this.subtitle				
				+ "', Publish_Date: " + this.publishDate
				+ "', Number_Of_Pages: '" + this.numberOfPages				
				+ "', Publishers: '" + this.publishers
				+ "', Authors: '" + this.authors
				+ "', Cover: '" + this.cover
				+ "', Subjects: '" + this.subjects
				+ "'";
	}

}
