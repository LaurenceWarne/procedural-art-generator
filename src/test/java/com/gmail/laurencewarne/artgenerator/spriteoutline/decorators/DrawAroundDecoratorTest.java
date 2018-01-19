package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.spriteoutline.EnumSpriteOutlineGenerator;


public class DrawAroundDecoratorTest {

    private EnumSpriteOutlineGenerator e;
    private final static EnumSpriteOutlineGenerator.CellState ALWAYS_EMPTY =
	EnumSpriteOutlineGenerator.CellState.ALWAYS_EMPTY;
    private final static EnumSpriteOutlineGenerator.CellState ALWAYS_FILLED =
	EnumSpriteOutlineGenerator.CellState.ALWAYS_FILLED;
    private final static EnumSpriteOutlineGenerator.CellState VARIED =
	EnumSpriteOutlineGenerator.CellState.VARIED;    
    ICellGrid<EnumSpriteOutlineGenerator.CellState> grid1, grid2, grid3;

    @Before
    public void setUp() {

	grid1 = new ArrayListCellGrid<>(10, 10, VARIED);
	grid2 = new ArrayListCellGrid<>(20, 10, ALWAYS_FILLED);
	grid3 = new ArrayListCellGrid<>(5, 5, ALWAYS_EMPTY);
	e = new EnumSpriteOutlineGenerator(grid3, 34L);
    }

    @Test
    public void testAloneFilledCellIsDrawnAroundCorrectly() {

	e.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e);
	boolean[][] drawnAroundGrid = gen1.genSpriteOutline();
	System.out.println(Arrays.deepToString(drawnAroundGrid));
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

	e.setCellStateAt(new CellCoordinate(0, 0), ALWAYS_FILLED);
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e);	
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

	e.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e);
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

	DrawAroundDecorator gen1 = new DrawAroundDecorator(e);
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
    public void testAdjacentFilledCoordinatesAreDrawnAroundCorrectly() {

	e.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_FILLED);
	e.setCellStateAt(new CellCoordinate(2, 1), ALWAYS_FILLED);	
	DrawAroundDecorator gen1 = new DrawAroundDecorator(e);
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
}
