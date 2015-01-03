file = open('input.txt', 'r')

for name in file:
	print('Hello %s!'%name.replace("\n", "").replace("\r", ""))
