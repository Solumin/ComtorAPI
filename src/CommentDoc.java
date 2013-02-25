import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

import java.util.ArrayList;
import java.util.List;

public class CommentDoc implements Doc {

	private String commentText;

	private int lineNumber;

	private ArrayList<Tag> tags;

	public CommentDoc(String ct, int ln, ArrayList<Tag> tags) {
		commentText = ct;
		lineNumber = ln;
		tags = tags;
	}

	public CommentDoc(CommonTree root) {
		lineNumber = root.getLine();
		commentText = root.getChild(0).getText();
		tags = makeTags(commentText);
	}

	private ArrayList<Tag> makeTags(String text) {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		//offset tracks how far down the comment a tag is.
		int offset = 1;
		for (String line : text.split("\n")) {
			if (line.startsWith("@")) {
				int space = line.indexOf(' ');
				tags.add(new Tag(line.substring(1,space), lineNumber+offset, line.substring(space+1)));
			}
			offset++;
		}
		return tags;
	}

	public String getName() {
		return "Comment";
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getCommentText() {
		return commentText;
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public String toString() {
		return commentText;
	}

}