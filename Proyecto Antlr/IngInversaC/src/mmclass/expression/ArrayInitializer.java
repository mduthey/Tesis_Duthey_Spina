package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppExpression;

public class ArrayInitializer extends CppExpression {

	Vector<CppExpression> expressions = new Vector<CppExpression>();
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppArrayInitializer\"");
		fileOut.write(">");
		for(CppExpression exp: this.expressions){
			exp.writeToFile("expressions", depth+1, fileOut);
		}
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void addExpression(CppExpression exp){
		this.expressions.addElement(exp);
	}

}
