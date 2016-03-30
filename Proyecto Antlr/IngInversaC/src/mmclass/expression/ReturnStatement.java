package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.abs.CppExpression;
import mmclass.expression.abs.JumpStatement;

public class ReturnStatement extends JumpStatement {

	CppExpression returnExpression = null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppReturnStatement\">");
		if(this.returnExpression != null) this.returnExpression.writeToFile("returnExpression", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setReturnExpression(CppExpression returnExpression) {
		this.returnExpression = returnExpression;
		returnExpression.setReference("returnExpression", -1, this);
	}

}
