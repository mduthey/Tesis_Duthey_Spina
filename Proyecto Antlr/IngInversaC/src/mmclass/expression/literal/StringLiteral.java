package mmclass.expression.literal;

import mmclass.abs.CppExpression;

import mmclass.CppModel;
import java.io.Writer;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringLiteral extends CppExpression {

	String literalValue;
	
	public StringLiteral(String literalValue){
		this.literalValue = literalValue;
	}
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppStringLiteral\"");
		fileOut.write(getTab(depth+1)+"literalValue=\""+StringEscapeUtils.escapeHtml4(this.literalValue)+"\" />");
	}

}
