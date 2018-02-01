package com.gmail.laurencewarne.artgenerator.spriteoutline;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;

/**
Users of this interface implement a method of obtaining a 2D rectangular array
 representing a 'sprite outline'.
 */
public interface ISpriteOutlineGenerator {

    /**
       Returns a 2D(rectangular) boolean array in which true values represent a 
       "filled" cell and false values represent an "empty" cell. "filled" cells
       form the outline of the sprite.
       
       @return rectangular boolean array representing a sprite outline
     */
    boolean[][] genSpriteOutline();

    /**
       Returns an implementation of a cell grid equivalent to the 2D boolean array
       returned by genSpriteOutline.
       
       @return ICellGrid representing a sprite outline.
     */
    ICellGrid<Boolean> genSpriteOutlineAsCellGrid();

    /**
       Returns the length in x of the output of genSpriteOutline, aka the number
       of columns in the outline.

       @return the length in x of the sprite outline.
     */
    int getXLengthOfOutline();

    /**
       Returns the length in y of the output of genSpriteOutline, aka the number
       of rows in the outline.

       @return the length in y of the sprite outline.
     */    
    int getYLengthOfOutline();
}
