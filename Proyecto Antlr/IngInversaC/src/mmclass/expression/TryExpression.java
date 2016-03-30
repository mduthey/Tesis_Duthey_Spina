package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppExpression;

public class TryExpression extends CppExpression {
	
	CppExpression theBody = null;
	Vector<CatchClause> catchClause = new Vector<CatchClause>();
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppTryExpression\"");
		fileOut.write(">");
		if(this.theBody != null) this.theBody.writeToFile("theBody", depth+1, fileOut);
		for(CatchClause cc: this.catchClause){
			cc.writeToFile("catchClause", depth+1, fileOut);
		}
		fileOut.write(getTab(depth)+"</"+container+">");
	}

}
