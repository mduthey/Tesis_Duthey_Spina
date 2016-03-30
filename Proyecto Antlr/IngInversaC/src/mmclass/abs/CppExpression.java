package mmclass.abs;

import generic.Class2XMI;
import generic.XmiReference;
import generic.XmiReferenceInterface;

public abstract class CppExpression implements Class2XMI, XmiReferenceInterface{
	
	XmiReference xr = new XmiReference();
	
	protected String getTab(int depth) {
		String out = "\n";
		for(int i=0; i < depth; i++){
			out += "\t";
		}
		return out;
	}
	
	@Override
	public String getReference() {
		return this.xr.getReference();
	}
	
	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		this.xr.setReference(container, position, parent);
	}
}
