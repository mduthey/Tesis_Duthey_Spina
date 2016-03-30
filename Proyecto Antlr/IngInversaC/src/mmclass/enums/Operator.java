package mmclass.enums;

public enum Operator {
	GREATER_EQUALS("GREATER_EQUALS", ">="), 
	OR("OR", "||"), 
	MINUS("MINUS", "-"),
	XOR_EQ("XOR_EQ", "^="),
	LESS_EQUALS("LESS_EQUALS", "<="),
	EQUALS("EQUALS", "=="),
	NOT_EQUALS("NOT_EQUALS", "!="),
	AND("AND", "&&"),
	PLUS("PLUS", "+"),
	GREATER_THAN("GREATER_THAN", ">"),
	LESS_THAN("LESS_THAN", "<"),
	TIMES("TIMES", "*"),
	DIVISION("DIVISION", "/"),
	SHIFT_LEFT("SHIFT_LEFT", "<<"),
	SHIFT_RIGHT("SHIFT_RIGHT", ">>"),
	XOR("XOR", "^"),
	BIT_OR("BIT_OR", "|"),
	REMAINDER("REMAINDER", "%"),
	BIT_AND("BIT_AND", "&");
	private String value;
	private String literal;
	
	private Operator(String value, String literal){
		this.value = value;
		this.literal = literal;
	}
	
	public String toString(){
		return this.value;
	}
	
	public String getLiteral(){
		return literal;
	}
		
	public static Operator fromLiteral(String literal){
		Operator tmp = null;
		Operator[] operators = Operator.values();
		for(int i=0; tmp == null && i < operators.length; i++){
			if( operators[i].getLiteral().equals(literal) )
				tmp = operators[i];
		}
		if(tmp == null) tmp = GREATER_EQUALS;
		return tmp;
	}
}
