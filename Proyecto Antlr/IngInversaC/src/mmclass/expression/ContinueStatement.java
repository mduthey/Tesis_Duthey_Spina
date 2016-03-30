package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.expression.abs.JumpStatement;

public class ContinueStatement extends JumpStatement {

	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppContinueStatement\">");
		fileOut.write(getTab(depth)+"</"+container+">");
	}

}