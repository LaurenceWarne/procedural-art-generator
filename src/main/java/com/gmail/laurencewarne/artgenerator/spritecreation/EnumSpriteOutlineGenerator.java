package com.gmail.laurencewarne.artgenerator.spritecreation;

import java.util.Random;
import java.util.List;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


/**
<pre>
An implementation of the ISpriteOutlineGenerator interface which uses an
implementation of an ICellGrid to generate sprite outlines.

This implementation uses a grid, whose rows and columns are analogous to the 
rectangular output grid/array, to produce said output 2D boolean grid/array. The 
first 'anterior' grid is passed to the class' constructor. Its cells consist of enum
values which in addition to a random seed determine the output grid whose cells
consist of purely boolean values.

ALWAYS_FILLED cells in anteriorGrid are always true cells in the base grid. 
ALWAYS_EMPTY cells in anteriorGrid are always false cells in the base grid, VARIED 
cells can be either and are determined by a seed. An EnumSpriteOutlineGenerator
with the same seed an anterior grid will always produce the same output grid.
</pre>
 */
public class EnumSpriteOutlineGenerator extends AbstractSpriteOutlineGenerator implements ISpriteOutlineGenerator {

    /** 
	Represents possible states in the generated base grid. ALWAYS_FILLED cells
	in anteriorGrid are always true cells in the base grid. ALWAYS_EMPTY cells in
	anteriorGrid are always false cells in the base grid, VARIED cells can be 
	either and are determined by a seed.
    */
    public enum CellState {
	ALWAYS_FILLED, ALWAYS_EMPTY, VARIED
    };

    /** Grid used to generate the base grid. **/
    protected final ICellGrid<CellState> anteriorGrid;
    protected long seed;
    
    /**
      Constructs a new instance of EnumSpriteOutlineGenerator based on an ICellGrid
      object and a seed.
      
      @param anteriorGrid an ICellGrid used to generate the sprite outline
      @param seed the seed used to create a Random object which will be used to
      generate random numbers for cells marked as VARIED in the anteriorGrid
     */
    public EnumSpriteOutlineGenerator( final ICellGrid<CellState> anteriorGrid, final long seed ) {

	this.anteriorGrid = anteriorGrid;
	this.seed = seed;
    }

    /**
       Sets the cell at the specified coordinate in the anterior grid to the 
       specified value.

       @param coord the coordinate of the cell to be changed
       @param value the cell at the coordinate is to be changed to
     */
    public void setCellStateAt( final CellCoordinate coord, final CellState value ) {

	anteriorGrid.setValueAt(coord, value);
    }

    /**
       Returns the cell at the specified coordinate in the anterior grid.

       @param  coord the coordinate of the cell to be returned
       @return value of the cell at the coordinate
     */
    public CellState getCellStateAt( final CellCoordinate coord ) {

	return anteriorGrid.getValueAt(coord);
    }    

    public void setSeed( final long seed ) {

	this.seed = seed;
    }

    public long getSeed() {

	return seed;
    }

    @Override
    public ICellGrid<Boolean> genSpriteOutlineAsCellGrid() {

	Random random = new Random(seed);
	int xLength = getXLengthOfOutline(), yLength = getYLengthOfOutline();
	ICellGrid<Boolean> spriteOutline = new ArrayListCellGrid(xLength, yLength, false);
	for ( int i = 0; i < yLength; i++ ){
	    for ( int j = 0; j < xLength; j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		CellState state = anteriorGrid.getValueAt(coord);
		if ( state.equals(CellState.ALWAYS_FILLED) ){
		    spriteOutline.setValueAt(coord, true);
		}
		else if ( state.equals(CellState.ALWAYS_EMPTY) ){
		    spriteOutline.setValueAt(coord, false);
		}
		else {
		    spriteOutline.setValueAt(coord, random.nextBoolean());
		}
	    }
	}
	return spriteOutline;
    }

    @Override
    public int getXLengthOfOutline() {

	return anteriorGrid.getXLength();
    }

    @Override
    public int getYLengthOfOutline() {

	return anteriorGrid.getYLength();
    }
}
