package com.gmail.laurencewarne.artgenerator.spritecolour;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


public class ConnectedCoordinateCollectionTest {

    private ICellGrid<Boolean> grid1, grid2, grid3, grid4;
    private ConnectedCoordinateCollection col1, col2, col3, col4;

    @Before
    public void setUp() {

	grid1 = new ArrayListCellGrid<>(10, 10, true);
	grid2 = new ArrayListCellGrid<>(20, 30, true);
	grid3 = new ArrayListCellGrid<>(10, 10, false);
	grid4 = new ArrayListCellGrid<>(20, 30, false);
	col1 = new ConnectedCoordinateCollection(grid1, true);
	col2 = new ConnectedCoordinateCollection(grid2, true);
	col3 = new ConnectedCoordinateCollection(grid3, true);
    }

    @Test
    public void testAllCellAreConnectedOnAllTrueGrid() {

	Iterator<Set<CellCoordinate>> iter = col1.iterator();
	Set<CellCoordinate> fstSet = iter.next();
	assertEquals(iter.hasNext(), false);
	for ( int i = 0; i < 10; i++ ){
	    for ( int j = 0; j < 10; j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		assertEquals(fstSet.contains(coord), true);
	    }
	}
    }

    @Test
    public void testNoCellsAreConnectedOnAllFalseGrid() {

	Iterator<Set<CellCoordinate>> iter = col3.iterator();
	assertEquals(iter.hasNext(), false);
    }

    @Test
    public void testLoneTrueCellIsOnlyCellInOnlySet() {

	grid3.setValueAt(new CellCoordinate(5, 5), true);
	col4 = new ConnectedCoordinateCollection(grid3, true);
	Iterator<Set<CellCoordinate>> iter = col4.iterator();
	Set<CellCoordinate> fstSet = iter.next();
	assertEquals(iter.hasNext(), false);
	assertEquals(fstSet.size(), 1);
	assertTrue(fstSet.contains(new CellCoordinate(5, 5)));
    }

    @Test
    public void testLoneFalseCellIsAbsentInOnlySet() {

	grid1.setValueAt(new CellCoordinate(5, 5), false);
	col4 = new ConnectedCoordinateCollection(grid1, true);
	Iterator<Set<CellCoordinate>> iter = col4.iterator();
	Set<CellCoordinate> fstSet = iter.next();
	assertEquals(iter.hasNext(), false);
	for ( int i = 0; i < 10; i++ ){
	    for ( int j = 0; j < 10; j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		if ( coord.equals(new CellCoordinate(5, 5)) ){
		    assertFalse(fstSet.contains(coord));
		}
		else {
		    assertTrue(fstSet.contains(coord));
		}
	    }
	}
    }

    @Test
    public void testVerticalLineOfFalseCellsGivesTwoSets() {

	for ( int i = 0; i < 30; i++ ){
	    grid2.setValueAt(new CellCoordinate(9, i), false);
	}
	col4 = new ConnectedCoordinateCollection(grid2, true);
	Iterator<Set<CellCoordinate>> iter = col4.iterator();
	Set<CellCoordinate> leftSet = iter.next();
	System.out.println(leftSet.size());
	Set<CellCoordinate> rightSet = iter.next();
	if ( rightSet.contains(new CellCoordinate(0, 0)) ){
	    Set<CellCoordinate> tmp = leftSet;
	    leftSet = rightSet; rightSet = tmp;
	}
	assertEquals(iter.hasNext(), false);
	for ( int i = 0; i < 30; i++ ){
	    for ( int j = 0; j < 20; j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		if ( j < 9 ){
		    assertTrue(leftSet.contains(coord));
		    assertFalse(rightSet.contains(coord));
		}
		else if ( j > 9 ){
		    assertFalse(leftSet.contains(coord));
		    assertTrue(rightSet.contains(coord));
		}
		else {
		    assertFalse(leftSet.contains(coord));
		    assertFalse(rightSet.contains(coord));
		}
	    }
	}
    }

    @Test
    public void testVerticalAndHorizontalLineOfFalseGivesFourSets() {

	for ( int i = 0; i < 30; i++ ){
	    grid2.setValueAt(new CellCoordinate(9, i), false);
	}
	for ( int j = 0; j < 20; j++ ){
	    grid2.setValueAt(new CellCoordinate(j, 14), false);
	}
	col4 = new ConnectedCoordinateCollection(grid2, true);
	Iterator<Set<CellCoordinate>> iter = col4.iterator();
	List<Set<CellCoordinate>> sets = Arrays.
	    asList(iter.next(), iter.next(), iter.next(), iter.next()
		   );
	assertEquals(iter.hasNext(), false);
	Set<CellCoordinate> topRight = null;
	Set<CellCoordinate> topLeft = null;
	Set<CellCoordinate> bottomLeft = null;
	Set<CellCoordinate> bottomRight = null;
	for ( Set<CellCoordinate> coordSet : sets ){
	    if ( coordSet.contains(new CellCoordinate(0, 0)) ){
		topLeft = coordSet;
	    }
	    else if ( coordSet.contains(new CellCoordinate(0, 29)) ){
		bottomLeft = coordSet;
	    }
	    else if ( coordSet.contains(new CellCoordinate(19, 0)) ){
		topRight = coordSet;
	    }
	    else {
		bottomRight = coordSet;
	    }
	}

	for ( int i = 0; i < 30; i++ ){
	    for ( int j = 0; j < 20; j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		if ( j < 9 && i < 14){
		    assertTrue(topLeft.contains(coord));
		    assertFalse(bottomLeft.contains(coord));	    
		    assertFalse(topRight.contains(coord));
		    assertFalse(bottomRight.contains(coord));
		}
		else if ( j > 9 && i < 14 ){
		    assertFalse(topLeft.contains(coord));
		    assertFalse(bottomLeft.contains(coord));
		    assertTrue(topRight.contains(coord));
		    assertFalse(bottomRight.contains(coord));

		}
		else if ( j < 9 && i > 14 ){
		    assertFalse(topLeft.contains(coord));
		    assertTrue(bottomLeft.contains(coord));
		    assertFalse(topRight.contains(coord));
		    assertFalse(bottomRight.contains(coord));
		    
		}
		else if ( j > 9 && i > 14 ){
		    assertFalse(topLeft.contains(coord));
		    assertFalse(bottomLeft.contains(coord));
		    assertFalse(topRight.contains(coord));
		    assertTrue(bottomRight.contains(coord));		    
		}
		else {
		    assertFalse(topLeft.contains(coord));
		    assertFalse(bottomLeft.contains(coord));
		    assertFalse(topRight.contains(coord));
		    assertFalse(bottomRight.contains(coord));
		}
	    }
	}
    }

    public void testTrueEdgeCellsGiveFourSets() {

	grid4.setValueAt(new CellCoordinate(0, 0), true);
	grid4.setValueAt(new CellCoordinate(0, 1), true);
	grid4.setValueAt(new CellCoordinate(1, 0), true);
	grid4.setValueAt(new CellCoordinate(19, 0), true);
	grid4.setValueAt(new CellCoordinate(19, 1), true);
	grid4.setValueAt(new CellCoordinate(18, 0), true);
	grid4.setValueAt(new CellCoordinate(1, 29), true);
	grid4.setValueAt(new CellCoordinate(0, 29), true);
	grid4.setValueAt(new CellCoordinate(0, 28), true);
	grid4.setValueAt(new CellCoordinate(19, 29), true);
	grid4.setValueAt(new CellCoordinate(18, 29), true);
	grid4.setValueAt(new CellCoordinate(19, 28), true);	

	Set<CellCoordinate> trueCells = new HashSet<>
	    (grid4.getCoordsOfCellsEqualTo(true));
	col4 = new ConnectedCoordinateCollection(grid4, true);
	Iterator<Set<CellCoordinate>> iter = col4.iterator();
	List<Set<CellCoordinate>> sets = Arrays.
	    asList(iter.next(), iter.next(), iter.next(), iter.next()
		   );
	assertEquals(iter.hasNext(), false);
	Set<CellCoordinate> amalgamOfRuns = new HashSet<>();
	for ( Set<CellCoordinate> set : sets ){
	    amalgamOfRuns.addAll(set);
	    assertEquals(set.size(), 3);
	    assertTrue(trueCells.containsAll(set));
	}
	// Assert the sets share no elements
	assertEquals(amalgamOfRuns.size(), 12);
	assertEquals(amalgamOfRuns, trueCells);
    }
}
