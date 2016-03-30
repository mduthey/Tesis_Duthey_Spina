package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.expression.abs.JumpStatement;

public class GotoStatement extends JumpStatement {
	
	LabeledStatement label = null;
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppGotoStatement\"");
		if(this.label != null) fileOut.write(getTab(depth+1)+"label=\""+this.label.getReference()+"\"");
		fileOut.write(">");
		fileOut.write(getTab(depth)+"</"+container+">");
	}

}
