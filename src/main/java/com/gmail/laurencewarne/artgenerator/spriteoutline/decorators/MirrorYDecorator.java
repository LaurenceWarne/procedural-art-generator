package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import com.gmail.laurencewarne.artgenerator.spriteoutline.ISpriteOutlineGenerator;
import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


public class MirrorYDecorator extends SpriteOutlineDecorator implements ISpriteOutlineGenerator {

    protected boolean reflectUp;

    public MirrorYDecorator( final ISpriteOutlineGenerator spriteOutlineGenerator, final boolean reflectUp ) {

	super(spriteOutlineGenerator);
	this.reflectUp = reflectUp;
    }

    
    @Override
    public ICellGrid<Boolean> genSpriteOutlineAsCellGrid() {

	ICellGrid<Boolean> baseGrid = super.genSpriteOutlineAsCellGrid();
	int yLen = baseGrid.getYLength(), xLen = baseGrid.getXLength();
	ICellGrid<Boolean> decGrid = new ArrayListCellGrid(xLen, yLen*2, false);
	for ( int i = 0; i < yLen*2; i++ ){
	    // We translate this function of i to produce the reflections.
	    float iFunc = Math.abs(yLen - i - 0.5f);
	    int transIFunc;
	    if ( reflectUp ){
		transIFunc = Math.round(iFunc - 0.5f);
	    }
	    else {  // We reflect up
		transIFunc = Math.round(-iFunc + yLen - 0.5f);
	    }
	    // Y Coordinates are rows so whole rows need to be set in the new grid.
	    for ( int j = 0; j < xLen; j++ ){
		CellCoordinate transCoord = new CellCoordinate(j, transIFunc);
		CellCoordinate newCoord = new CellCoordinate(j, i);
		decGrid.setValueAt(newCoord, baseGrid.getValueAt(transCoord));
	    }
	}
	return decGrid;
    }

    @Override
    public int getYLengthOfOutline() {

	return super.getYLengthOfOutline() * 2;
    }
}
