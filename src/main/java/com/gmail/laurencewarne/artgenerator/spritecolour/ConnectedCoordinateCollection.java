package com.gmail.laurencewarne.artgenerator.spritecolour;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


public class ConnectedCoordinateCollection implements Iterable<Set<CellCoordinate>> {

    private final ICellGrid<Boolean> uncolouredSpriteGrid;
    private final List<Set<CellCoordinate>> connectedCoordinateSets;
    private final boolean connectFalseValues;

    public ConnectedCoordinateCollection( final ICellGrid<Boolean> uncolouredSpriteGrid, final boolean connectFalseValues ) {

	this.uncolouredSpriteGrid = uncolouredSpriteGrid;
	this.connectFalseValues = connectFalseValues;
	connectedCoordinateSets = new ArrayList<>();
	
	// Now we extract the connected coordinates from the grid using a bfs
	List<CellCoordinate> matchedCells = uncolouredSpriteGrid.
	    getCoordsOfCellsEqualTo(connectFalseValues);
	while ( !matchedCells.isEmpty() ){
	    // Each iteration of this loop creates a new set
	    CellCoordinate matchedCell = matchedCells.remove(0);
	    Set<CellCoordinate> connectedSet = new HashSet<>(Arrays.asList(matchedCell));
	    LinkedList<CellCoordinate> connectedCells = new LinkedList<>
		(uncolouredSpriteGrid.getCoordsOfNeumannNeighbours(matchedCell));
	    while ( !connectedCells.isEmpty() ) {
		// Each iteration of this loop can add an element to the newly
		// created set
		CellCoordinate connectedCell = connectedCells.remove();
		if ( uncolouredSpriteGrid.getValueAt(connectedCell) == connectFalseValues &&
		       !connectedSet.contains(connectedCell) ) {
		    connectedSet.add(connectedCell);
		    connectedCells.addAll(uncolouredSpriteGrid.
					  getCoordsOfNeumannNeighbours(connectedCell));
		}
	    }
	    matchedCells.removeAll(connectedSet);
	    connectedCoordinateSets.add(connectedSet);
	}
    }

    public Set<CellCoordinate> getValidCellsConnectedTo( final CellCoordinate coord ) {

	for ( Set<CellCoordinate> coordSet : connectedCoordinateSets ){
	    if ( coordSet.contains(coord) ){
		Set<CellCoordinate> copy = new HashSet<>(coordSet);
		copy.remove(coord);
		return Collections.unmodifiableSet(copy);
	    }
	}
	throw new IllegalArgumentException("Coordinate not found.");
    }

    public boolean isCellConnectedToEdge( final CellCoordinate coord ) {

	return false;
    }

    @Override
    public Iterator<Set<CellCoordinate>> iterator() {

	// Anonymous class to prohibit removals
	return new Iterator<Set<CellCoordinate>>() {

	    private final Iterator<Set<CellCoordinate>>
		listIter = connectedCoordinateSets.iterator();

	    @Override
	    public boolean hasNext() {

		return listIter.hasNext();
	    }

	    @Override
	    public Set<CellCoordinate> next() {

		return listIter.next();
	    }

	    @Override
	    public void remove() {
		
		throw new UnsupportedOperationException("Removal not permitted");
	    }
	    
	};
    }
}
