package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.spritecreation.EnumSpriteOutlineGenerator;


public class DrawAroundDecoratorTest {

    private EnumSpriteOutlineGenerator e1, e2, e3;
    private final static EnumSpriteOutlineGenerator.CellState ALWAYS_EMPTY =
	EnumSpriteOutlineGenerator.CellState.ALWAYS_EMPTY;
    private final static EnumSpriteOutlineGenerator.CellState ALWAYS_FILLED =
	EnumSpriteOutlineGenerator.CellState.ALWAYS_FILLED;
    private final static EnumSpriteOutlineGenerator.CellState VARIED =
	EnumSpriteOutlineGenerator.CellState.VARIED;    
    ICellGrid<EnumSpriteOutlineGenerator.CellState> grid1, grid2, grid3, grid4;

    @Before
    public void setUp() {

	grid1 = new ArrayListCellGrid<>(10, 10, VARIED);
	grid2 = new ArrayListCellGrid<>(20, 10, ALWAYS_EMPTY);
	grid3 = new ArrayListCellGrid<>(5, 5, ALWAYS_EMPTY);
	grid4 = new ArrayListCellGrid<>(5, 10, ALWAYS_FILLED);
	e1 = new EnumSpriteOutlineGenerator(grid3, 34L);
	e2 = new EnumSpriteOutlineGenerator(grid2, 435L);
	e3 = new EnumSpriteOutlineGenerator(grid4, 342L);
    }

    @Test
    public void testAloneFilledCellIsDrawnAroundCorrectly() {

	e1.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e1);
	boolean[][] drawnAroundGrid = gen1.genSpriteOutline();
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
	for ( int i = 0; i < drawnAroundGrid.length; i++ ){
	    for ( int j = 0; j < drawnAroundGrid[0].length; j++ ){
		if ( filledCells.contains(new CellCoordinate(j, i)) ){
		    assertTrue(drawnAroundGrid[i][j]);
		}
		else {
		    assertFalse(drawnAroundGrid[i][j]);
		}
	    }
	}
    }

    @Test
    public void testCornerCellisDrawnAroundCorrectly() {

	e1.setCellStateAt(new CellCoordinate(0, 0), ALWAYS_FILLED);
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e1);	
	boolean[][] drawnAroundGrid = gen1.genSpriteOutline();
	List<CellCoordinate> filledCells =
	    Arrays.asList(
			  new CellCoordinate(1, 0),
			  new CellCoordinate(0, 1),
			  new CellCoordinate(1, 1)
			  );
	for ( int i = 0; i < drawnAroundGrid.length; i++ ){
	    for ( int j = 0; j < drawnAroundGrid[0].length; j++ ){
		if ( filledCells.contains(new CellCoordinate(j, i)) ){
		    assertTrue(drawnAroundGrid[i][j]);
		}
		else {
		    assertFalse(drawnAroundGrid[i][j]);
		}
	    }
	}	
    }

    @Test
    public void testAlwaysFullCoordWorksForLoneFilledCell() {

	e1.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e1);
	gen1.addAlwaysFullCoord(new CellCoordinate(1, 1));
	boolean[][] drawnAroundGrid = gen1.genSpriteOutline();
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
	for ( int i = 0; i < drawnAroundGrid.length; i++ ){
	    for ( int j = 0; j < drawnAroundGrid[0].length; j++ ){
		if ( filledCells.contains(new CellCoordinate(j, i)) ){
		    assertTrue(drawnAroundGrid[i][j]);
		}
		else {
		    assertFalse(drawnAroundGrid[i][j]);
		}
	    }
	}	
    }

    @Test
    public void testRemoveAlwaysFullCoordinate() {

	DrawAroundDecorator gen1 = new DrawAroundDecorator(e1);
	gen1.addAlwaysFullCoord(new CellCoordinate(1, 1));
	gen1.removeAlwaysFullCoord(new CellCoordinate(1, 1));
	boolean[][] drawnAroundGrid = gen1.genSpriteOutline();
	for ( int i = 0; i < drawnAroundGrid.length; i++ ){
	    for ( int j = 0; j < drawnAroundGrid[0].length; j++ ){
		assertFalse(drawnAroundGrid[i][j]);
	    }
	}	
    }
    
    @Test
    public void testAdjacentFilledCellsAreDrawnAroundCorrectly() {

	e1.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	e1.setCellStateAt(new CellCoordinate(2, 1), ALWAYS_FILLED);	
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e1);
	boolean[][] drawnAroundGrid = gen1.genSpriteOutline();
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
	
	for ( int i = 0; i < drawnAroundGrid.length; i++ ){
	    for ( int j = 0; j < drawnAroundGrid[0].length; j++ ){
		if ( filledCells.contains(new CellCoordinate(j, i)) ){
		    assertTrue(drawnAroundGrid[i][j]);
		}
		else {
		    assertFalse(drawnAroundGrid[i][j]);
		}
	    }
	}
    }

    @Test
    public void testTwoNonAdjacentFilledCellsAreDrawnAroundCorrectly() {


	e2.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	e2.setCellStateAt(new CellCoordinate(13, 8), ALWAYS_FILLED);	
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e2);
	boolean[][] drawnAroundGrid = gen1.genSpriteOutline();
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
	for ( int i = 0; i < drawnAroundGrid.length; i++ ){
	    for ( int j = 0; j < drawnAroundGrid[0].length; j++ ){
		if ( filledCells.contains(new CellCoordinate(j, i)) ){
		    assertTrue(drawnAroundGrid[i][j]);
		}
		else {
		    assertFalse(drawnAroundGrid[i][j]);
		}
	    }
	}
    }

    @Test
    public void testCompletelyFilledGridGivesEmptyGridWhenDrawnAround() {

	DrawAroundDecorator gen1 = new DrawAroundDecorator(e3);
	boolean[][] drawnAroundGrid = gen1.genSpriteOutline();
	for ( int i = 0; i < drawnAroundGrid.length; i++ ){
	    for ( int j = 0; j < drawnAroundGrid[0].length; j++ ){
		assertFalse(drawnAroundGrid[i][j]);
	    }
	}
    }

    @Test
    public void testLineOfFilledCellsGivesTwoLinesOfFilledCellsWhenDrawnAround() {

	for ( int i = 0; i < 10; i++ ){
	    e2.setCellStateAt(new CellCoordinate(10, i), ALWAYS_FILLED);
	}
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e2);
	boolean[][] drawnAroundGrid = gen1.genSpriteOutline();
	for ( int i = 0; i < drawnAroundGrid.length; i++ ){
	    for ( int j = 0; j < drawnAroundGrid[0].length; j++ ){
		if ( j == 9 || j == 11 ){
		    assertTrue(drawnAroundGrid[i][j]);
		}
		else {
		    assertFalse(drawnAroundGrid[i][j]);
		}
	    }
	}
    }

    @Test
    public void testLoneEmptyCellGivesLoneFilledCellWhenDrawnAround() {

	DrawAroundDecorator gen1 = new DrawAroundDecorator(e3);
	e3.setCellStateAt(new CellCoordinate(3, 3), ALWAYS_EMPTY);
	boolean[][] drawnAroundGrid = gen1.genSpriteOutline();
	for ( int i = 0; i < drawnAroundGrid.length; i++ ){
	    for ( int j = 0; j < drawnAroundGrid[0].length; j++ ){
		if ( i == 3 && j == 3 ){
		    assertTrue(drawnAroundGrid[i][j]);
		}
		else {
		    assertFalse(drawnAroundGrid[i][j]);
		}
	    }
	}
	
    }
}
