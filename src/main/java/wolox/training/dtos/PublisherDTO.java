package wolox.training.dtos;

public class PublisherDTO {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public PublisherDTO(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PublisherDTO [name=" + name + "]";
	}
	
}
