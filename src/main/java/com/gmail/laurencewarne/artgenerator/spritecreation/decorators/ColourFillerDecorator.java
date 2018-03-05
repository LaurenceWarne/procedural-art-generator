package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import com.gmail.laurencewarne.artgenerator.spritecreation.decorators.colourfunctions.IColourFunction;
import com.gmail.laurencewarne.artgenerator.spritecreation.decorators.colourfunctions.SpriteColour;
import com.gmail.laurencewarne.artgenerator.spritecreation.ISpriteGenerator;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;


public class ColourFillerDecorator extends SpriteTransformer<Boolean, SpriteColour> implements ISpriteGenerator<SpriteColour> {

    private IColourFunction colourFunction;

    public ColourFillerDecorator( final ISpriteGenerator<Boolean> spriteGenerator, final IColourFunction colourFunction ) {

	super(spriteGenerator);
	this.colourFunction = colourFunction;
    }

    @Override
    public ICellGrid<SpriteColour> genSpriteAsCellGrid() {

	ICellGrid<Boolean> baseGrid = super.genSpriteAsCellGridFromDecoratee();
	int yLen = baseGrid.getYLength(), xLen = baseGrid.getXLength();
	ICellGrid<SpriteColour> decGrid = new ArrayListCellGrid<>(xLen, yLen, null);
	for ( int i = 0; i < yLen; i++ ){
	    for ( int j = 0; j < xLen; j++ ){
		SpriteColour spriteColour = colourFunction.getColourAtCoordinate(j, i);
		decGrid.setValueAt(new CellCoordinate(j, i), spriteColour);
	    }
	}
	return decGrid;
    }

    public IColourFunction getColourFunction() {

	return colourFunction;
    }

    public void setColourFunction( final IColourFunction colourFunction ) {

	this.colourFunction = colourFunction;
    }
}
