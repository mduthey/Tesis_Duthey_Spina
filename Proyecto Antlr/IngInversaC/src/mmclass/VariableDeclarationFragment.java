package mmclass;

import java.io.Writer;

import mmclass.abs.CppVariableType;
import mmclass.abs.VariableDeclaration;

public class VariableDeclarationFragment extends VariableDeclaration implements CppVariableType {
	VariableDeclarationGroup variablesContainer = null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppVariableDeclarationFragment\"");
		if(this.variablesContainer != null) fileOut.write(getTab(depth+1)+"variablesContainer=\"//"+this.variablesContainer.getReference()+"\"");
		super.writeToFile(container, depth+1, fileOut); // End with name.
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setVariablesContainer(VariableDeclarationGroup variablesContainer) {
		this.variablesContainer = variablesContainer;
	}
	
	@Override
	public String getTypeName(){
		return ((CppClass)this.variablesContainer.getType().getAccess()).getName();
	}
}
