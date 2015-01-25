package codieval.complexity;

import java.util.LinkedList;

import java.util.regex.*;

public class BigO {

	private LinkedList<Integer> exponents;

	public BigO() {
		this.exponents = new LinkedList<Integer>();
	}

	public BigO(String bigO) {
		this.exponents = new LinkedList<Integer>();

		try {
			Pattern pattern = Pattern.compile("O\\((.*)\\)");
			Matcher matcher = pattern.matcher(bigO);

			matcher.find();

			for (String parameter : matcher.group(1).split("\\+")) {
				String[] parameterSplit = parameter.split("\\^");
				if(parameterSplit.length > 1) {
					this.exponents.add(Integer.parseInt(parameterSplit[1]));
				} else {
					this.exponents.add(1);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void add(int exponent) {
		this.exponents.add(exponent);
	}

	public int getValue(int n) {
		int value = 0;

		for(int i = 0; i < this.exponents.size(); i++) {
			value += (int)Math.pow(n, this.exponents.get(i));
		}

		return value > 0? value: 1;
	}

	@Override
	public String toString() {
		String toString = "O(";
		if (this.exponents.size() > 0) {
			toString += "n^"+this.exponents.get(0);
			for (int i = 1; i < this.exponents.size(); i++) {
				toString += "+n^"+this.exponents.get(i);
			}
		} else {
			toString += "1";
		}

		return toString + ")";
	}
}
