package gui;

class CompilationErrorException extends Exception {
	private String message;
	public CompilationErrorException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
