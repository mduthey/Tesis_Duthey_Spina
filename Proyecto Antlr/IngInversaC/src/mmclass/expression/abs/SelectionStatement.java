package mmclass.expression.abs;

import java.io.Writer;

import mmclass.abs.CppExpression;

public abstract class SelectionStatement extends CppExpression {

	CppExpression condition = null;
	CppExpression statement = null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		if(this.condition != null) this.condition.writeToFile("condition", depth, fileOut);
		if(this.statement != null) this.statement.writeToFile("statement", depth, fileOut);
	}
	
	public void setCondition(CppExpression condition) {
		this.condition = condition;
		condition.setReference("condition", -1, this);
	}
	public CppExpression getCondition() {
		return condition;
	}
	public void setStatement(CppExpression statement) {
		this.statement = statement;
		statement.setReference("statement", -1, this);
	}
	public CppExpression getStatement() {
		return statement;
	}

}
