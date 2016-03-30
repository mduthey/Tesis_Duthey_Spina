package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.abs.CppExpression;

public class CppParenthizedExpression extends CppExpression {

	CppExpression expression;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppParenthizedExpression\">");
		if(this.expression != null) this.expression.writeToFile("expression", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setExpression(CppExpression expression) {
		this.expression = expression;
	}
	
	public CppExpression getExpression() {
		return expression;
	}

}
