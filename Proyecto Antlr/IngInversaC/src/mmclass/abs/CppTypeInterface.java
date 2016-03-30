package mmclass.abs;

import generic.Class2XMI;
import generic.XmiReferenceInterface;

import java.util.Vector;

import mmclass.CppClass;
import mmclass.expression.TypeAccess;

public interface CppTypeInterface extends Class2XMI, XmiReferenceInterface{
	//property usagesInTypeAccess#access : TypeAccess[*];
	//property typeParameters : CppTypeParameter[*] { composes };
	public void addRefTypeAccess(TypeAccess ta);
	public Vector<TypeAccess> getRefTypeAccess();
	
	public CppClass getClassByName(String name);
}
