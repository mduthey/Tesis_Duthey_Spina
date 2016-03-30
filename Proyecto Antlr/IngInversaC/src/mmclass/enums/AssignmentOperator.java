package mmclass.enums;

public enum AssignmentOperator {
	ASSIGN("ASSIGN","="),
	TIMES_ASSIGN("TIMES_ASSIGN", "*="),
	DIVISSION_ASSIGN("DIVISSION_ASSIGN", "/="),
	MODULO_ASSIGN("MODULO_ASSIGN", "%="),
	PLUS_ASSIGN("PLUS_ASSIGN","+="),
	MINUS_ASSIGN("MINUS_ASSIGN","-="),
	SHIFT_LEFT_ASSIGN("SHIFT_LEFT_ASSIGN","<<="),
	SHIFT_RIGHT_ASSIGN("SHIFT_RIGHT_ASSIGN",">>="),
	AND_ASSIGN("AND_ASSIGN","&="),
	XOR_ASSIGN("XOR_ASSIGN","^="),
	OR_ASSIGN("OR_ASSIGN","|=");
	private String value;
	private String literal;
	
	private AssignmentOperator(String value, String literal){
		this.value = value;
		this.literal = literal;
	}
	
	public String toString(){
		return this.value;
	}
	
	public String getLiteral(){
		return literal;
	}
	
	public static AssignmentOperator fromLiteral(String literal){
		AssignmentOperator tmp = null;
		AssignmentOperator[] operators = AssignmentOperator.values();
		for(int i=0; tmp == null && i < operators.length; i++){
			if( operators[i].getLiteral().equals(literal) )
				tmp = operators[i];
		}
		if(tmp == null) tmp = ASSIGN;
		return tmp;
	}
}
