package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import com.gmail.laurencewarne.artgenerator.spriteoutline.ISpriteOutlineGenerator;
import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


/**
Implementation of ISpriteOutlineGenerator used as a decorator to mirror the output of
another ISpriteOutlineGenerator instance either left or right.

<pre>
For example if we were to decorate the following grid:

       |---|---|---|
       |   |   | X |
       |---|---|---|
       |   |   | X |
       |---|---|---|
       | X | X | X |
       |---|---|---|

And reflect it right the ouput would be:

       |---|---|---|---|---|---|
       |   |   | X | X |   |   |
       |---|---|---|---|---|---|
       |   |   | X | X |   |   |
       |---|---|---|---|---|---|
       | X | X | X | X | X | X |
       |---|---|---|---|---|---|

And if we reflected it left the output would be:

       |---|---|---|---|---|---|
       | X |   |   |   |   | X |
       |---|---|---|---|---|---|
       | X |   |   |   |   | X |
       |---|---|---|---|---|---|
       | X | X | X | X | X | X |
       |---|---|---|---|---|---|
</pre>
 */
public class MirrorXDecorator extends SpriteOutlineDecorator implements ISpriteOutlineGenerator {

    /** 
	True means the decorator reflects the outline left, and false means it 
	reflects it right.
    */
    protected boolean reflectLeft;

    /**
       Constructs a MirrorXDecorator decorating the specified spriteOutlineGenerator,
       reflecting the outuput of the decorated object left or right depending on the
       value of reflectLeft.

       @param spriteOutlineGenerator the ISpriteOutlineGenerator to be decorated
       @param reflectLeft whether the decorator should reflect left or right
     */
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
