package mmclass.abs;

import java.io.Writer;

import generic.XmiReferenceInterface;
import mmclass.enums.AccessSpecifier;

public abstract class CppMemberFunction extends CppFunction implements CppFieldInterface {
	protected String funcCompare = null;
	
	CppField cppField = new CppField();
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		this.cppField.writeToFile(container, depth, fileOut);
		super.writeToFile(container, depth, fileOut);
	}

	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		this.cppField.setReference(container, position, parent);
	}

	@Override
	public String getReference() {
		return this.cppField.getReference();
	}

	@Override
	public void setContainer(CppFieldContainer fieldContainer) {
		this.cppField.setContainer(fieldContainer);
	}

	@Override
	public CppFieldContainer getContainer() {
		return this.cppField.getContainer();
	}

	@Override
	public void setAccessSpecifier(AccessSpecifier accessSpecifier) {
		this.cppField.setAccessSpecifier(accessSpecifier);
	}

	@Override
	public AccessSpecifier getAccessSpecifier() {
		return this.cppField.getAccessSpecifier();
	}

	@Override
	public void setName(String name) {
		this.cppField.setName(name);
	}

	@Override
	public String getName() {
		return this.cppField.getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof String){
			String tmp = (String) obj;
			if(this.funcCompare == null){
				tmp = tmp.substring( tmp.indexOf( "(" ) + 1 );
				tmp = tmp.substring(0, tmp.indexOf(")") );
				return (tmp.split(",").length == this.ownedParameters.size());
			}else{
				int pos = tmp.indexOf("{");
				if(pos > 0)
					tmp = tmp.substring(0, pos);
				return this.funcCompare.equals(tmp);
			}
		}else{
			return super.equals(obj);
		}
	}
	
	protected void setFuncCompare(String ctxMethod){
		if(ctxMethod != null){
			int tmp = ctxMethod.indexOf("{");
			if(tmp > 0)
				this.funcCompare = ctxMethod.substring(0, tmp);
			else
				this.funcCompare = ctxMethod;
		}
	}
}
