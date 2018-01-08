package com.gmail.laurencewarne.artgenerator.cellgrid;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;


public class ArrayListCellGrid<E> extends AbstractCellGrid<E> implements ICellGrid<E> {

    private List<List<E>> arrayGrid;

    public ArrayListCellGrid( final int xLength, final int yLength, E defaultValue ) {

	super(xLength, yLength);
	arrayGrid = new ArrayList<List<E>>();
	for ( int i = 0; i < yLength; i++ ){
	    arrayGrid.add(new ArrayList<E>(Collections.nCopies(xLength, defaultValue))
	    );
	}
    }

    @Override
    public List<CellCoordinate> getCoordsOfMooreNeighbours( CellCoordinate coord )
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
    public List<CellCoordinate> getCoordsOfNeumannNeighbours( CellCoordinate coord ) {

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
    public List<CellCoordinate> getCoordsOfCellsEqualTo( E value ) {

	List<CellCoordinate> equalValues = new LinkedList<CellCoordinate>();
	for ( int i = 0; i < yLength; i++ ){
	    for ( int j = 0; j < xLength; j++ ){
		if ( arrayGrid.get(i).get(j).equals(value) )
		    equalValues.add(new CellCoordinate(i, j));
	    }
	}
	return equalValues;
    }
    
    @Override
    public E getValueAt( CellCoordinate coord ) {

	if ( ! isValidCoord(coord) ){
	    throw new IllegalArgumentException("Coordinate not in the grid!");
	}	
	return arrayGrid.get(coord.y).get(coord.x);
    }

    @Override
    public void setValueAt( CellCoordinate coord, E value ) {

	if ( ! isValidCoord(coord) ){
	    throw new IllegalArgumentException("Coordinate not in the grid!");
	}
	// do a null check?
	arrayGrid.get(coord.y).set(coord.x, value);
    }
}
