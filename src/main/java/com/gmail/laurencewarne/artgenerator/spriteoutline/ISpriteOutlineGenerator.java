package com.gmail.laurencewarne.artgenerator.spriteoutline;

/**
Users of this interface implement a method of obtaining a 2D rectangular boolean
array representing a 'sprite outline'. The elements of the array model pixels with
the boolean value true representing black('filled') pixels, and false representing
white('empty') pixels.
 */
public interface ISpriteOutlineGenerator {

    /**
       Returns a 2D(rectangular) boolean array in which true values represent a 
       "filled" cell and false values represent an "empty" cell. "filled" cells
       form the outline of the sprite.
     */
    boolean[][] genSpriteOutline();

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
