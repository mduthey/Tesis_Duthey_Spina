package mmclass.expression.literal;

import java.io.Writer;

import mmclass.CppModel;
import mmclass.abs.CppExpression;

public class NumberLiteral extends CppExpression {
	
	int tokenInt;
	float tokenFloat;
	
	boolean isInt = false;
	
	public NumberLiteral(int token){
		this.tokenInt = token;
		this.isInt = true;
	}
	
	public NumberLiteral(float token){
		this.tokenFloat = token;
	}

	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppNumberLiteral\"");
		if(this.isInt)
			fileOut.write(getTab(depth+1)+"token=\""+this.tokenInt+"\" />");
		else
			fileOut.write(getTab(depth+1)+"token=\""+this.tokenFloat+"\" />");
	}

}
