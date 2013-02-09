import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDoc implements ElementDoc {

	private String className;
	private int lineNumber;

	private ArrayList<String> modifiers;
	private PackageDoc pkgDoc;
	private CommentDoc comment;

	private ArrayList<String> imports;
	private ArrayList<MethodDoc> methods;
	private ArrayList<ConstructorDoc> constructors;
	private ArrayList<FieldDoc> fields;

	private String superClass;
	private String interfaces;

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

		superClass = "";
		interfaces = "";

		List memTrees = root.getChildren();

		for (int i = 1; i < memTrees.size(); i++) {
			CommonTree mem = (CommonTree)memTrees.get(i);
			switch (mem.getText()) {
				case "ACCESS_MODIFER":
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
					String inType = mem.getChild(0).getText();
					interfaces += (interfaces.isEmpty()) ? inType : ", "+inType;
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
					for (int j = 0; j < imps.size(); j++) {
						List impList = ((CommonTree)imps.get(j)).getChildren();
						String impStatement = "";
						for (int k = 0; k < impList.size(); k++) {
							CommonTree imp = (CommonTree) impList.get(k);
							impStatement += imp.getText();
						}
						imports.add(impStatement);
					}
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

	public String toString() {
		return getName();
	}
}