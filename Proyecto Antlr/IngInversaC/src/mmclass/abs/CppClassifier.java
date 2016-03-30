package mmclass.abs;

import generic.XmiReferenceInterface;

import java.util.Vector;

public abstract class CppClassifier extends CppType implements CppFieldContainerInterface{
	CppFieldContainer fc = new CppFieldContainer();
	
	@Override
	public void addField(CppFieldInterface field) {
		fc.addField(field);
	}
	@Override
	public Vector<CppFieldInterface> getCppFields() {
		return fc.getCppFields();
	}
	
	@Override
	public String getReference() {
		return fc.getReference();
	}
	
	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		fc.setReference(container, position, parent);
	}
	
}