package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.abs.CppExpression;

public class ArrayAccess extends CppExpression {

	CppExpression index = null;
	CppExpression array = null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppArrayAccess\"");
		fileOut.write(">");
		if(this.index != null) this.index.writeToFile("index", depth+1, fileOut);
		if(this.array != null) this.array.writeToFile("array", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setArray(CppExpression array) {
		this.array = array;
	}
	
	public CppExpression getArray() {
		return array;
	}
	
	public void setIndex(CppExpression index) {
		this.index = index;
	}
	
	public CppExpression getIndex() {
		return index;
	}

}
