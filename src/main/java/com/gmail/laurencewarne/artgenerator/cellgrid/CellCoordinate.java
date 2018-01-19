package com.gmail.laurencewarne.artgenerator.cellgrid;

import java.util.Arrays;

/**
An immutable class encapsulating a coordinate on a two dimensional plane.
 */
public class CellCoordinate {

    public final int x, y;

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
