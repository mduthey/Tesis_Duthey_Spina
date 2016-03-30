package mmclass.expression.abs;

import java.io.Writer;

import mmclass.abs.CppExpression;

public abstract class IterationStatement extends CppExpression {
	
	/*
	 * DoWhile = 1
	 * While = 2;
	 * For = 3 / 4;
	 * */
	protected int type = 0;
	
	CppExpression theBody = null;
	CppExpression condition = null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		if(this.condition != null) this.condition.writeToFile("condition", depth, fileOut);
		if(this.theBody != null) this.theBody.writeToFile("theBody", depth, fileOut);
	}
	
	public void setTheBody(CppExpression theBody) {
		this.theBody = theBody;
		theBody.setReference("theBody", -1, this);
	}
	
	public CppExpression getTheBody() {
		return theBody;
	}
	
	public void setCondition(CppExpression condition) {
		this.condition = condition;
	}
	
	public CppExpression getCondition() {
		return condition;
	}

	public int getType() {
		return type;
	}
	
}
