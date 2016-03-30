package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppExpression;

public class CppCase extends CppExpression {

	Vector<CppExpression> values = new Vector<CppExpression>();
	CppExpression expression = null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppCase\">");
		for(CppExpression v: this.values){
			v.writeToFile("value", depth+1, fileOut);
		}
		if(this.expression != null)
			this.expression.writeToFile("expression", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}

	public void addValues(CppExpression val){
		this.values.addElement(val);
		val.setReference("value", this.values.size()-1, this);
	}
	
	public void setStatement(CppExpression statement) {
		if(this.expression == null)
			this.expression = statement;
		else
			if( this.expression instanceof CppBlock ){
				( (CppBlock)this.expression ).addExpression(statement);
			}else{
				CppBlock tmp = new CppBlock();
				tmp.addExpression(this.expression);
				tmp.addExpression(statement);
				this.expression = tmp;
			}
		this.expression.setReference("expression", -1, this);
	}
	public CppExpression getStatement() {
		return expression;
	}
	
}
