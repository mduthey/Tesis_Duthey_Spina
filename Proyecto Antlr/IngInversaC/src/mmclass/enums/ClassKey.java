package mmclass.enums;

public enum ClassKey {
	CLASS("CLASS"), STRUCT("STRUCT"), UNION("UNION");
	private String value;
	
	private ClassKey(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
