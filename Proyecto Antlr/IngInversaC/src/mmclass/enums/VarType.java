package mmclass.enums;

public enum VarType {
	OBJECT("OBJECT"), REFERENCE("REFERENCE"), POINTER("POINTER");
	private String value;
	
	private VarType(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
