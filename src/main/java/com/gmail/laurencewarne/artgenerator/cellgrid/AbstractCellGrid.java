package com.gmail.laurencewarne.artgenerator.cellgrid;


public abstract class AbstractCellGrid implements ICellGrid {

    /** The number of columns in the grid. **/
    final protected int xLength;
    /** The number of rows in the grid. **/
    final protected int yLength;

    public AbstractCellGrid( final int xLength, final int yLength ) {

	this.xLength = xLength;
	this.yLength = yLength;
    }

    public boolean isValidCoord( CellCoordinate coord ) {

	if ( coord.x < 0 || coord.y < 0 || coord.x >= xLength || coord.y >= yLength ){
	    return false;
	}
	else {
	    return true;
	}
    }
}
