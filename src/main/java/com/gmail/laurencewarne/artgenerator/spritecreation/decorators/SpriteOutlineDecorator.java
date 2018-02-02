package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import com.gmail.laurencewarne.artgenerator.spritecreation.ISpriteOutlineGenerator;
import com.gmail.laurencewarne.artgenerator.spritecreation.AbstractSpriteOutlineGenerator;
import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;


public abstract class SpriteOutlineDecorator extends AbstractSpriteOutlineGenerator implements ISpriteOutlineGenerator {

    private final ISpriteOutlineGenerator spriteOutlineGenerator;

    public SpriteOutlineDecorator( final ISpriteOutlineGenerator spriteOutlineGenerator ) {
	this.spriteOutlineGenerator = spriteOutlineGenerator;
    }

    @Override
    public ICellGrid<Boolean> genSpriteOutlineAsCellGrid() {

	return spriteOutlineGenerator.genSpriteOutlineAsCellGrid();
    }
    
    /**
    @Override
    public boolean[][] genSpriteOutline() {

	return spriteOutlineGenerator.genSpriteOutline();
    }
    */
    
    @Override
    public int getXLengthOfOutline() {

	return spriteOutlineGenerator.getXLengthOfOutline();
    }

    @Override
    public int getYLengthOfOutline() {

	return spriteOutlineGenerator.getYLengthOfOutline();
    }

}
