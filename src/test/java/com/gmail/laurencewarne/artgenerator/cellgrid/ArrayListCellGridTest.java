package com.gmail.laurencewarne.artgenerator.cellgrid;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ArrayListCellGridTest {

    private ArrayListCellGrid<Integer> intCellGrid1, intCellGrid2;

    @Before
    public void setUp() {

	intCellGrid1 = new ArrayListCellGrid<Integer>(10, 10, 0);
	intCellGrid2 = new ArrayListCellGrid<Integer>(1, 1, 0);
    }

    @Test
    public void testAllElementsAreDefault() {

	for ( int i = 0; i < intCellGrid1.getYLength(); i++ ){
	    for ( int j = 0; j < intCellGrid1.getXLength(); j++ ){
		CellCoordinate coord = new CellCoordinate(i, j);
		assertEquals(intCellGrid1.getValueAt(coord), Integer.valueOf(0));
	    }
	}
    }

    @Test
    public void testMooreNeighboursWithCentralCoord() {

	CellCoordinate centre = new CellCoordinate(1, 1);
	List<CellCoordinate> neighbours = intCellGrid1
	    .getCoordsOfMooreNeighbours(centre);
	assertEquals(neighbours.size(), 8);
	for ( CellCoordinate coord : neighbours ){
	    assertTrue(Math.abs(centre.x - coord.x) == 1 ||
		       Math.abs(centre.x - coord.x) == 0
	    );
	    assertTrue(Math.abs(centre.y - coord.y) == 1 ||
		       Math.abs(centre.y - coord.y) == 0
	    );
	}
    }


    @Test
    public void testMooreNeighboursWithCornerCoord() {

	CellCoordinate centre = new CellCoordinate(0, 0);
	List<CellCoordinate> neighbours = intCellGrid1
	    .getCoordsOfMooreNeighbours(centre);
	assertEquals(neighbours.size(), 3);
	for ( CellCoordinate coord : neighbours ){
	    assertTrue(Math.abs(centre.x - coord.x) == 1 ||
		       Math.abs(centre.x - coord.x) == 0
	    );
	    assertTrue(Math.abs(centre.y - coord.y) == 1 ||
		       Math.abs(centre.y - coord.y) == 0
	    );
	}
	List<CellCoordinate> neighbours2 = intCellGrid2
	    .getCoordsOfMooreNeighbours(centre);
	assertEquals(neighbours2.size(), 0);
    }

    @Test
    public void testNuemannNeighboursWithCentralCoord() {

	CellCoordinate centre = new CellCoordinate(1, 1);
	List<CellCoordinate> neighbours = intCellGrid1
	    .getCoordsOfNeumannNeighbours(centre);
	assertEquals(neighbours.size(), 4);
	for ( CellCoordinate coord : neighbours ){
	    assertTrue(Math.abs(centre.x - coord.x) == 1 ||
		       Math.abs(centre.x - coord.x) == 0
	    );
	    assertTrue(Math.abs(centre.y - coord.y) == 1 ||
		       Math.abs(centre.y - coord.y) == 0
	    );
	}
    }

    @Test
    public void testNuemannNeighboursWithCornerCoord() {

	CellCoordinate centre = new CellCoordinate(0, 0);
	List<CellCoordinate> neighbours = intCellGrid1
	    .getCoordsOfNeumannNeighbours(centre);
	assertEquals(neighbours.size(), 2);
	for ( CellCoordinate coord : neighbours ){
	    assertTrue(Math.abs(centre.x - coord.x) == 1 ||
		       Math.abs(centre.x - coord.x) == 0
	    );
	    assertTrue(Math.abs(centre.y - coord.y) == 1 ||
		       Math.abs(centre.y - coord.y) == 0
	    );
	}
	List<CellCoordinate> neighbours2 = intCellGrid2
	    .getCoordsOfNeumannNeighbours(centre);
	assertEquals(neighbours2.size(), 0);
    }

    @Test
    public void testGetCoordsOfCellsEqualToReturnsDefault() {

	List<CellCoordinate> equalCellCoords =
	    intCellGrid1.getCoordsOfCellsEqualTo(0);
	assertEquals(equalCellCoords.size(), 100);
	for ( CellCoordinate coord : equalCellCoords ){
	    assertEquals(intCellGrid1.getValueAt(coord), Integer.valueOf(0));
	}
	List<CellCoordinate> equalCellCoords2 =
	    intCellGrid1.getCoordsOfCellsEqualTo(1);
	assertEquals(equalCellCoords2.size(), 0);
    }

    @Test
    public void testGetCoordsOfCellsEqualToWorksWithChangedCells() {

	intCellGrid1.setValueAt(new CellCoordinate(0, 0), 1);
	intCellGrid1.setValueAt(new CellCoordinate(1, 1), 1);
	intCellGrid1.setValueAt(new CellCoordinate(2, 2), 1);
	intCellGrid1.setValueAt(new CellCoordinate(3, 3), 1);
	List<CellCoordinate> equalCellCoords =
	    intCellGrid1.getCoordsOfCellsEqualTo(1);
	assertEquals(equalCellCoords.size(), 4);
	List<CellCoordinate> equalCellCoords2 =
	    intCellGrid1.getCoordsOfCellsEqualTo(0);
	assertEquals(equalCellCoords2.size(), 96);
    }
}
