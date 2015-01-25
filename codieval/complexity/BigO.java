package codieval.complexity;

import java.util.LinkedList;

public class BigO {

	private LinkedList<Integer> exponents;

	public BigO() {
		this.exponents = new LinkedList<Integer>();
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
