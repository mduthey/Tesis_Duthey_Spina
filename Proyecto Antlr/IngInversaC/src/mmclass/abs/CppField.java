package mmclass.abs;

import java.io.Writer;

import generic.XmiReference;
import generic.XmiReferenceInterface;
import mmclass.enums.AccessSpecifier;

public class CppField extends CppNamedElement implements CppFieldInterface{
	
	CppFieldContainer fieldContainer = null;
	XmiReference xr = new XmiReference();

	AccessSpecifier accessSpecifier = null;
	
	@Override
	public void setContainer(CppFieldContainer fieldContainer) {
		this.fieldContainer = fieldContainer;
	}

	@Override
	public CppFieldContainer getContainer() {
		return this.fieldContainer;
	}

	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		if(accessSpecifier != null){
			fileOut.write(getTab(depth)+"accessSpecifier=\""+this.accessSpecifier.toString()+"\"");
		}
		if(this.name != null){
			fileOut.write(getTab(depth)+"name=\""+this.getName()+"\"");
		}
	}
	
	protected String getTab(int depth) {
		String out = "\n";
		for(int i=0; i < depth; i++){
			out += "\t";
		}
		return out;
	}

	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		xr.setReference(container, position, parent);
	}

	@Override
	public String getReference() {
		return xr.getReference();
	}
	
	@Override
	public void setAccessSpecifier(AccessSpecifier accessSpecifier) {
		this.accessSpecifier = accessSpecifier;
	}
	
	@Override
	public AccessSpecifier getAccessSpecifier() {
		return accessSpecifier;
	}
}