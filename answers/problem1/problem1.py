input = open('input.txt', 'r')
output = open('output.txt', 'w')

for line in input.readlines():
    output.write('Hello %s!\n'%line[:-1])
