package wolox.training.dtos;

public class AuthorDTO {

	private String url;
	private String name;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	
	public AuthorDTO(String url, String name) {
		super();
		this.url = url;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "AuthorDTO [url=" + url + ", name=" + name + "]";
	}	
}
