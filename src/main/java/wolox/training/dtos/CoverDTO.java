package wolox.training.dtos;

public class CoverDTO {
	private String small;
	private String large;
	private String medium;	
	
	public CoverDTO() {	    
	}
	
	public CoverDTO(String small, String medium, String large) {
		this.small = small;		
		this.medium = medium;
		this.large = large;
	}	
	public String getSmall() {
		return small;
	}	
	public void setSmall(String small) {
		this.small = small;
	}	
	public String getLarge() {
		return large;
	}
	public void setLarge(String large) {
		this.large = large;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
}
