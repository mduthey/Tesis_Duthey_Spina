package mmclass.abs;

import generic.XmiReference;
import generic.XmiReferenceInterface;

import java.io.Writer;
import java.util.Vector;

import mmclass.CppClass;
import mmclass.CppTypeParameter;
import mmclass.expression.TypeAccess;

public class CppType extends CppNamedElement implements CppTypeInterface{
	//property usagesInTypeAccess#access : TypeAccess[*];
	//property typeParameters : CppTypeParameter[*] { composes };
	
	Vector<TypeAccess> usagesInTypeAccess = new Vector<TypeAccess>();
	Vector<CppTypeParameter> typeParameters = new Vector<CppTypeParameter>();
	XmiReference xr = new XmiReference();
	
	@Override
	public void addRefTypeAccess(TypeAccess ta) {
		this.usagesInTypeAccess.addElement(ta);
	}

	@Override
	public Vector<TypeAccess> getRefTypeAccess() {
		return this.usagesInTypeAccess;
	}
	
	public String getRefList(){
		String out = "";
		for(TypeAccess t: this.usagesInTypeAccess){
			out += "//"+t.getReference()+" ";
		}
		return out.trim();
	}

	protected String getTab(int depth) {
		String out = "\n";
		for(int i=0; i < depth; i++){
			out += "\t";
		}
		return out;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {}

	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		xr.setReference(container, position, parent);
	}

	@Override
	public String getReference() {
		return xr.getReference();
	}

	@Override
	public CppClass getClassByName(String name) {
		return null;
	}
	
}
