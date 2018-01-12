package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.gmail.laurencewarne.artgenerator.spriteoutline.ISpriteOutlineGenerator;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;


public class DrawAroundDecorator extends SpriteOutlineDecorator implements ISpriteOutlineGenerator {

    protected final Set<CellCoordinate> alwaysFullCoords, alwaysEmptyCoords;

    public DrawAroundDecorator( final ISpriteOutlineGenerator spriteOutlineGenerator, final Set<CellCoordinate> alwaysFullCoords, final Set<CellCoordinate> alwaysEmptyCoords ) {

	super(spriteOutlineGenerator);
	this.alwaysFullCoords = alwaysFullCoords;
	this.alwaysEmptyCoords = alwaysEmptyCoords;
    }

    public DrawAroundDecorator( final ISpriteOutlineGenerator spriteOutlineGenerator ) {

	this(spriteOutlineGenerator, new HashSet<CellCoordinate>(), new HashSet<CellCoordinate>());
    }

    public void addAlwaysFullCoord( final CellCoordinate coord ) throws IllegalArgumentException {

	if ( !isValidCoord(coord) ){
	    throw new IllegalArgumentException("Coordinate does not lie in the grid!");
	}
	else {
	    alwaysFullCoords.add(coord);
	}
    }

    public void addAlwaysEmptyCoord( final CellCoordinate coord ) throws IllegalArgumentException {

	if ( !isValidCoord(coord) ){
	    throw new IllegalArgumentException("Coordinate does not lie in the grid!");
	}
	else {
	    alwaysEmptyCoords.add(coord);
	}
    }    

    public boolean[][] genSpriteOutline() {

	// change
	ICellGrid<Boolean> baseGrid = null;//super.genSpriteOutline();
	int xLength = getXLengthOfOutline(), yLength = getYLengthOfOutline();
	boolean[][] spriteArray = new boolean[yLength][xLength];
	for ( int i = 0; i < yLength; i++ ){
	    for ( int j = 0; j < xLength; j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		boolean state = baseGrid.getValueAt(coord);
		if ( state == false ){
		    List<CellCoordinate> neighbours = baseGrid.
			getCoordsOfMooreNeighbours(coord);
		    boolean foundNeighbour = false;
		    for ( CellCoordinate adjCoord : neighbours ){
			if ( baseGrid.getValueAt(adjCoord) == true ){
			    foundNeighbour = true;
			}
		    }
		    spriteArray[i][j] = foundNeighbour;
		}
		else
		    spriteArray[i][j] = false;
	    }
	}
	return spriteArray;
    }
    
}
