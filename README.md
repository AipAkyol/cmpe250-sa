# Magical Map Pathfinding Project

This project, originally developed as a homework assignment for CMPE 250 students at Bogazici University, implements a pathfinding system in a magical map where visibility and terrain types affect movement. The program finds optimal paths between objectives while considering visibility radius and terrain modifications.

## Project Overview

The program navigates through a grid-based map where:
- Each cell has a terrain type (0: normal, 1: impassable, 2+: special terrain)
- Limited visibility radius affects path planning
- Special terrain types can be modified during navigation
- Multiple objectives must be reached in sequence

## Setup and Requirements

### Prerequisites
- Java Development Kit (JDK)
- Java compiler

### Project Structure
```
/
├── src/                # Source code files
├── test_cases/         # Test cases
│   ├── small/          # Small test scenarios
│   └── large/          # Large test scenarios
└── README.md          # This file
```

## How to Run

1. Compile the Java files:
```bash
javac src/*.java
```

2. Run the program with required input files:
```bash
java src/Project <nodes_file> <edges_file> <objectives_file> <output_file>
```

Arguments:
- `nodes_file`: Contains map dimensions and node types
- `edges_file`: Defines connections between nodes
- `objectives_file`: Lists objectives to reach
- `output_file`: Where the path output will be written

## Input File Formats

### Nodes File
```
<width> <height>
x y type
...
```
- First line: Map dimensions
- Subsequent lines: Node coordinates and terrain type

### Edges File
```
x1-y1,x2-y2 weight
...
```
- Each line defines an edge between two nodes with its weight

### Objectives File
```
<visibility_radius>
<start_x> <start_y>
<obj1_x> <obj1_y> [terrain_types...]
<obj2_x> <obj2_y> [terrain_types...]
...
```
- First line: Visibility radius
- Second line: Starting position
- Subsequent lines: Objective coordinates and optional terrain types

## Testing

### Using Test Cases
The project includes various test cases in the `test_cases` directory:

1. Small test cases (4x4 to 128x128 maps):
```bash
java src/Project test_cases/small/4-4/4-4-nodes.txt test_cases/small/4-4/4-4-edges.txt test_cases/small/4-4/4-4-objectives.txt output.txt
```

2. Large test cases (300x300 to 500x500 maps):
```bash
java src/Project test_cases/large/500-500/500-500-nodes.txt test_cases/large/500-500/500-500-edges.txt test_cases/large/500-500/500-500-objectives.txt output.txt
```

### Verifying Output
The program generates a path output file containing:
- Movement steps ("Moving to x-y")
- Path status messages ("Path is impassable!")
- Objective completion messages ("Objective N reached!")
- Terrain modification choices ("Number N is chosen!")

Compare your output with provided test case outputs to verify correctness.

## Implementation Details

### Key Features
- Dijkstra's algorithm for pathfinding
- Dynamic visibility system
- Terrain modification mechanics
- Path recalculation when obstacles are discovered

### Performance Considerations
- Efficient graph representation using adjacency lists
- Optimized visibility calculations
- Smart path recalculation only when necessary

## Troubleshooting

### Common Issues
1. FileNotFoundException
   - Verify file paths are correct
   - Ensure input files exist and are readable

2. Path is impassable
   - Check if objectives are reachable
   - Verify terrain types and visibility settings

### Debug Tips
- Use small test cases first
- Check output file for detailed movement logs
- Verify input file formats