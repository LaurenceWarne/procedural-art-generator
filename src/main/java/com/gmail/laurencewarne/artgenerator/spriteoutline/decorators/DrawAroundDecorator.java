package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import com.gmail.laurencewarne.artgenerator.spriteoutline.ISpriteOutlineGenerator;


public DrawAroundDecorator extends SpriteOutlineDecorator implements ISpriteOutlineGenerator {

    private final Set<CellCoordinate> alwaysFullCoords, alwaysEmptyCoords;

    public DrawAroundDecorator( final ISpriteOutlineGenerator spriteOutlineGenerator, final Set<CellCoordinate> alwaysFullCoords, final Set<CellCoordinate> alwaysEmptyCoords) {

	super(spriteOutlineGenerator);
	this.alwaysFullCoords = alwaysFullCoords;
	this.alwaysEmptyCoords = alwaysEmptyCoords;
    }

    public DrawAroundDecorator( final ISpriteOutlineGenerator spriteOutlineGenerator ) {

	DrawAroundDecorator(spriteOutlineGenerator, new HashSet<CellCoordinate>(), new HashSet<CellCoordinate());
    }

    public void addAlwaysFullCoord( CellCoordinate coord ) {

	alwaysFullCoords.add(coord);
    }

    public void addAlwaysEmptyCoord( CellCoordinate coord ) {

	alwaysEmptyCoords.add(coord);
    }    

    public boolean[][] genSpriteOutline() {

	ICellGrid<Boolean> baseGrid = super.genSpriteOutline();
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
