import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;
import java.util.List;
//Arbitrary comment!
public class ClassDoc implements ElementDoc {

	private String className;
	private int lineNumber;

	private PackageDoc pkgDoc;
	private CommentDoc comment;

	private ArrayList<String> modifiers;
	private String superClass;
	private ArrayList<String> interfaces;
	private ArrayList<String> imports;

	private ArrayList<MethodDoc> methods;
	private ArrayList<ConstructorDoc> constructors;
	private ArrayList<FieldDoc> fields;

	// Creates a basic "null" class with only really basic info.
	public ClassDoc(String name, int ln) {
		this.className = name;
		this.lineNumber = ln;
	}

	// Parses an ANTLR tree to create the class documentation
	public ClassDoc(CommonTree root) {
		className = root.getChild(0).getText();
		lineNumber = root.getLine();

		modifiers = new ArrayList<String>();
		imports = new ArrayList<String>();
		methods = new ArrayList<MethodDoc>();
		constructors = new ArrayList<ConstructorDoc>();
		fields = new ArrayList<FieldDoc>();

		interfaces = new ArrayList<String>();

		List memTrees = root.getChildren();

		for (int i = 1; i < memTrees.size(); i++) {
			CommonTree mem = (CommonTree)memTrees.get(i);
			switch (mem.getText()) {
				case "ACCESS_MODIFIER":
					List accMods = mem.getChildren();
					for (int j = 0; j < accMods.size(); j++) {
						CommonTree acc = (CommonTree)accMods.get(j);
						modifiers.add(acc.getText());
					}
					break;
				case "TYPE_PARAMS":
					className += "<";
					List typeParams = mem.getChildren();
					for (int j = 0; j < typeParams.size(); j++) {
						CommonTree type = (CommonTree)typeParams.get(j);
						className += type.getText() + ", ";
					}
					className = className.substring(0, className.length()-2) + ">";
					break;
				case "extends":
					superClass = mem.getChild(0).getText();
					break;
				case "implements":
					List inters = mem.getChildren();
					for (int j = 0; j < inters.size(); j++) {
						CommonTree inter = (CommonTree)inters.get(j);
						interfaces.add(inter.getText());
					}
					break;
				case "VAR_DEF":
					fields.add(new FieldDoc(mem));
					break;
				case "CONSTRUCTOR":
					constructors.add(new ConstructorDoc(mem));
					break;
				case "METHOD_DEC":
					methods.add(new MethodDoc(mem));
					break;
				case "IMPORTS":
					List imps = mem.getChildren();
					if (imps == null) {
						break;
					}
					for (int j = 0; j < imps.size(); j++) {
						List impList = ((CommonTree)imps.get(j)).getChildren();
						String impStatement = "";
						for (int k = 0; k < impList.size(); k++) {
							CommonTree imp = (CommonTree) impList.get(k);
							impStatement += imp.getText();
						}
						imports.add(impStatement);
					}
					break;
				case "COMMENT_STATEMENT":
					comment = new CommentDoc(mem);
					break;
			} // switch
		} // member loop
	}

	public String getName() {
		return className;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public ArrayList<String> getModifiers() {
		return modifiers;
	}

	public PackageDoc getPackageDoc() {
		return pkgDoc;
	}

	public void setPackageDoc(PackageDoc pkg) {
		pkgDoc = pkg;
	}

	public void setMemberDocs(PackageDoc pkg) {
		pkgDoc = pkg;
		for (MemberDoc mem : getMembers()) {
			mem.setClassDoc(this);
			mem.setPackageDoc(pkg);
		}
	}

	public CommentDoc getComment() {
		return comment;
	}

	public ArrayList<String> getImports() {
		return imports;
	}

	public String getSuperClass() {
		return superClass;
	}

	public ArrayList<String> getInterfaces() {
		return interfaces;
	}

	public ArrayList<MemberDoc> getMembers() {
		ArrayList<MemberDoc> members = new ArrayList<MemberDoc>(methods);
		members.addAll(constructors);
		members.addAll(fields);
		return members;
	}

	public ArrayList<ConstructorDoc> getConstructors() {
		return constructors;
	}

	public ArrayList<MethodDoc> getMethods() {
		return methods;
	}

	public ArrayList<FieldDoc> getFields() {
		return fields;
	}

	public Boolean hasComment() {
		return comment != null;
	}

	public String toString() {
		return getName();
	}
}