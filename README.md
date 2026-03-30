# Maze Generator & Pathfinding Visualizer

A full-stack web application that dynamically generates complex mazes and visualizes how different pathfinding algorithms search for the shortest path from start to finish.

This project was built to demonstrate a deep understanding of data structures, graph algorithms, and full-stack integration using a functional programming backend and a component-based frontend.

## Features

### Maze Generation
- Depth-First Search (DFS): Generates a "Perfect Maze" (a maze with exactly one path between any two points).
- Braid Maze Modifier: A custom algorithm that intelligently breaks down random walls after the initial generation to create loops and shortcuts. This creates a much more interesting terrain for advanced algorithms (like A*) to evaluate.

### Pathfinding Algorithms
- Breadth-First Search (BFS): Explores equally in all directions. Guarantees the shortest path.
- A* (A-Star) Search: Uses Manhattan distance heuristics to "guess" the direction of the target, finding the shortest path much faster than BFS.

### Visualizer (Frontend)
- Step-by-step animation of the maze generation process (tracking the history of the algorithms).
- Distinct visual states for:
Exploration (History): Shows how the algorithm searches through dead ends.
Shortest Path: Highlights the final, optimal route.
Frontend project: https://github.com/vicovacana/search-algorithms-visualization

## Technologies

### Backend: Clojure
- Uses Ring and Compojure for routing and API endpoints.
- Pure functional implementation of complex graph algorithms (utilizing persistent data structures like Queues, Sets, and Maps).
- Fully RESTful JSON API.

### Frontend: Angular & TypeScript
- Manages application state and grid rendering.
- Uses HttpClient for seamless communication with the Clojure backend.
- Handles precise, interval-based CSS animations to bring the algorithms to life.

## How to run
1. Start the Clojure project
   lein run
2. Start the Angular project: https://github.com/vicovacana/search-algorithms-visualization
   - npm install
   - npm run dev
3. Browse http://localhost:4200

See more details: https://github.com/vicovacana/clojure-project.wiki.git
