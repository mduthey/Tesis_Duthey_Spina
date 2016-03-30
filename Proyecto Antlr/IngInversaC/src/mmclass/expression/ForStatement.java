package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.abs.CppExpression;
import mmclass.expression.abs.IterationStatement;

public class ForStatement extends IterationStatement {
	
	public ForStatement() {
		this.type = 3;
	}
	
	CppExpression initializer = null;
	CppExpression updater = null;
	
	public void setInitializer(CppExpression initializer) {
		this.initializer = initializer;
		this.initializer.setReference("initializer", -1, this);
	}
	
	public void setUpdater(CppExpression updater) {
		this.updater = updater;
	}
	
	public CppExpression getInitializer() {
		return initializer;
	}
	
	public CppExpression getUpdater() {
		return updater;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppForStatement\">");
		if(this.initializer != null) this.initializer.writeToFile("initializer", depth+1, fileOut);
		if(this.updater != null) this.updater.writeToFile("updater", depth+1, fileOut);
		super.writeToFile(container, depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
}
