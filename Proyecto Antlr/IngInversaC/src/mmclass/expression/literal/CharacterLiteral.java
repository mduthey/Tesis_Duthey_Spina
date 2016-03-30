package mmclass.expression.literal;

import java.io.Writer;

import mmclass.CppModel;
import mmclass.abs.CppExpression;

public class CharacterLiteral extends CppExpression {

	char charValue;
	
	public CharacterLiteral(char charValue){
		this.charValue = charValue;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppCharacterLiteral\"");
		fileOut.write(getTab(depth+1)+"charValue=\""+this.charValue+"\" />");
	}

}
