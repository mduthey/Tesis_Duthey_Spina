package mmclass;

import java.io.Writer;

import mmclass.abs.CppMemberFunction;
import mmclass.abs.CppTypedElement;
import mmclass.abs.CppTypedElementInterface;
import mmclass.expression.CppBlock;
import mmclass.expression.TypeAccess;

public class Method extends CppMemberFunction implements CppTypedElementInterface {

	CppTypedElement cppTypedElement = new CppTypedElement();
	
	boolean isFinal;
	boolean isConst;
	boolean isVirtual;
	boolean isPureVirtual;
	
	public Method(String ctxMethod) {
		this.setFuncCompare(ctxMethod);
	}

	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppMethod\"");
		if(this.isFinal != false) fileOut.write(getTab(depth+1)+"isFinal=\""+this.isFinal+"\"");
		if(this.isConst != false) fileOut.write(getTab(depth+1)+"isConst=\""+this.isConst+"\"");
		if(this.isVirtual != false) fileOut.write(getTab(depth+1)+"isVirtual=\""+this.isVirtual+"\"");
		if(this.isPureVirtual != false) fileOut.write(getTab(depth+1)+"isPureVirtual=\""+this.isPureVirtual+"\"");
		super.writeToFile(container, depth+1, fileOut);
		this.cppTypedElement.writeToFile("type", depth+1, fileOut);
		
		fileOut.write(getTab(depth)+"</"+container+">");
	}

	@Override
	public void setFunctionBody(CppBlock functionBody) {
		super.setFunctionBody(functionBody);
		this.setPureVirtual(false);
	}
	
	@Override
	public void setType(TypeAccess type) {
		this.cppTypedElement.setType(type);
		type.setReference("type", -1, this);
	}
	
	@Override
	public TypeAccess getType() {
		return this.cppTypedElement.getType();
	}

	public void setVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual;
		this.setPureVirtual(this.isVirtual);
	}
	
	public void setConst(boolean isConst) {
		this.isConst = isConst;
	}
	
	public void setPureVirtual(boolean isPureVirtual) {
		this.isPureVirtual = isPureVirtual;
	}
	
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
}
