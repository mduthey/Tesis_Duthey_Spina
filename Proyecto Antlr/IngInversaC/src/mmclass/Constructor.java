package mmclass;

import java.io.Writer;

import mmclass.abs.CppMemberFunction;

public class Constructor extends CppMemberFunction {
	
	public Constructor(String ctxMethod) {
		this.setFuncCompare(ctxMethod);
	}

	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppConstructor\"");
		super.writeToFile(container, depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
}
