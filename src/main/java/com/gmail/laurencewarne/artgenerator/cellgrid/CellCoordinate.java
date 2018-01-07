package com.gmail.laurencewarne.artgenerator.cellgrid;


public class CellCoordinate {

    public final int x, y;

    public CellCoordinate( final int x, final int y ) {

	this.x = x;
	this.y = y;
    }

    public boolean equals( Object o ) {

	if ( this == o )
	    return true;
	if ( o == null )
	    return false;
	if ( getClass() != o.getClass() )
	    return false;
	CellCoordinate otherCoord = (CellCoordinate) o;
	return this.x == otherCoord.x && this.y == otherCoord.y;
    }
}
