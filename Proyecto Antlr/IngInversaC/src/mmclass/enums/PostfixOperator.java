package mmclass.enums;

public enum PostfixOperator {
	DECREMENT("DECREMENT","--"),
	INCREMENT("INCREMENT", "++");
	private String value;
	private String literal;
	
	private PostfixOperator(String value, String literal){
		this.value = value;
		this.literal = literal;
	}
	
	public String toString(){
		return this.value;
	}
	
	public String getLiteral(){
		return literal;
	}
	
	public static PostfixOperator fromLiteral(String literal){
		PostfixOperator tmp = null;
		PostfixOperator[] operators = PostfixOperator.values();
		for(int i=0; tmp == null && i < operators.length; i++){
			if( operators[i].getLiteral().equals(literal) )
				tmp = operators[i];
		}
		if(tmp == null) tmp = DECREMENT;
		return tmp;
	}
}
