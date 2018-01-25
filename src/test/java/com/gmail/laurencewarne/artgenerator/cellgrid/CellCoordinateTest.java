package com.gmail.laurencewarne.artgenerator.cellgrid;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellCoordinateTest {

    @Test
    public void testCellCoordinateEquals() {

	CellCoordinate c1 = new CellCoordinate(0, 0);
	CellCoordinate c2 = new CellCoordinate(0, 1);
	CellCoordinate c3 = new CellCoordinate(-1, 0);
	CellCoordinate c4 = new CellCoordinate(0, 0);
	CellCoordinate c5 = new CellCoordinate(1, 1);
	CellCoordinate c6 = new CellCoordinate(1, 1);
	assertEquals(c1, c1);
	assertEquals(c1, c4);
	assertEquals(c5, c6);
	assertNotEquals(c1, c2);
	assertNotEquals(c1, c3);
	assertNotEquals(c2, c3);
	assertNotEquals(c1, "A string!");
    }
}
