package mmclass.expression.literal;

import java.io.Writer;

import mmclass.CppModel;
import mmclass.abs.CppExpression;

public class BooleanLiteral extends CppExpression {

	boolean booleanValue;
	
	public BooleanLiteral(boolean value){
		this.booleanValue = value;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppBooleanLiteral\"");
		fileOut.write(getTab(depth+1)+"booleanValue=\""+this.booleanValue+"\" />");
	}

}
