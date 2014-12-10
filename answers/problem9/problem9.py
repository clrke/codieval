def stroke_count(move):
	count = 0
	current_node = 0

	for connection in move:
		if connection[0] != current_node:
			count += 1
		current_node = connection[1]

	return count

def possible_moves(existing, incoming, minimum_strokes):
	moves = []

	if len(incoming) is 0:
		moves.append(existing)

	for i in range(len(incoming)):
		new_existing = list(existing)
		new_incoming = list(incoming)
		new_existing.append(new_incoming.pop(i))

		if stroke_count(new_existing) < minimum_strokes:
			for move in possible_moves(new_existing, new_incoming, minimum_strokes):
				moves.append(move)
				if stroke_count(move) < minimum_strokes:
					minimum_strokes = stroke_count(move)

		new_existing = list(existing)
		new_incoming = list(incoming)
		new_existing.append(new_incoming.pop(i)[::-1])

		if stroke_count(new_existing) < minimum_strokes:
			for move in possible_moves(new_existing, new_incoming, minimum_strokes):
				moves.append(move)
				if stroke_count(move) < minimum_strokes:
					minimum_strokes = stroke_count(move)

	return moves

input = open('input.txt', 'r')
output = open('output.txt', 'w')

line = input.readline()

while line is not '':
	line = line.rstrip('\n')
	split = line.split(' ')

	nodes_count, connections_count = int(split[0]), int(split[1])

	connections = []
	for i in range(connections_count):
		connections.append([int(point) for point in input.readline().rstrip('\n').split(' ')])

	output.write(str(min([stroke_count(move) for move in possible_moves([], connections, 9999)])))
	output.write('\n')

	line = input.readline()
