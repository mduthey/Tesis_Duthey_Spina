package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.expression.abs.SelectionStatement;

public class IfStatement extends SelectionStatement {
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppIfStatement\">");
		super.writeToFile(container, depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
}
