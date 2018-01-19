package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import com.gmail.laurencewarne.artgenerator.spriteoutline.ISpriteOutlineGenerator;
import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


public class MirrorXDecorator extends SpriteOutlineDecorator implements ISpriteOutlineGenerator {

    /** 
	True means the decorator reflects the outline left, and false means it 
	reflects it right.
    */
    protected boolean reflectLeft;

    public MirrorXDecorator( final ISpriteOutlineGenerator spriteOutlineGenerator, final boolean reflectLeft ) {
	
	super(spriteOutlineGenerator);
	this.reflectLeft = reflectLeft;
    }
    
    @Override
    public ICellGrid<Boolean> genSpriteOutlineAsCellGrid() {

	ICellGrid<Boolean> baseGrid = super.genSpriteOutlineAsCellGrid();
	int yLen = baseGrid.getYLength(), xLen = baseGrid.getXLength();
	ICellGrid<Boolean> decGrid = new ArrayListCellGrid(xLen*2, yLen, false);
	for ( int i = 0; i < yLen; i++ ){
	    for ( int j = 0; j < xLen*2; j++ ){
		CellCoordinate coord = new CellCoordinate(j, i), baseCoord;
		// We transform this function of j to produce the reflections
		float jFunc = Math.abs(xLen - j - 0.5f);
		if ( reflectLeft ){
		    baseCoord = new CellCoordinate(Math.round(jFunc - 0.5f), i);
		}
		else {  // We reflect right
		    baseCoord =
			new CellCoordinate(Math.round(-jFunc + xLen - 0.5f), i);
		}
		decGrid.setValueAt(coord, baseGrid.getValueAt(baseCoord));
	    }
	}
	return decGrid;
    }

    @Override
    public int getXLengthOfOutline() {

	return super.getXLengthOfOutline() * 2;
    }
}
