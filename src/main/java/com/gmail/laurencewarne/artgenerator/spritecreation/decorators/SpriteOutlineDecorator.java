package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import com.gmail.laurencewarne.artgenerator.spritecreation.ISpriteOutlineGenerator;
import com.gmail.laurencewarne.artgenerator.spritecreation.AbstractSpriteOutlineGenerator;
import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;


public abstract class SpriteOutlineDecorator<E> extends AbstractSpriteOutlineGenerator<E> implements ISpriteOutlineGenerator<E> {

    private final ISpriteOutlineGenerator<E> spriteOutlineGenerator;

    public SpriteOutlineDecorator( final ISpriteOutlineGenerator<E> spriteOutlineGenerator ) {
	this.spriteOutlineGenerator = spriteOutlineGenerator;
    }

    @Override
    public ICellGrid<E> genSpriteOutlineAsCellGrid() {

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
