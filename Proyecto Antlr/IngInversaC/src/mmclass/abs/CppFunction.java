package mmclass.abs;

import generic.XmiReferenceInterface;

import java.io.IOException;
import java.io.Writer;
import java.util.Vector;

import mmclass.enums.LinkageSpecifier;
import mmclass.expression.CppBlock;
import mmclass.SingleVariableDeclaration;

public abstract class CppFunction implements CppModelElement, XmiReferenceInterface {
	Vector<SingleVariableDeclaration> ownedParameters = new Vector<SingleVariableDeclaration>();
	boolean incompleteParameters = false;
	boolean isVarArg;
	LinkageSpecifier linkage = null;
	boolean isInline;
	CppBlock functionBody = null;
	
	protected String getTab(int depth) {
		String out = "\n";
		for(int i=0; i < depth; i++){
			out += "\t";
		}
		return out;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		if(this.isVarArg != false) fileOut.write(getTab(depth)+"isVarArg=\""+this.isVarArg+"\"");
		if(this.linkage != null) fileOut.write(getTab(depth)+"linkage=\""+this.linkage.toString()+"\"");
		if(this.isInline != false) fileOut.write(getTab(depth)+"isInline=\""+this.isInline+"\"");
		fileOut.write(">");
		for(SingleVariableDeclaration svd: this.ownedParameters){
			svd.writeToFile("ownedParameters", depth, fileOut);
		}
		if(this.functionBody != null){
			this.functionBody.writeToFile("functionBody", depth, fileOut);
		}
	}
	
	public void addParameter(SingleVariableDeclaration svd){
		this.ownedParameters.addElement(svd);
		svd.setReference("ownedParameters", this.ownedParameters.size()-1, this);
	}
	
	public void setFunctionBody(CppBlock functionBody) {
		this.functionBody = functionBody;
		functionBody.setReference("functionBody", -1, this);
	}
	
	public CppBlock getFunctionBody() {
		return functionBody;
	}
	
	public void setInline(boolean isInline) {
		this.isInline = isInline;
	}
	
	public void setLinkage(LinkageSpecifier linkage){
		this.linkage = linkage;
	}
	
	public LinkageSpecifier getLinkage() {
		return linkage;
	}
	
	public boolean hasIncompleteParameters() {
		return incompleteParameters;
	}
	
	public void setIncompleteParameters(boolean incompleteParameters) {
		this.incompleteParameters = incompleteParameters;
	}
	
	public SingleVariableDeclaration getParameter(String type){
		SingleVariableDeclaration out = null;
		
		for(int i=0; out == null && i < this.ownedParameters.size(); i++){
			out = this.ownedParameters.get(i);
			if(out.getName() != null || !out.getType().getTypeName().equals(type)){
				out = null;
			}
		}
		
		return out;
	}
}
