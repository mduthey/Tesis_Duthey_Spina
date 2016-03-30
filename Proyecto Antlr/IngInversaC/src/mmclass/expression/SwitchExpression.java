package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppExpression;

public class SwitchExpression extends CppExpression {

	boolean waitDefault = false;
	
	CppExpression expression = null;
	Vector<CppCase> cases = new Vector<CppCase>();
	Vector<CppExpression> defaultBranch = new Vector<CppExpression>();
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppSwitchExpression\">");
		if(this.expression != null)
			this.expression.writeToFile("expression", depth+1, fileOut);
		for(CppCase c: this.cases){
			c.writeToFile("cases", depth+1, fileOut);
		}
		for(CppExpression exp: this.defaultBranch){
			exp.writeToFile("default", depth+1, fileOut);
		}
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void addCase(CppCase acase){
		this.cases.addElement(acase);
		acase.setReference("cases", this.cases.size() - 1, this);
	}	
	
	public CppCase getLastCase(){
		if(this.cases.size() > 0 )
			return this.cases.lastElement();
		return null;
	}
	
	public void setStatement(CppExpression exp){
		if ( this.isWaitDefault() ){
			this.addDefault(exp);
		} else {
			this.cases.lastElement().setStatement(exp);
		}
	}
	
	public void addDefault(CppExpression exp){
		this.defaultBranch.addElement(exp);
		exp.setReference("default", this.defaultBranch.size() - 1, this);
	}
	
	public void setCondition(CppExpression exp){
		this.expression = exp;
	}
	
	public CppExpression getCondition(){
		return this.expression;
	}
	
	public boolean isWaitDefault() {
		return waitDefault;
	}
	
	public void setWaitDefault(boolean waitDefault) {
		this.waitDefault = waitDefault;
	}
	
	public boolean hasCases(){
		return (this.cases.size() > 0);
	}
}
