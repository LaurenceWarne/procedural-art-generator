package com.gmail.laurencewarne.artgenerator.spritecreation.decorators.colourfunctions;


/**
Implementations of this interface represent a 'colour function'. That is they take
the coordinates of a pixel in an image and return the colour of that pixel.
 */
public interface IColourFunction {

    /**
       Returns an array of integers representing the colour of a pixel at a specified
       point.
       
       @param  x the x coordinate of the pixel
       @param  y the y coordinate of the pixel
       @throws IllegalArgumentException if either the x or y coordinates are not
       valid
     */
    SpriteColour getColourAtCoordinate( int x, int y ) throws IllegalArgumentException;
}
