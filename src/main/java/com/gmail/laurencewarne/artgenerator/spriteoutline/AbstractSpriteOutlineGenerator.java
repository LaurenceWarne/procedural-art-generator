package com.gmail.laurencewarne.artgenerator.spriteoutline;

import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;

/**
Abstract class with a few useful methods that may be useful for objects
implementing the ISpriteOutlineGenerator interface.
 */
public abstract class AbstractSpriteOutlineGenerator implements ISpriteOutlineGenerator {

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
	if ( !(coord.x < getXLengthOfOutline() && coord.y < getYLengthOfOutline()) ){
	    return false;
	}
	else {
	    return true;
	}
    }
}
