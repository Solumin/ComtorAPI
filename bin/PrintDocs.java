import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

import java.util.ArrayList;

public class PrintDocs extends Doclet {
	public Boolean start(RootDoc rootDoc) {
		//tabLevel tracks how much indentation is needed
		int tabLevel = 0;

		for (PackageDoc pkg : rootDoc.getPackages()) {
			printPackage(pkg, tabLevel);
		}

		return true;
	}

	String tabs(int tabLevel) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tabLevel; i++) {
			sb.append('\t');
		}
		return sb.toString();
	}

	void printPackage(PackageDoc pkg, int tabLevel) {
		String tabstr = tabs(tabLevel);
		ArrayList<ClassDoc> classes = pkg.getClasses();
		System.out.println(String.format("%sPackage %s (%d class%s)", tabstr,
			pkg.getName(), classes.size(), classes.size() == 1 ? "" : "es"));
		for (ClassDoc cls : classes) {
			printClass(cls, tabLevel+1);
		}
	}

	void printClass(ClassDoc cls, int tabLevel) {
		String tabstr = tabs(tabLevel);
		StringBuilder clsStr = new StringBuilder(tabstr);

		for (String mod : cls.getModifiers()) {
			clsStr.append(mod);
			clsStr.append(' ');
		}

		clsStr.append(cls.getName());
		clsStr.append(String.format(" (line %d)", cls.getLineNumber()));

		if (cls.getSuperClass() != null) {
			clsStr.append(String.format("\n%s\tExtends: %s",
				tabstr, cls.getSuperClass()));
		}

		if (!cls.getInterfaces().isEmpty()) {
			clsStr.append('\n');
			clsStr.append(tabstr);
			clsStr.append("\tImplements: ");
			for (String inter : cls.getInterfaces()) {
				clsStr.append(inter);
				clsStr.append(", ");
			}
			clsStr.delete(clsStr.length()-2, clsStr.length());
		}

		if (!cls.getImports().isEmpty()) {
			clsStr.append('\n');
			clsStr.append(tabstr);
			clsStr.append("\tImports: ");
			for (String imp : cls.getImports()) {
				clsStr.append(imp);
				clsStr.append(", ");
			}
			clsStr.delete(clsStr.length()-2, clsStr.length());
		}

		if (cls.getComment() != null) {
			clsStr.append(String.format("\n%s\tHas comment: %b", 
				tabstr, cls.getComment()));
		}

		System.out.println(clsStr);

		System.out.println(tabstr+"\tFields:");
		for (FieldDoc fld : cls.getFields()) {
			printField(fld, tabLevel+2);
		}
		System.out.println(tabstr+"\tConstructors:");
		for (ConstructorDoc con : cls.getConstructors()) {
			printConstructor(con, tabLevel+2);
		}
		System.out.println(tabstr+"\tMethods:");
		for (MethodDoc mem : cls.getMethods()) {
			printMethod(mem, tabLevel+2);
		}		
	}

	void printField(FieldDoc fld, int tabLevel) {
		String tabstr = tabs(tabLevel);
		StringBuilder fldStr = new StringBuilder(tabstr);

		for (String mod : fld.getModifiers()) {
			fldStr.append(mod);
			fldStr.append(' ');
		}

		fldStr.append(fld);
		fldStr.append(String.format(" (line %d)", fld.getLineNumber()));

		if (fld.getValue() != null) {
			fldStr.append(String.format("\n%s\tInitial value: %s",
				tabstr, fld.getValue()));
		}
		if (fld.getComment() != null) {
			fldStr.append(String.format("\n%s\tHas comment: %b", 
				tabstr, fld.getComment()));
		}

		System.out.println(fldStr);
	}

	void printConstructor(ConstructorDoc con, int tabLevel) {
		String tabstr = tabs(tabLevel);
		StringBuilder conStr = new StringBuilder(tabstr);

		for (String mod : con.getModifiers()) {
			conStr.append(mod);
			conStr.append(' ');
		}

		conStr.append(con.getName());

		if (!con.getParams().isEmpty()) {
			conStr.append('(');
			for (String param : con.getParams()) {
				conStr.append(param);
				conStr.append(", ");
			}
			conStr.replace(conStr.length()-2, conStr.length(), ")");
		}

		conStr.append(String.format(" (line %d)", con.getLineNumber()));

		if (con.getComment() != null) {
			conStr.append(String.format("\n%s\tHas comment: %b", 
				tabstr, con.getComment()));
		}

		System.out.println(conStr);
	}

	void printMethod(MethodDoc mem, int tabLevel) {
		String tabstr = tabs(tabLevel);
		StringBuilder memStr = new StringBuilder(tabstr);

		for (String mod : mem.getModifiers()) {
			memStr.append(mod);
			memStr.append(' ');
		}

		memStr.append(mem.getReturnType());
		memStr.append(' ');
		memStr.append(mem.getName());
		
		if (!mem.getParams().isEmpty()) {
			memStr.append('(');
			for (String param : mem.getParams()) {
				memStr.append(param);
				memStr.append(", ");
			}
			memStr.replace(memStr.length()-2, memStr.length(), ")");
		}

		memStr.append(String.format(" (line %d)", mem.getLineNumber()));

		if (!mem.getExceptions().isEmpty()) {
			memStr.append('\n');
			memStr.append(tabstr);
			memStr.append("\tThrows: ");
			for (String ex : mem.getExceptions()) {
				memStr.append(ex);
				memStr.append(", ");
			}
			memStr.delete(memStr.length()-2, memStr.length());
		}

		if (mem.getComment() != null) {
			memStr.append(String.format("\n%s\tComment:\n", tabstr));
			CommentDoc comment = mem.getComment();
			//Quick-and-dirty split the comment into 2 parts
			//pre = the bit before any tags
			String pre = comment.getCommentText().split("\n@\\w+\\b")[0];
			memStr.append(tabstr+"\t\t");
			//Indent the entire comment
			memStr.append(pre.replace("\n","\n"+tabstr+"\t\t"));

			if (!comment.getTags().isEmpty()) {
				memStr.append(String.format("\n%s\tTags:",tabstr));
				for (Tag t : comment.getTags()) {
					memStr.append(String.format("\n%s\t\t%s", tabstr, t));
				}
			}
		}

		System.out.println(memStr);
	}
}