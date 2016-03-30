package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.expression.abs.IterationStatement;

public class WhileStatement extends IterationStatement {
	
	public WhileStatement() {
		this.type = 2;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppWhileStatement\">");
		super.writeToFile(container, depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
}
