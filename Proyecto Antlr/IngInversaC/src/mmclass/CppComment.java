package mmclass;

import java.io.Writer;

import mmclass.abs.CppModelElement;

public class CppComment implements CppModelElement {

	boolean singleLine = false;
	boolean multiLine = false;
	String content = null;
	
	protected String getTab(int depth) {
		String out = "\n";
		for(int i=0; i < depth; i++){
			out += "\t";
		}
		return out;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppComment\"");
		if(this.singleLine != false) fileOut.write(getTab(depth+1)+"singleLine=\""+this.singleLine+"\"");
		if(this.multiLine != false) fileOut.write(getTab(depth+1)+"multiLine=\""+this.multiLine+"\"");
		if(this.content != null) fileOut.write(getTab(depth+1)+"content=\""+this.content+"\"");
		fileOut.write(">");
		fileOut.write(getTab(depth)+"</"+container+">");
	}

}
