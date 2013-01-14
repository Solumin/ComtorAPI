import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;

public class PackageDoc implements Doc {

	public ArrayList<ClassDoc> classes;
	public String name;
	public int lineNumber;

	public String getName() {
		return name;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public ClassDoc getClasses() {
		return null;
	}

	public ClassDoc findClass(String className) {
		return null;
	}

}