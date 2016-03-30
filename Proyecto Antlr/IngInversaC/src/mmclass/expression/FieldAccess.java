package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.abs.CppExpression;

public class FieldAccess extends CppExpression {
	VariableAccess field = null;
	CppExpression expression = null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppFieldAccess\"");
		fileOut.write(">");
		if(this.field != null) this.field.writeToFile("field", depth+1, fileOut);
		if(this.expression != null) this.expression.writeToFile("expression", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setField(VariableAccess field) {
		this.field = field;
	}
	
	public void setExpression(CppExpression expression) {
		this.expression = expression;
	}

}
