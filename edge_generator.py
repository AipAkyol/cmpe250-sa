#this is a generator for edges that will be used in some grid graph
#format is:
#x1-x2,x2-y2 {edge weight as floats with 6 digits significance between 0 and 99999}
#write the edges to a file and then use the file as input to the graph

import random

size_x = 128
size_y = 128

def generate_edges():
    for i in range(size_x):
        for j in range(size_y):
            if i < size_x - 1:
                yield f'{i}-{j},{i+1}-{j} {random.uniform(0, 99999):.6f}'
            if j < size_y - 1:
                yield f'{i}-{j},{i}-{j+1} {random.uniform(0, 99999):.6f}'

with open('edges.txt', 'w') as f:
    for edge in generate_edges():
        f.write(edge + '\n')


