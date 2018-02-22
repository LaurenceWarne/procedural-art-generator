package com.gmail.laurencewarne.artgenerator.spritecreation;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;

/**
Abstract class with a few methods that may be useful for objects implementing the
 ISpriteGenerator interface.
 */
public abstract class AbstractSpriteGenerator<T> implements ISpriteGenerator<T> {

    /**
       Returns True if the specified coordinate lies within the size of an output
       grid(all are the same size), false otherwise.

       @param  coord the coordinate to test
       @return whether the specified coordinate is in an output grid
     */
    public boolean isValidCoord( final CellCoordinate coord ) {

	if ( !(coord.x >= 0 && coord.y >= 0) ){
	    return false;
	}
	if ( !(coord.x < getXLength() && coord.y < getYLength()) ){
	    return false;
	}
	else {
	    return true;
	}
    }
}
