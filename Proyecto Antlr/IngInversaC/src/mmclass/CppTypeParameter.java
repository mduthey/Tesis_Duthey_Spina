package mmclass;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppType;
import mmclass.expression.CatchClause;
import mmclass.expression.TypeAccess;

public class CppTypeParameter extends CppType {
	Vector<TypeAccess> bounds = new Vector<TypeAccess>();
	
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppTypeParameter\"");
		fileOut.write(">");
		for(TypeAccess ta: this.bounds){
			ta.writeToFile("bounds", depth+1, fileOut);
		}
		fileOut.write(getTab(depth)+"</"+container+">");
	}
}
