import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDoc implements Doc {

	public ArrayList<ClassDoc> classes;
	public String name;
	public int lineNumber;

	public PackageDoc(CommonTree root) {
		name = root.getText();
		lineNumber = root.getLine();

		List clsTrees = root.getChildren();
		classes = new ArrayList<ClassDoc>();

		for (int i = 0; i < clsTrees.size(); i++) {
			CommonTree clsTree = (CommonTree)clsTrees.get(i);
			if (clsTree.getText().equals("NORMAL_CLASS")) {
				CommonTree imps = (CommonTree)clsTrees.get(i+1);
				if (imps.getText().equals("IMPORTS")) {
					clsTree.addChild(imps);
				}
				ClassDoc clsDoc = new ClassDoc(clsTree);
				classes.add(clsDoc);
			}
		}
	}

	public String getName() {
		return name;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public ArrayList<ClassDoc> getClasses() {
		return classes;
	}

	public ClassDoc findClass(String className) {
		for (ClassDoc c : classes)
			if (c.getName().equals(className)) 
				return c;
		return null;
	}

}