package com.gmail.laurencewarne.artgenerator.cellgrid;

import java.util.List;


/**
Abstracts a grid of 'cells'. A cell grid can be thought of as a 2 dimensional table
where an (x, y) coordinate accesses the xth cell on the yth row. The topmost leftmost
cell being indexed by (0, 0), thus making the topmost leftmost cell the first cell on
the yth row.
 */
public interface ICellGrid<E> {

    /**
       Get the (x, y) coordinates of the 'Moore Neighbours' of a cell. The Moore 
       neighbours of a cell are the cells the the N, NE, E, SE, S, SW, W and NW 
       (if they exist) of the cell in place.
       
       The cells marked with an X constitute the Moore neighbourhood of the centre
       blank cell:
       
       <pre>
       |---|---|---|
       | X | X | X |
       |---|---|---|
       | X |   | X |
       |---|---|---|
       | X | X | X |
       |---|---|---|
       </pre>
       
       @param  coord coordinate of cell whose Moore neighbours are to be returned
       @return a List of coordinates of Moore neighbours of the cell
       @see <a href="https://en.wikipedia.org/wiki/Moore_neighborhood">https://en.wikipedia.org/wiki/Moore_neighborhood</a>
       @throws IllegalArgumentException If the coordinate is out of bounds of the grid
     */
    List<CellCoordinate> getCoordsOfMooreNeighbours( CellCoordinate coord )
	throws IllegalArgumentException;

    /**
       Get the (x, y) coordinates of the 'Neumann Neighbours' of a cell. The Neumann 
       neighbours of a cell are the cells the the N, E, S and W (if they exist) of
       the cell in place.
       
       The cells marked with an X constitute the Neumann neighbourhood of the centre
       blank cell:
       
       <pre>
       |---|---|---|
       |   | X |   |
       |---|---|---|
       | X |   | X |
       |---|---|---|
       |   | X |   |
       |---|---|---|
       </pre>
       
       @param  coord coordinate of cell whose Neumann neighbours are to be returned
       @return a List of coordinates of Neumann neighbours of the cell
       @see <a href="https://en.wikipedia.org/wiki/Von_Neumann_neighborhood">https://en.wikipedia.org/wiki/Von_Neumann_neighborhood</a>
       @throws IllegalArgumentException If the coordinate is out of bounds of the grid
     */    
    List<CellCoordinate> getCoordsOfNeumannNeighbours( CellCoordinate coord )
	throws IllegalArgumentException;

    /**
       Returns a List of coordinates which point to cells whose values are the same
       as the specified value.

       @param  value the value of which the desired cells should equal
       @return a List of coordinates which point to cells whose values are the same
       as the specified value
       @throws IllegalArgumentException If the coordinate is out of bounds of the grid
     */
    List<CellCoordinate> getCoordsOfCellsEqualTo( E value )
	throws IllegalArgumentException;

    /**
       Returns the (value in the) cell of the specified coordinate.

       @param  coord the coordinate of the cell to be returned
       @return returns the cell at the specified coordinate
       @throws IllegalArgumentException If the coordinate is out of bounds of the grid
     */
    E getValueAt( CellCoordinate coord )
	throws IllegalArgumentException;

    /**
       Sets the value at the specified coordinate.

       @param  coord the coordinate of the cell to be set
       @param  value the new value to set the cell to
       @throws IllegalArgumentException If the coordinate is out of bounds of the grid
     */
    void setValueAt( CellCoordinate coord, E value )
	throws IllegalArgumentException;

    /**
       Returns The length in x of the cell grid, aka the number of 'columns' in
       the grid.
       
       @return the length in x of the grid
     */
    int getXLength();

    /**
       Returns The length in y of the cell grid, aka the number of 'rows' in the
       grid.
       
       @return the length in y of the grid
     */
    int getYLength();
}
