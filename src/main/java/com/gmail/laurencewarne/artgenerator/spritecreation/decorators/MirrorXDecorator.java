package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import com.gmail.laurencewarne.artgenerator.spritecreation.ISpriteGenerator;
import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


/**
Implementation of ISpriteGenerator used as a decorator to mirror the output of
another ISpriteGenerator instance either left or right.

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
public class MirrorXDecorator<T> extends SpriteTransformer<T, T> implements ISpriteGenerator<T> {

    /** 
	True means the decorator reflects the  left, and false means it 
	reflects it right.
    */
    protected boolean reflectLeft;

    /**
       Constructs a MirrorXDecorator decorating the specified spriteGenerator,
       reflecting the outuput of the decorated object left or right depending on the
       value of reflectLeft.

       @param spriteGenerator the ISpriteGenerator to be decorated
       @param reflectLeft whether the decorator should reflect left or right
     */
    public MirrorXDecorator( final ISpriteGenerator<T> spriteGenerator, final boolean reflectLeft ) {
	
	super(spriteGenerator);
	this.reflectLeft = reflectLeft;
    }
    
    @Override
    public ICellGrid<T> genSpriteAsCellGrid() {

	ICellGrid<T> baseGrid = super.genSpriteAsCellGridFromDecoratee();
	int yLen = baseGrid.getYLength(), xLen = baseGrid.getXLength();
	ICellGrid<T> decGrid = new ArrayListCellGrid<>(xLen*2, yLen, null);
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
    public int getXLength() {

	return super.getXLength() * 2;
    }
}
