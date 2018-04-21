##Scala island generator
Randomly generates a island and generates a svg-map of it

Inspired by [Polygonal Map Generation for Games](http://www-cs-students.stanford.edu/~amitp/game-programming/polygon-map-generation/) by red blob games.

###Method
The island is generated in an iterative process. The steps are, in order:

1. Generate the 2D structure:
   1. Generate a parameterized number of points in 2D-space
   2. Calculate the Voronoi-Graph of these points
   3. Use the center points of the graph as the new points and iterate