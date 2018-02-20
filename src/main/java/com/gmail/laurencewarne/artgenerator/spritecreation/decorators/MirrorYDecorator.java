package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import com.gmail.laurencewarne.artgenerator.spritecreation.ISpriteGenerator;
import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


/**
Implementation of ISpriteOutineGenerator used as a decorator to mirror the ouput of 
another ISpriteGenerator instance either up or down.

<pre>
For example if we were to decorate the following grid:

       |---|---|---|
       |   |   | X |
       |---|---|---|
       |   |   | X |
       |---|---|---|
       | X | X | X |
       |---|---|---|

And reflect it down the output would be:

       |---|---|---|
       |   |   | X |
       |---|---|---|
       |   |   | X |
       |---|---|---|
       | X | X | X |
       |---|---|---|
       | X | X | X |
       |---|---|---|
       |   |   | X |
       |---|---|---|
       |   |   | X |
       |---|---|---|

And if we reflected it up the output would be:

       |---|---|---|
       | X | X | X |
       |---|---|---|
       |   |   | X |
       |---|---|---|
       |   |   | X |
       |---|---|---|
       |   |   | X |
       |---|---|---|
       |   |   | X |
       |---|---|---|
       | X | X | X |
       |---|---|---|
</pre>
 */
public class MirrorYDecorator<T> extends SpriteTransformer<T, T> implements ISpriteGenerator<T> {

    /** True means reflect up, false means reflect down. */
    protected boolean reflectUp;

    /**
       Constructs a MirrorYDecorator decorating the specified spriteGenerator,
       reflecting the outuput of the decorated object up or down depending on the
       value of reflectUp.

       @param spriteGenerator the ISpriteGenerator to be decorated
       @param reflectUp whether the decorator should reflect up or down
    */
    public MirrorYDecorator( final ISpriteGenerator<T> spriteGenerator, final boolean reflectUp ) {

	super(spriteGenerator);
	this.reflectUp = reflectUp;
    }

    
    @Override
    public ICellGrid<T> genSpriteAsCellGrid() {

	ICellGrid<T> baseGrid = super.genSpriteAsCellGridFromDecoratee();
	int yLen = baseGrid.getYLength(), xLen = baseGrid.getXLength();
	ICellGrid<T> decGrid = new ArrayListCellGrid<>(xLen, yLen*2, null);
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
    public int getYLength() {

	return super.getYLength() * 2;
    }
}
