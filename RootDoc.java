import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;

public class RootDoc {

	private ArrayList<PackageDoc> packages;
	private ArrayList<ClassDoc> classes;

	// Creates a RootDoc from an ANTLR tree.
	public RootDoc(CommonTree root) {

	}

	public ClassDoc getClasses() {
		return null;
	}

	public ClassDoc findClass(String className) {
		return null;
	}

	public PackageDoc getPackages() {
		return null;
	}

	public PackageDoc findPackage(String pkgName) {
		return null;
	}

}