import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;

public class CommentDoc implements Doc {

	private String commentText;

	private int lineNumber;

	private ArrayList<Tag> tags;

	public CommentDoc(String ct, int ln, ArrayList<Tag> tags) {
		commentText = ct;
		lineNumber = ln;
		tags = tags;
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

}