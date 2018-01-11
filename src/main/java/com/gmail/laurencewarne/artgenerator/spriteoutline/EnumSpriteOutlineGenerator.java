package com.gmail.laurencewarne.artgenerator.spriteoutline;

import java.util.Random;
import java.util.List;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


/**
An implementation of the ISpriteOutlineGenerator interface which uses an
implementation of an ICellGrid to generate sprite outlines.

This implementation uses the concept of 2 grids, whose rows and columns are analogous
to the rectangular output array, to produce said output 2D boolean array. The first 
'anterior' grid is passed to the class' constructor. It's cells consist of enum
values which in addition to a random seed determine the 'base' grid whose cells
consist of purely boolean values. An 'outline' is then drawn around the true values
 of the base grid to produce the output 2D boolean array.
 */
public class EnumSpriteOutlineGenerator implements ISpriteOutlineGenerator {

    /** 
	Represents possible states in the generated base grid. ALWAYS_FILLED cells
	in anteriorGrid are always true cells in the base grid. ALWAYS_EMPTY cells in
	anteriorGrid are always false cells in the base grid, VARIED cells can be 
	either and are determined by a (optionally specified) seed.
    */
    public enum CellState {
	ALWAYS_FILLED, ALWAYS_EMPTY, VARIED
    };

    /** Grid used to generate the base grid. **/
    protected final ICellGrid<CellState> anteriorGrid;
    protected final int xLength, yLength;
    protected final Random random = new Random();
    
    public EnumSpriteOutlineGenerator( final ICellGrid<CellState> anteriorGrid ) {

	this.anteriorGrid = anteriorGrid;
	this.xLength = anteriorGrid.getXLength();
	this.yLength = anteriorGrid.getYLength();
    }

    /**
       Sets the cell at the specified coordinate in the anterior grid to the 
       specified value.

       @param coord the coordinate of the cell to be changed.
       @param value the value the cell at the coordinate is to be changed to.
     */
    public void setCellStateAt( final CellCoordinate coord, final CellState value ) {

	anteriorGrid.setValueAt(coord, value);
    }

    public CellState getCellStateAt( final CellCoordinate coord ) {

	return anteriorGrid.getValueAt(coord);
    }    

    protected ICellGrid<Boolean> genBaseGrid( final long seed ) {

	Random random = new Random(seed);
	ICellGrid<Boolean> baseGrid =
	    new ArrayListCellGrid<Boolean>(xLength, yLength, false);
	// Create array from which outline is made.
	for ( int i = 0; i < yLength; i++ ){
	    for ( int j = 0; j < xLength; j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		CellState state = anteriorGrid.getValueAt(coord);
		if ( state.equals(CellState.ALWAYS_FILLED) )
		    baseGrid.setValueAt(coord, true);
		else if ( anteriorGrid.equals(CellState.ALWAYS_EMPTY) )
		    baseGrid.setValueAt(coord, false);
		else
		    baseGrid.setValueAt(coord, random.nextBoolean());
	    }
	}
	
	return baseGrid;
    }

    public boolean[][] genSpriteOutline( final long seed ) {

	ICellGrid<Boolean> baseGrid = genBaseGrid(seed);
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

    @Override
    public boolean[][] genSpriteOutline() {

	return genSpriteOutline(random.nextLong());
    }

    public int getGridXLength() {

	return anteriorGrid.getXLength();
    }

    public int getGridYLength() {

	return anteriorGrid.getYLength();
    }    
}
