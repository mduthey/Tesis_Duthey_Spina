package mmclass.enums;

public enum LinkageSpecifier {
	STATIC("STATIC"), EXTERN("EXTERN");
	private String value;
	
	private LinkageSpecifier(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
