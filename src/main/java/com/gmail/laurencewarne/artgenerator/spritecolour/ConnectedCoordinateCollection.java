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

/**
This class locates runs of either false or true cells in an ICellGrid using a bfs. A
cell is classed as "connected" to another cell if it adjacent (N, E, S or W - Von 
Nuemann neighbourhood) to said cell, or adjacent to a cell which is in turn 
"connected" to said cell (recursive definition).
 */
public class ConnectedCoordinateCollection implements Iterable<Set<CellCoordinate>> {

    /** 
	Input ICellGrid with true cells representing "filled" values and false
	cells representing "empty" values.
    */
    protected final ICellGrid<Boolean> uncolouredSpriteGrid;
    protected final List<Set<CellCoordinate>> connectedCoordinateSets;
    protected final boolean connectTrueValues;

    /**
       Constructs an instance using an ICellGrid representing an uncoloured sprite,
       and boolean value specifying whether the instance should store connected
       true values or connected false values.
       
       @param uncolouredSpriteGrid ICellGrid representing the "uncoloured" sprite
       @param connectTrueValues boolean value controlling whether true or false
       values are matched
     */
    public ConnectedCoordinateCollection( final ICellGrid<Boolean> uncolouredSpriteGrid, final boolean connectTrueValues ) {

	this.uncolouredSpriteGrid = uncolouredSpriteGrid;
	this.connectTrueValues = connectTrueValues;
	connectedCoordinateSets = new ArrayList<>();
	
	// Now we extract the connected coordinates from the grid using a bfs
	List<CellCoordinate> matchedCells = uncolouredSpriteGrid.
	    getCoordsOfCellsEqualTo(connectTrueValues);
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
		if ( uncolouredSpriteGrid.getValueAt(connectedCell) == connectTrueValues &&
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

    /**
       Returns a set of all the coordinates of cells "connected" to a specified
       cell, not including the cell itself. An unmodifiable set is returned.

       @param coord the coordinate of the cell whose connected cells are to be returned
       @return set of all coordinates connected to the specified cell
       @throws IllegalArgumentException if no connected sets were found that contain
       the specified coordinate
     */
    public Set<CellCoordinate> getValidCellsConnectedTo( final CellCoordinate coord )  throws IllegalArgumentException {

	for ( Set<CellCoordinate> coordSet : connectedCoordinateSets ){
	    if ( coordSet.contains(coord) ){
		Set<CellCoordinate> copy = new HashSet<>(coordSet);
		copy.remove(coord);
		return Collections.unmodifiableSet(copy);
	    }
	}
	throw new IllegalArgumentException("Coordinate not found.");
    }

    /**
       Returns true if the specified cell is connected to a cell which is on the 
       edge of the grid.

       @param  coord the coordinate of the cell to check
       @return true if the cell is connected to an edge, false otherwise
       @throws IllegalArgumentException if the coordinate does not point to a 
       boolean value which this instance is connecting (e.g. it points to a false
       value when in fact the instance is connecting true values), also thrown
       if the coordinate does not lie on the grid
     */
    public boolean isCellConnectedToEdge( final CellCoordinate coord ) {

	Set<CellCoordinate> containingSet = null;
	for ( Set<CellCoordinate> coordSet : connectedCoordinateSets ){
	    if ( coordSet.contains(coord) ){
		containingSet = coordSet;
	    }
	}
	if ( containingSet == null ){
	    throw new IllegalArgumentException("Coordinates do not point to valid cell");
	}
	for ( CellCoordinate connectedCoord : containingSet ){
	    if ( isCoordOnEdge(connectedCoord) ){
		return true;
	    }
	}
	return false;
    }

    /**
       Returns true if the coordinate is on the edge of the grid, false otherwise.

       @param  coord the coordinate to be checked
       @return true if the coordinate is on the edge of the grid, false otherwise
     */
    public boolean isCoordOnEdge( final CellCoordinate coord ) {

	int xLength = uncolouredSpriteGrid.getXLength();
	int yLength = uncolouredSpriteGrid.getYLength();
	if ( coord.x == 0 || coord.x == xLength ||
	     coord.y == 0 || coord.y == yLength ){
	    return true;
	}
	else {
	    return false;
	}
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
