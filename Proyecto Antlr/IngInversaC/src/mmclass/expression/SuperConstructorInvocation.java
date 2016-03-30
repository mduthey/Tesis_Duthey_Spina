package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

public class SuperConstructorInvocation extends MethodInvocation {
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth+1)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppSuperConstructorInvocation\"");
		super.writeToFile(container, depth+1, fileOut);
		if(this.expression != null) 
			this.expression.writeToFile("expression", depth+1, fileOut);
		fileOut.write(getTab(depth+1)+"</"+container+">");		
	}
}
