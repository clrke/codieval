input = open('input.txt', 'r')
output = open('output.txt', 'w')

for name in input:
	output.write('Hello %s!'%name.rstrip('\n'))
	output.write('\n')
