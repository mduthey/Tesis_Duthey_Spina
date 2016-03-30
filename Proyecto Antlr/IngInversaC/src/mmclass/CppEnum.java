package mmclass;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppType;

public class CppEnum extends CppType {
	
	public String getName() {
		String out = super.getName();
		out = (out == null) ? "" : out;
		return out;
	}
	
	Vector<CppEnumConstructor> literals = new Vector<CppEnumConstructor>();
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppEnum\"");
		fileOut.write(getTab(depth+1)+"name=\""+this.getName()+"\"");
		fileOut.write(">\r\n");
		for(CppEnumConstructor cec: this.literals){
			cec.writeToFile("literals", depth+1, fileOut);
		}
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void addLiterals(CppEnumConstructor literal){
		this.literals.addElement(literal);
		literal.setReference("literals", this.literals.size()-1, this);
	}
}
