package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import java.util.Arrays;

import com.gmail.laurencewarne.artgenerator.spriteoutline.ISpriteOutlineGenerator;


public class MirrorYDecorator extends SpriteOutlineDecorator implements ISpriteOutlineGenerator {

    protected boolean reflectUp;

    public MirrorYDecorator( final ISpriteOutlineGenerator spriteOutlineGenerator, final boolean reflectUp ) {

	super(spriteOutlineGenerator);
	this.reflectUp = reflectUp;
    }

    @Override
    public boolean[][] genSpriteOutline() {

	boolean[][] baseGrid = super.genSpriteOutline();
	int yLen = baseGrid.length, xLen = baseGrid[0].length;
	boolean[][] decGrid = new boolean[yLen*2][xLen];
	for ( int i = 0; i < yLen*2; i++ ){
	    // We translate this function of i to produce the reflections.
	    float iFunc = Math.abs(yLen - i - 0.5f);
	    if ( reflectUp ){
		decGrid[i] = Arrays.copyOf(baseGrid[Math.round(iFunc - 0.5f)], xLen);
	    }
	    else {  // We reflect up
		decGrid[i] = Arrays.copyOf(baseGrid[Math.round(-iFunc + yLen - 0.5f)], xLen);
	    }
	}
	return decGrid;
    }

    @Override
    public int getYLengthOfOutline() {

	return super.getYLengthOfOutline() * 2;
    }
}
