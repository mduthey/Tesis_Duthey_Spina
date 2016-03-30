package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.enums.UnaryOperator;
import mmclass.expression.abs.UnaryExpression;

public class PrefixExpression extends UnaryExpression {

	UnaryOperator operator = null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppPrefixExpression\"");
		if(this.operator != null) fileOut.write(getTab(depth+1)+"operator=\""+this.operator.toString()+"\"");
		fileOut.write(">");
		super.writeToFile(container, depth, fileOut);
		
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setOperator(UnaryOperator op){
		this.operator = op;
	}
	
}
