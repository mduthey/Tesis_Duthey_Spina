package mmclass.abs;

import java.io.Writer;

import mmclass.CppModel;
import mmclass.expression.TypeAccess;

public class CppTypedElement implements CppTypedElementInterface {

	TypeAccess type = null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		if(this.type != null)
			this.type.writeToFile(container, depth, fileOut);
	}

	@Override
	public void setType(TypeAccess type) {
		this.type = type;
	}

	@Override
	public TypeAccess getType() {
		return this.type;
	}

}
