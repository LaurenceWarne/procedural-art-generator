package com.gmail.laurencewarne.artgenerator.cellgrid;

import java.util.Arrays;

/**
An immutable class encapsulating a coordinate on a two dimensional plane.
 */
public final class CellCoordinate {

    public final int x, y;

    /**
       Constructs a CellCoordinate with x and y values set to the specified x and
       y values.

       @param x the x coordinate of the CellCoordinate
       @param y the y coordinate of the CellCoordinate
     */
    public CellCoordinate( final int x, final int y ) {

	this.x = x;
	this.y = y;
    }

    @Override
    public boolean equals( Object o ) {

	if ( this == o ){
	    return true;
	}
	if ( o == null ){
	    return false;
	}
	if ( getClass() != o.getClass() ){
	    return false;
	}
	CellCoordinate otherCoord = (CellCoordinate) o;
	return this.x == otherCoord.x && this.y == otherCoord.y;
    }

    @Override
    public int hashCode() {
	return Arrays.hashCode(new Object[] {x, y});
    }

    @Override
    public String toString() {

	return "x: " + x + " y: " + y;
    }
}
