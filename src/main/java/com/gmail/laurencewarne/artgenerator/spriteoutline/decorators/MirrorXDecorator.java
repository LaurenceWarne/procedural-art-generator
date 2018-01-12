package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import com.gmail.laurencewarne.artgenerator.spriteoutline.ISpriteOutlineGenerator;


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
    public boolean[][] genSpriteOutline() {

	boolean[][] baseGrid = super.genSpriteOutline();
	int yLen = baseGrid.length, xLen = baseGrid[0].length;
	boolean[][] decGrid = new boolean[yLen][xLen * 2];
	for ( int i = 0; i < yLen; i++ ){
	    for ( int j = 0; j < xLen*2; j++ ){
		// We transform this function of j to produce the reflections
		float jFunc = Math.abs(xLen - j - 0.5f);
		if ( reflectLeft ){
		    decGrid[i][j] = baseGrid[i][Math.round(jFunc - 0.5f)];
		}
		else {  // We reflect right
		    decGrid[i][j] = baseGrid[i][Math.round(-jFunc + xLen - 0.5f)];
		}		    
	    }
	}
	return decGrid;
    }

    @Override
    public int getXLengthOfOutline() {

	return super.getXLengthOfOutline() * 2;
    }
}
