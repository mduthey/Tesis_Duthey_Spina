package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.SingleVariableDeclaration;
import mmclass.abs.CppExpression;

public class CatchClause extends CppExpression {

	CppExpression theBody = null;
	SingleVariableDeclaration exception = null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppCatchClause\"");
		fileOut.write(">");
		if(this.theBody != null) this.theBody.writeToFile("theBody", depth+1, fileOut);
		if(this.exception != null) this.exception.writeToFile("exception", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}

}
