package com.gmail.laurencewarne.artgenerator.spritecolour;


/**
Implementations of this interface represent a 'colour function'. That is they take
the coordinates of a pixel in an image and return the colour of that pixel.
 */
public interface IColourFunction {

    /**
       Returns an array of integers representing the HSV colour of a pixel at a
       specified point.
     */
    int[] getHSVColourAtCoordinate(int x, int y);

    /**
       Returns an array of integers representing the RGB colour of a pixel at a
       specified point.
     */    
    int[] getRGBColourAtCoordinate(int x, int y);
}
