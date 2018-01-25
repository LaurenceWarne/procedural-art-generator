package com.gmail.laurencewarne.artgenerator.spritecolour;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


public class ConnectedCoordinateCollection implements Iterable<Set<CellCoordinate>> {

    private final ICellGrid<Boolean> uncolouredSpriteGrid;
    private final List<Set<CellCoordinate>> connectedCoordinateSets;

    public ConnectedCoordinateCollection( final ICellGrid<Boolean> uncolouredSpriteGrid ) {

	this.uncolouredSpriteGrid = uncolouredSpriteGrid;
	connectedCoordinateSets = new ArrayList<>();
	
	// Now we extract the connected coordinates from the grid using a bfs
	List<CellCoordinate> trueCells = uncolouredSpriteGrid.
	    getCoordsOfCellsEqualTo(true);
	while ( !trueCells.isEmpty() ){
	    // Each iteration of this loop creates a new set
	    CellCoordinate trueCell = trueCells.remove(0);
	    Set<CellCoordinate> connectedSet = new HashSet<>(Arrays.asList(trueCell));
	    LinkedList<CellCoordinate> connectedCells = new LinkedList<>
		(uncolouredSpriteGrid.getCoordsOfNeumannNeighbours(trueCell));
	    while ( !connectedCells.isEmpty() ) {
		// Each iteration of this loop can add an element to the newly
		// created set
		CellCoordinate connectedCell = connectedCells.remove();
		if ( uncolouredSpriteGrid.getValueAt(connectedCell) == true &&
		       !connectedSet.contains(connectedCell) ) {
		    connectedSet.add(connectedCell);
		    connectedCells.addAll(uncolouredSpriteGrid.
					  getCoordsOfNeumannNeighbours(connectedCell));
		}
	    }
	    trueCells.removeAll(connectedSet);
	    connectedCoordinateSets.add(connectedSet);
	}
    }

    public Set<CellCoordinate> getCellsConnectedTo( final CellCoordinate coord )
	throws IllegalArgumentException {

	// return an immutable set
	return null;
    }

    public boolean isCellOnEdge( final CellCoordinate coord ) {

	return false;
    }

    @Override
    public Iterator<Set<CellCoordinate>> iterator() {

	// need anonymous class!
	return connectedCoordinateSets.iterator();
    }
}
