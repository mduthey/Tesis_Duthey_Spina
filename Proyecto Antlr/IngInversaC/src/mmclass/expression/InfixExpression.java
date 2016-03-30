package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.enums.Operator;
import mmclass.expression.abs.BinaryExpression;

public class InfixExpression extends BinaryExpression {
	
	Operator operator;
	
	public InfixExpression(Operator operator){
		this.setOperator(operator);
	}
	
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppInfixExpression\"");
		fileOut.write(getTab(depth+1)+"operator=\""+this.operator.toString()+"\">");
		super.writeToFile(container, depth, fileOut); // End with name.
		fileOut.write(getTab(depth)+"</"+container+">");
	}

}
