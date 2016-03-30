package mmclass.enums;

public enum StorageType {
	AUTO("AUTO"), REGISTER("REGISTER"), STATIC("STATIC"), EXTERN("EXTERN"), TYPEDEF("TYPEDEF"), THREAD_LOCAL("THREAD_LOCAL"), MUTABLE("MUTABLE");
	private String value;
	
	private StorageType(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
	
	public static StorageType fromLiteral(String literal){
		StorageType tmp = null;
		StorageType[] operators = StorageType.values();
		for(int i=0; tmp == null && i < operators.length; i++){
			if( operators[i].toString().equalsIgnoreCase(literal) )
				tmp = operators[i];
		}
		if(tmp == null) tmp = AUTO;
		return tmp;
	}
}
