package mmclass;

import java.io.Writer;

import mmclass.abs.CppMemberFunction;

public class Destructor extends CppMemberFunction {
	boolean isVirtual;
	
	public Destructor(String ctxMethod) {
		this.setFuncCompare(ctxMethod);
	}

	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppDestructor\"");
		if(this.isVirtual != false){
			fileOut.write(getTab(depth+1)+"isVirtual=\""+this.isVirtual+"\"");
		}
		super.writeToFile(container, depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual;
	}
}
