package generic;

import java.io.Writer;

import mmclass.CppModel;

public interface Class2XMI {
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception;
}
