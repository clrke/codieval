import java.io.*;
import java.util.ArrayList;

public class Problem5 {
	public static int buffer;
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
			PrintWriter pw = new PrintWriter(new FileWriter(new File("output.txt")));

			ArrayList<String> lines = new ArrayList<String>();
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				lines.add(line);
			}
			br.close();

			for(buffer = 0; buffer < lines.size(); buffer++) {
				String line = lines.get(buffer);
				if(tabs(line) == 0) {
					BinaryTree root = new BinaryTree(line.trim(), tabs(line));
					parseTree(lines, root);
					pw.println(root);
				}
			}

			pw.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void parseTree(ArrayList<String> lines, BinaryTree current) {
		buffer++;

		if(buffer >= lines.size())
			return;

		String line = lines.get(buffer);

		int depth = tabs(line);

		if(depth > current.depth) {
			current.left = new BinaryTree(line.trim(), depth);
			parseTree(lines, current.left);

			line = lines.get(buffer);

			current.right = new BinaryTree(line.trim(), depth);
			parseTree(lines, current.right);
		}
	}

	public static int tabs(String str) {
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i) != '\t')
				return i;
		}
		return -1;
	}
}

class BinaryTree {
	public String value;
	public int depth;
	public BinaryTree left;
	public BinaryTree right;

	public BinaryTree(String value, int depth) {
		this.value = value;
		this.depth = depth;
		this.left = null;
		this.right = null;
	}

	@Override
	public String toString() {
		return this.preorder() + "\n" + this.inorder() + "\n" + this.postorder() + "\n";
	}

	public String preorder() {
		return this.value + (this.left!=null?this.left.preorder():"") + (this.right!=null?this.right.preorder():"");
	}

	public String inorder() {
		return (this.left!=null?this.left.inorder():"") + this.value + (this.right!=null?this.right.inorder():"");
	}

	public String postorder() {
		return (this.left!=null?this.left.postorder():"") + (this.right!=null?this.right.postorder():"") + this.value;
	}
}
