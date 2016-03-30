package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.abs.CppExpression;
import mmclass.expression.abs.AbstractMethodInvocation;

public class MethodInvocation extends AbstractMethodInvocation {
	CppExpression expression = null;
	
	public void setExpression(CppExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppMethodInvocation\"");
		super.writeToFile(container, depth+1, fileOut);
		if(this.expression != null) 
			this.expression.writeToFile("expression", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");		
	}
}
