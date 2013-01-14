import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;

public class RootDoc {

	public ArrayList<PackageDoc> packages;
	public ArrayList<ClassDoc> classes;

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