public class Tag {

	public String name;
	public int lineNumber;
	public String text;

	public Tag(String name, int ln, String text) {
		this.name = name;
		this.lineNumber = ln;
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String toString() {
		return null;
	}

}