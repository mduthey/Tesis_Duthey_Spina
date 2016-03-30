package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.enums.PostfixOperator;
import mmclass.expression.abs.UnaryExpression;

public class PostfixExpression extends UnaryExpression {

	PostfixOperator operator = null;
	
	public void setOperator(String operator) {
		if(operator.equals("++"))
			this.operator = PostfixOperator.INCREMENT;
		else
			this.operator = PostfixOperator.DECREMENT;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppPostfixExpression\"");
		if(this.operator != null) fileOut.write(getTab(depth+1)+"operator=\""+this.operator.toString()+"\"");
		fileOut.write(">");
		super.writeToFile(container, depth, fileOut);
		
		fileOut.write(getTab(depth)+"</"+container+">");
	}

}
