package mmclass.abs;

import generic.XmiReference;
import generic.XmiReferenceInterface;

import java.io.Writer;
import java.util.Vector;

public class CppFieldContainer implements CppModelElement, CppFieldContainerInterface {
	XmiReference xr = new XmiReference();
	//property cppFields#fieldContainer : CppField[*] { ordered composes !resolve };
	Vector<CppFieldInterface> cppFields = new Vector<CppFieldInterface>();
	
	public void addField(CppFieldInterface field){
		this.cppFields.addElement(field);
		field.setReference("cppFields", this.cppFields.size()-1, this);
	}
	
	public Vector<CppFieldInterface> getCppFields() {
		return cppFields;
	}

	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {}

	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		this.xr.setReference(container, position, parent);
	}

	@Override
	public String getReference() {
		return this.xr.getReference();
	}
}