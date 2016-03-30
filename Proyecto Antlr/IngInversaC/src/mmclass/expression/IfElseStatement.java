package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.abs.CppExpression;
import mmclass.expression.abs.SelectionStatement;

public class IfElseStatement extends SelectionStatement {
	
	CppExpression elseStatement = null;
	boolean inLine;
	boolean elseBranch = false;
	
	public void setInLine(boolean inLine) {
		this.inLine = inLine;
	}
	
	public void setElseStatement(CppExpression elseStatement) {
		this.elseStatement = elseStatement;
		elseStatement.setReference("elseStatement", -1, this);
	}
	
	public CppExpression getElseStatement() {
		return elseStatement;
	}
	
	@Override
	public void setStatement(CppExpression statement) {
		if(!this.isElseBranch())
			super.setStatement(statement);
		else
			this.setElseStatement(statement);
	}
	
	@Override
	public CppExpression getStatement() {
		if(!this.isElseBranch())
			return super.getStatement();
		else
			return this.getElseStatement();
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppIfElseStatement\"");
		if(this.inLine != false) fileOut.write(getTab(depth+1)+"inLine=\""+this.inLine+"\"");
		fileOut.write(">\r\n");
		super.writeToFile(container, depth+1,fileOut);
		if(this.elseStatement != null) this.elseStatement.writeToFile("elseStatement", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setElseBranch(boolean elseBranch) {
		this.elseBranch = elseBranch;
	}
	
	public boolean isElseBranch() {
		return elseBranch;
	}
}
