package com.gmail.laurencewarne.artgenerator.spriteoutline;


public interface ISpriteOutlineGenerator {

    /**
       Returns a 2D(rectangular) boolean array in which true values represent a 
       "filled" cell and false values represent an "empty" cell. "filled" cells
       form the outline of the sprite.
     */
    boolean[][] genSpriteOutline();
}
