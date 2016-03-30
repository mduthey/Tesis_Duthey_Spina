package mmclass.expression.literal;

import java.io.Writer;

import mmclass.CppModel;
import mmclass.abs.CppExpression;

public class RegexLiteral extends CppExpression {

	String pattern;
	String options;
	
	public RegexLiteral(String pattern, String options){
		this.pattern = pattern;
		this.options = options;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppRegexLiteral\"");
		fileOut.write(getTab(depth+1)+"pattern=\""+this.pattern+"\"");
		fileOut.write(getTab(depth+1)+"options=\""+this.options+"\" />");
	}

}
