package com.gmail.laurencewarne.artgenerator.cellgrid;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;


/**
An implementation of the ICellGrid interface which uses an Arraylist to store the 
elements of the grid.
 */
public class ArrayListCellGrid<E> extends AbstractCellGrid<E> implements ICellGrid<E> {

    /** A nested list that this implementation of ICellGrid wraps. **/
    protected final List<List<E>> arrayGrid;

    /**
       Constructs an ArrayCellGrid with length xLength in x (columns), length yLength
       in y (rows) and fills all of the cells with the value given by defaultValue.

       @param xLength the length in x of the grid
       @param yLength the length in y of the grid
       @param defaultValue the value to set all of the initial values in the grid to
       @throws IllegalArgumentException if xLength or yLength are less than 1
     */
    public ArrayListCellGrid( final int xLength, final int yLength, final E defaultValue ) throws IllegalArgumentException {

	super(xLength, yLength);
	arrayGrid = new ArrayList<List<E>>();
	for ( int i = 0; i < yLength; i++ ){
	    arrayGrid.add(new ArrayList<E>(Collections.nCopies(xLength, defaultValue))
	    );
	}
    }

    @Override
    public List<CellCoordinate> getCoordsOfMooreNeighbours( final CellCoordinate coord )
	throws IllegalArgumentException {

	if ( !isValidCoord(coord) ){
	    throw new IllegalArgumentException("Coordinate not in the grid!");
	}
	List<CellCoordinate> adjCoords = new LinkedList<CellCoordinate>();
	for ( int i = coord.y - 1; i < coord.y + 2; i++ ){
	    for ( int j = coord.x - 1; j < coord.x + 2; j++ ){
		CellCoordinate newCoord = new CellCoordinate(j, i);
		// Check the coordinate is valid and not the input coordinate
		if ( isValidCoord(newCoord) && !newCoord.equals(coord) )
		    adjCoords.add(newCoord);
	    }
	}
	return adjCoords;
    }

    @Override    
    public List<CellCoordinate> getCoordsOfNeumannNeighbours( final CellCoordinate coord ) {

	if ( ! isValidCoord(coord) ){
	    throw new IllegalArgumentException("Coordinate not in the grid!");
	}
	List<CellCoordinate> adjCoords = new LinkedList<CellCoordinate>();
	for ( int i = coord.y - 1; i < coord.y + 2; i++ ){
	    for ( int j = coord.x - 1; j < coord.x + 2; j++ ){
		CellCoordinate newCoord = new CellCoordinate(j, i);
		// Check the coordinate is valid and not the input coordinate
		if ( (Math.abs(newCoord.x - coord.x) == 1 ^
		     Math.abs(newCoord.y - coord.y) == 1) &&
		     isValidCoord(newCoord) )
		    adjCoords.add(newCoord);
	    }
	}
	return adjCoords;
    }

    @Override
    public List<CellCoordinate> getCoordsOfCellsEqualTo( final E value ) {

	List<CellCoordinate> equalValues = new LinkedList<CellCoordinate>();
	for ( int i = 0; i < yLength; i++ ){
	    for ( int j = 0; j < xLength; j++ ){
		if ( arrayGrid.get(i).get(j).equals(value) )
		    equalValues.add(new CellCoordinate(j, i));
	    }
	}
	return equalValues;
    }
    
    @Override
    public E getValueAt( final CellCoordinate coord ) {

	if ( ! isValidCoord(coord) ){
	    throw new IllegalArgumentException("Coordinate not in the grid!");
	}	
	return arrayGrid.get(coord.y).get(coord.x);
    }

    @Override
    public void setValueAt( final CellCoordinate coord, final E value ) {

	if ( ! isValidCoord(coord) ){
	    throw new IllegalArgumentException("Coordinate not in the grid!");
	}
	// do a null check?
	arrayGrid.get(coord.y).set(coord.x, value);
    }

    /**
       Compares the specified object with this object for equality. Returns true
       if and only if the specified object is also an ArrayListCellGrid, both 
       grids have the same dimensions, and all corresponding pairs of elements in
       the two grids are equal.
     */
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
	ArrayListCellGrid otherGrid = (ArrayListCellGrid) o;
	int otherXLength = otherGrid.getXLength();
	int otherYLength = otherGrid.getYLength();
	if ( getXLength() != otherXLength || getYLength() != otherYLength ){
	    return false;
	}
	for ( int i = 0; i < otherYLength; i++ ){
	    for ( int j = 0; j < otherXLength; j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		if ( !otherGrid.getValueAt(coord).equals(getValueAt(coord)) ){
		    return false;
		}
	    }
	}
	return true;
    }

    /**
       Returns the hash code value for this ArrayListCellGrid. Returns the 
       hashcode of the internal ArrayList this class wraps.
     */
    @Override
    public int hashCode() {

	return arrayGrid.hashCode();
    }

    @Override
    public String toString() {

	StringBuilder stringBuilder = new StringBuilder();
	for ( int i = 0; i < getYLength(); i++ ){
	    stringBuilder.append("\n{");
	    for ( int j = 0; j < getXLength(); j++ ) {
		stringBuilder.append(getValueAt(new CellCoordinate(j, i)));
		stringBuilder.append(", ");
	    }
	    stringBuilder.append("}");
	}
	stringBuilder.append("\n");
	return stringBuilder.toString();
    }
}
