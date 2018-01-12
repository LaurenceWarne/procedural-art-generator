package com.gmail.laurencewarne.artgenerator.cellgrid;


/**
Abstract class that most/all implementations of ICellGrid should subclass. 
 */
public abstract class AbstractCellGrid<E> implements ICellGrid<E> {

    /** The number of columns in the grid. **/
    final protected int xLength;
    /** The number of rows in the grid. **/
    final protected int yLength;

    public AbstractCellGrid( final int xLength, final int yLength ) throws IllegalArgumentException {

	// Check if lengths are valid
	if ( xLength <= 0 || yLength <= 0 ){
	    throw new IllegalArgumentException("Lengths must be greater than zero!");
	}
	this.xLength = xLength;
	this.yLength = yLength;
    }

    /**
       Returns true if the coordinate is within the confines of the grid, false
       otherwise. An (x, y) coordinate is considered in the grid if x >= 0, y >= 0 
       and x and y are less than the x and y length of the grid respectively. x and
       y must also be integers but this is enforced by the CellCoordinate class.

       @param  coord the coordinate whose existence in the grid is to be found.
       @return whether the specified coordinate is in the grid.
     */
    public boolean isValidCoord( CellCoordinate coord ) {

	if ( coord.x < 0 || coord.y < 0 || coord.x >= xLength || coord.y >= yLength ){
	    return false;
	}
	else {
	    return true;
	}
    }

    @Override
    public int getXLength() {

	return xLength;
    }

    @Override
    public int getYLength() {

	return yLength;
    }
}
