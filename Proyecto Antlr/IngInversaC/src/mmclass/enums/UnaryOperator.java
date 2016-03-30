package mmclass.enums;

public enum UnaryOperator {
	AMPERSAND("AMPERSAND", "&"),
	ASTERISK("ASTERISK", "*"),
	PLUS("PLUS", "+"),
	MINUS("MINUS", "-"),
	BIT_NOT("BIT_NOT", "~"),
	NOT("NOT", "!"),
	INCREMENT("INCREMENT", "++"),
	DECREMENT("DECREMENT","--");
	private String value;
	private String literal;
	
	private UnaryOperator(String value, String literal){
		this.value = value;
		this.literal = literal;
	}
	
	public String toString(){
		return this.value;
	}
	
	public String getLiteral(){
		return literal;
	}
	
	public static UnaryOperator fromLiteral(String literal){
		UnaryOperator tmp = null;
		UnaryOperator[] operators = UnaryOperator.values();
		for(int i=0; tmp == null && i < operators.length; i++){
			if( operators[i].getLiteral().equals(literal) )
				tmp = operators[i];
		}
		if(tmp == null) tmp = AMPERSAND;
		return tmp;
	}
}
