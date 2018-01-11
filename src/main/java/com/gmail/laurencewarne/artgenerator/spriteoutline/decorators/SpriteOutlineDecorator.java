package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import com.gmail.laurencewarne.artgenerator.spriteoutline.ISpriteOutlineGenerator;


public abstract class SpriteOutlineDecorator implements ISpriteOutlineGenerator {

    private final ISpriteOutlineGenerator spriteOutlineGenerator;

    public SpriteOutlineDecorator( final ISpriteOutlineGenerator spriteOutlineGenerator ) {
	this.spriteOutlineGenerator = spriteOutlineGenerator;
    }

    @Override
    public boolean[][] genSpriteOutline() {

	return spriteOutlineGenerator.genSpriteOutline(seed);
    }
}
