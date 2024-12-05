#this is a generator for nodes that will be used in some grid graph
#format is:
#x y {type of node in int either 0 or 1}
#write the nodes to a file and then use the file as input to the graph
# % of types are determined by the density parameter below

import random

types = [0, 1, 2 , 3, 4]

densities = [ 75, 10,5,5,5]

size_x = 128
size_y = 128

# write the sizes of the grid to the first line of the file
with open('nodes.txt', 'w') as f:
    f.write(f'{size_x} {size_y}\n')

def generate_nodes():
    for i in range(size_x):
        for j in range(size_y):
            node_type = random.choices(types, densities)[0]
            yield f'{i} {j} {node_type}'

with open('nodes.txt', 'a') as f:
    for node in generate_nodes():
        f.write(node + '\n')



