#include <stdio.h>

void main() {
	char name[20];
	FILE* input = fopen("input.txt", "r");
	FILE* output = fopen("output.txt", "w");

	while(fscanf(input, "%s", name) != EOF) {
		fprintf(output, "Hello %s!\n", name);
	}

	fclose(input);
	fclose(output);
}
