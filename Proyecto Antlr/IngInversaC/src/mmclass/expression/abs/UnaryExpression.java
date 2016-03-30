package mmclass.expression.abs;

import java.io.Writer;

import mmclass.abs.CppExpression;

public abstract class UnaryExpression extends CppExpression {

	CppExpression expression = null;
	
	public void setExpression(CppExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		if(this.expression != null) this.expression.writeToFile("expression", depth+1, fileOut);
		
	}

}
