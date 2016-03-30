package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.expression.abs.IterationStatement;

public class DoWhileStatement extends IterationStatement {
	
	public DoWhileStatement() {
		this.type = 1;
	}

	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppDoWhileStatement\">");
		super.writeToFile(container, depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
}
