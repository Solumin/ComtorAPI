import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;
import java.util.List;

public class RootDoc {

	private ArrayList<PackageDoc> packages;
	private ArrayList<ClassDoc> classes;

	// Creates a RootDoc from an ANTLR tree.
	public RootDoc(CommonTree root) {
		List pkgs = root.getChildren();

		packages = new ArrayList<PackageDoc>();
		classes = new ArrayList<ClassDoc>();

		for (int i = 0; i < pkgs.size(); i++) {
			CommonTree pkg = (CommonTree)pkgs.get(i);
			PackageDoc pkgdoc = new PackageDoc(pkg);
			packages.add(pkgdoc);
			classes.addAll(pkgdoc.getClasses());
		}
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

	public ArrayList<PackageDoc> getPackages() {
		return packages;
	}

	public PackageDoc findPackage(String pkgName) {
		for (PackageDoc p : packages)
			if (p.getName().equals(pkgName))
				return p;
		return null;
	}

}