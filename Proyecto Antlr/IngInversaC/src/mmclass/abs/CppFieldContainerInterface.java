package mmclass.abs;

import generic.Class2XMI;
import generic.XmiReferenceInterface;

import java.util.Vector;

public interface CppFieldContainerInterface extends Class2XMI, XmiReferenceInterface {
	public abstract void addField(CppFieldInterface field);
	public abstract Vector<CppFieldInterface> getCppFields();
}
