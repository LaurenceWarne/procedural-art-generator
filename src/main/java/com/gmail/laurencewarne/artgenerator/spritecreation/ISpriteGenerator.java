package com.gmail.laurencewarne.artgenerator.spritecreation;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;

/**
Users of this interface implement a method of obtaining a 2D rectangular ICellGrid
representing a 'sprite'. The type T represents the type parameter of the output
ICellGrid.
 */
public interface ISpriteGenerator<T> {

    /**
       Returns a 2D(rectangular) boolean array in which true values represent a 
       "filled" cell and false values represent an "empty" cell. "filled" cells
       form the  of the sprite.
       
       @return rectangular boolean array representing a sprite 
     */
    //boolean[][] genSprite();

    /**
       Returns an implementation of a cell grid equivalent to the 2D boolean array
       returned by genSprite.
       
       @return ICellGrid representing a sprite.
     */
    ICellGrid<T> genSpriteAsCellGrid();

    /**
       Returns the length in x of the output of genSprite, aka the number
       of columns in the output grid.

       @return the length in x of the output sprite.
     */
    int getXLength();

    /**
       Returns the length in y of the output of genSprite, aka the number
       of rows in the output grid.

       @return the length in y of the output sprite.
     */    
    int getYLength();
}
