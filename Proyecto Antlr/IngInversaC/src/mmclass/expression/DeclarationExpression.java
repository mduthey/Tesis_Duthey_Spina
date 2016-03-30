package mmclass.expression;
import mmclass.CppModel;

import generic.XmiReferenceInterface;

import java.io.Writer;
import java.util.Vector;

import mmclass.VariableDeclarationGroup;
import mmclass.abs.CppExpression;

public class DeclarationExpression extends CppExpression {

	Vector<VariableDeclarationGroup> groups = new Vector<VariableDeclarationGroup>();
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppDeclarationExpression\"");
		fileOut.write(">");
		for(VariableDeclarationGroup group: this.groups){
			group.writeToFile("groups", depth+1, fileOut);
		}
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void addGroup(VariableDeclarationGroup group){
		this.groups.addElement(group);
		group.setReference("groups", this.groups.size()-1, this);
		group.setContainer(this);
	}

}
