package com.gmail.laurencewarne.artgenerator.spritecreation;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;

/**
Abstract class with a few methods that may be useful for objects implementing the
 ISpriteOutlineGenerator interface.
 */
public abstract class AbstractSpriteOutlineGenerator implements ISpriteOutlineGenerator {

    @Override
    public boolean[][] genSpriteOutline() {

	ICellGrid<Boolean> cellGrid = genSpriteOutlineAsCellGrid();
	int xLength = cellGrid.getXLength(), yLength = cellGrid.getYLength();
	boolean[][] spriteOutline = new boolean[yLength][xLength];
	for ( int i = 0; i < yLength; i++ ){
	    for ( int j = 0; j < xLength; j++ ){
		spriteOutline[i][j] = cellGrid.getValueAt(new CellCoordinate(j, i));
	    }
	}
	return spriteOutline;
    }

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
