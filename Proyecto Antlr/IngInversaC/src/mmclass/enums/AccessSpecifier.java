package mmclass.enums;

public enum AccessSpecifier {
	PRIVATE("PRIVATE"), PROTECTED("PROTECTED"), PUBLIC("PUBLIC");
	private String value;
	
	private AccessSpecifier(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}