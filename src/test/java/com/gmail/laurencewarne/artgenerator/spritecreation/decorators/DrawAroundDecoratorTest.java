package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.spritecreation.EnumSpriteGenerator;


public class DrawAroundDecoratorTest {

    private EnumSpriteGenerator e1, e2, e3;
    private final static EnumSpriteGenerator.CellState ALWAYS_EMPTY =
	EnumSpriteGenerator.CellState.ALWAYS_EMPTY;
    private final static EnumSpriteGenerator.CellState ALWAYS_FILLED =
	EnumSpriteGenerator.CellState.ALWAYS_FILLED;
    private final static EnumSpriteGenerator.CellState VARIED =
	EnumSpriteGenerator.CellState.VARIED;
    ICellGrid<EnumSpriteGenerator.CellState> grid1, grid2, grid3, grid4;

    @Before
    public void setUp() {

	grid1 = new ArrayListCellGrid<>(10, 10, VARIED);
	grid2 = new ArrayListCellGrid<>(20, 10, ALWAYS_EMPTY);
	grid3 = new ArrayListCellGrid<>(5, 5, ALWAYS_EMPTY);
	grid4 = new ArrayListCellGrid<>(5, 10, ALWAYS_FILLED);
	e1 = new EnumSpriteGenerator(grid3, 34L);
	e2 = new EnumSpriteGenerator(grid2, 435L);
	e3 = new EnumSpriteGenerator(grid4, 342L);
    }

    @Test
    public void testAloneFilledCellIsDrawnAroundCorrectly() {

	e1.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e1);
	ICellGrid<Boolean> drawnAroundGrid = gen1.genSpriteAsCellGrid();
	List<CellCoordinate> filledCells =
	    Arrays.asList(
			  new CellCoordinate(0, 0),
			  new CellCoordinate(1, 0),
			  new CellCoordinate(2, 0),
			  new CellCoordinate(0, 1),
			  new CellCoordinate(1, 2),
			  new CellCoordinate(2, 1),
			  new CellCoordinate(2, 2),
			  new CellCoordinate(0, 2)
			  );
	for ( int i = 0; i < drawnAroundGrid.getYLength(); i++ ){
	    for ( int j = 0; j < drawnAroundGrid.getXLength(); j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		if ( filledCells.contains(coord) ){
		    assertTrue(drawnAroundGrid.getValueAt(coord));
		}
		else {
		    assertFalse(drawnAroundGrid.getValueAt(coord));
		}
	    }
	}
    }

    @Test
    public void testCornerCellisDrawnAroundCorrectly() {

	e1.setCellStateAt(new CellCoordinate(0, 0), ALWAYS_FILLED);
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e1);	
	ICellGrid<Boolean> drawnAroundGrid = gen1.genSpriteAsCellGrid();
	List<CellCoordinate> filledCells =
	    Arrays.asList(
			  new CellCoordinate(1, 0),
			  new CellCoordinate(0, 1),
			  new CellCoordinate(1, 1)
			  );
	for ( int i = 0; i < drawnAroundGrid.getYLength(); i++ ){
	    for ( int j = 0; j < drawnAroundGrid.getXLength(); j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		if ( filledCells.contains(coord) ){
		    assertTrue(drawnAroundGrid.getValueAt(coord));
		}
		else {
		    assertFalse(drawnAroundGrid.getValueAt(coord));
		}
	    }
	}
    }

    @Test
    public void testAlwaysFullCoordWorksForLoneFilledCell() {

	e1.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e1);
	gen1.addAlwaysFullCoord(new CellCoordinate(1, 1));
	ICellGrid<Boolean> drawnAroundGrid = gen1.genSpriteAsCellGrid();
	List<CellCoordinate> filledCells =
	    Arrays.asList(
			  new CellCoordinate(1, 1),
			  new CellCoordinate(0, 0),
			  new CellCoordinate(1, 0),
			  new CellCoordinate(2, 0),
			  new CellCoordinate(0, 1),
			  new CellCoordinate(1, 2),
			  new CellCoordinate(2, 1),
			  new CellCoordinate(2, 2),
			  new CellCoordinate(0, 2)
			  );
	for ( int i = 0; i < drawnAroundGrid.getYLength(); i++ ){
	    for ( int j = 0; j < drawnAroundGrid.getXLength(); j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		if ( filledCells.contains(coord) ){
		    assertTrue(drawnAroundGrid.getValueAt(coord));
		}
		else {
		    assertFalse(drawnAroundGrid.getValueAt(coord));
		}
	    }
	}	
    }

    @Test
    public void testRemoveAlwaysFullCoordinate() {

	DrawAroundDecorator gen1 = new DrawAroundDecorator(e1);
	gen1.addAlwaysFullCoord(new CellCoordinate(1, 1));
	gen1.removeAlwaysFullCoord(new CellCoordinate(1, 1));
	ICellGrid<Boolean> drawnAroundGrid = gen1.genSpriteAsCellGrid();
	for ( int i = 0; i < drawnAroundGrid.getYLength(); i++ ){
	    for ( int j = 0; j < drawnAroundGrid.getXLength(); j++ ){
		assertFalse(drawnAroundGrid.getValueAt(new CellCoordinate(j, i)));
	    }
	}	
    }

    @Test
    public void testAdjacentFilledCellsAreDrawnAroundCorrectly() {

	e1.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	e1.setCellStateAt(new CellCoordinate(2, 1), ALWAYS_FILLED);	
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e1);
	ICellGrid<Boolean> drawnAroundGrid = gen1.genSpriteAsCellGrid();
	List<CellCoordinate> filledCells =
	    Arrays.asList(
			  new CellCoordinate(0, 0),
			  new CellCoordinate(1, 0),
			  new CellCoordinate(2, 0),
			  new CellCoordinate(3, 0),			
			  new CellCoordinate(0, 1),
			  new CellCoordinate(1, 2),
			  new CellCoordinate(2, 2),
			  new CellCoordinate(0, 2),
			  new CellCoordinate(3, 2),
			  new CellCoordinate(3, 1)
			  );
	
	for ( int i = 0; i < drawnAroundGrid.getYLength(); i++ ){
	    for ( int j = 0; j < drawnAroundGrid.getXLength(); j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		if ( filledCells.contains(coord) ){
		    assertTrue(drawnAroundGrid.getValueAt(coord));
		}
		else {
		    assertFalse(drawnAroundGrid.getValueAt(coord));
		}
	    }
	}
    }

    @Test
    public void testTwoNonAdjacentFilledCellsAreDrawnAroundCorrectly() {


	e2.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	e2.setCellStateAt(new CellCoordinate(13, 8), ALWAYS_FILLED);	
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e2);
	ICellGrid<Boolean> drawnAroundGrid = gen1.genSpriteAsCellGrid();
	List<CellCoordinate> filledCells =
	    Arrays.asList(
			  new CellCoordinate(0, 0),
			  new CellCoordinate(1, 0),
			  new CellCoordinate(2, 0),
			  new CellCoordinate(0, 1),
			  new CellCoordinate(1, 2),
			  new CellCoordinate(2, 2),
			  new CellCoordinate(0, 2),
			  new CellCoordinate(2, 1),
			  //-----------------------
			  new CellCoordinate(12, 7),
			  new CellCoordinate(12, 8),
			  new CellCoordinate(12, 9),
			  new CellCoordinate(13, 7),
			  new CellCoordinate(13, 9),
			  new CellCoordinate(14, 7),
			  new CellCoordinate(14, 8),
			  new CellCoordinate(14, 9)
			  );
	for ( int i = 0; i < drawnAroundGrid.getYLength(); i++ ){
	    for ( int j = 0; j < drawnAroundGrid.getXLength(); j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		if ( filledCells.contains(coord) ){
		    assertTrue(drawnAroundGrid.getValueAt(coord));
		}
		else {
		    assertFalse(drawnAroundGrid.getValueAt(coord));
		}
	    }
	}
    }

    @Test
    public void testCompletelyFilledGridGivesEmptyGridWhenDrawnAround() {

	DrawAroundDecorator gen1 = new DrawAroundDecorator(e3);
	ICellGrid<Boolean> drawnAroundGrid = gen1.genSpriteAsCellGrid();
	for ( int i = 0; i < drawnAroundGrid.getYLength(); i++ ){
	    for ( int j = 0; j < drawnAroundGrid.getXLength(); j++ ){
		assertFalse(drawnAroundGrid.getValueAt(new CellCoordinate(j, i)));
	    }
	}
    }

    @Test
    public void testLineOfFilledCellsGivesTwoLinesOfFilledCellsWhenDrawnAround() {

	for ( int i = 0; i < 10; i++ ){
	    e2.setCellStateAt(new CellCoordinate(10, i), ALWAYS_FILLED);
	}
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e2);
	ICellGrid<Boolean> drawnAroundGrid = gen1.genSpriteAsCellGrid();
	for ( int i = 0; i < drawnAroundGrid.getYLength(); i++ ){
	    for ( int j = 0; j < drawnAroundGrid.getXLength(); j++ ){
		if ( j == 9 || j == 11 ){
		    assertTrue(drawnAroundGrid.getValueAt(new CellCoordinate(j, i)));
		}
		else {
		    assertFalse(drawnAroundGrid.getValueAt(new CellCoordinate(j, i)));
		}
	    }
	}
    }

    @Test
    public void testLoneEmptyCellGivesLoneFilledCellWhenDrawnAround() {

	DrawAroundDecorator gen1 = new DrawAroundDecorator(e3);
	e3.setCellStateAt(new CellCoordinate(3, 3), ALWAYS_EMPTY);
	ICellGrid<Boolean> drawnAroundGrid = gen1.genSpriteAsCellGrid();
	for ( int i = 0; i < drawnAroundGrid.getYLength(); i++ ){
	    for ( int j = 0; j < drawnAroundGrid.getXLength(); j++ ){
		if ( i == 3 && j == 3 ){
		    assertTrue(drawnAroundGrid.getValueAt(new CellCoordinate(j, i)));
		}
		else {
		    assertFalse(drawnAroundGrid.getValueAt(new CellCoordinate(j, i)));
		}
	    }
	}	
    }
}
