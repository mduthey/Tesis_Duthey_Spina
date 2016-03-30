package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.enums.AssignmentOperator;
import mmclass.expression.abs.BinaryExpression;

public class AssignamentStatement extends BinaryExpression {
	AssignmentOperator operator = null;
	
	public AssignamentStatement(AssignmentOperator operator){
		this.operator = operator;
	}
	
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppAssignamentStatement\"");
		if(this.operator != null) fileOut.write(getTab(depth+1)+"operator=\""+this.operator.toString()+"\"");
		fileOut.write(">");
		super.writeToFile(container, depth, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
}
