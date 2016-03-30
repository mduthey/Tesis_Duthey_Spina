package mmclass.abs;

import mmclass.expression.TypeAccess;

public interface CppTypedElementInterface extends CppModelElement{
	public void setType(TypeAccess type);
	public TypeAccess getType();
}
