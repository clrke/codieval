input = open('input.txt', 'r')

for name in input:
	print('Hello %s!'%name.rstrip('\n'))
