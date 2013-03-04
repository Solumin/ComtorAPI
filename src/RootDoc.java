import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;
import java.util.List;

public class RootDoc {

	private ArrayList<PackageDoc> packages;
	private ArrayList<ClassDoc> classes;
	private CommonTree tree;

	// Creates a RootDoc from an ANTLR tree.
	public RootDoc(CommonTree root) {
		// The 1st level children are packages.
		List pkgs = root.getChildren();

		packages = new ArrayList<PackageDoc>();
		classes = new ArrayList<ClassDoc>();
		tree = root;

		// Parse the root tree to create the packages
		// Control recuses down through PackageDoc --> ClassDoc
		// and onto the Member docs (Constructor, Method and Field)
		for (int i = 0; i < pkgs.size(); i++) {
			CommonTree pkg = (CommonTree)pkgs.get(i);
			PackageDoc pkgdoc = new PackageDoc(pkg);
			packages.add(pkgdoc);
		}

		// To complete the docs, RootDoc asks PackageDoc to set the
		// rest of the metadata, e.g. ClassDoc's PackageDoc.
		for (PackageDoc pkg : packages) {
			for (ClassDoc cls : pkg.getClasses()) {
				cls.setMemberDocs(pkg);
				classes.add(cls);
			}
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

	public String toStringTree() {
		return tree.toStringTree();
	}
}