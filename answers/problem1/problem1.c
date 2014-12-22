#include <stdio.h>

void main() {
	char name[20];
	FILE* input = fopen("input.txt", "r");

	while(fscanf(input, "%s", name) != EOF) {
		printf("Hello %s!\n", name);
	}

	fclose(input);
}
