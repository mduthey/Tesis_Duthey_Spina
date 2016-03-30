package mmclass.abs;

import java.util.Vector;

import mmclass.CppClass;
import mmclass.CppPackage;

public abstract class CppPathReferentiable extends CppNamedElement {
	CppPackage parentReference = null; // opposite: childrenReferences
	Vector<CppPathReference> referencedIn = new Vector<CppPathReference>(); // opposite:referencedTerminal  readonly
	
	public abstract CppClass getClassByName(String name);
}