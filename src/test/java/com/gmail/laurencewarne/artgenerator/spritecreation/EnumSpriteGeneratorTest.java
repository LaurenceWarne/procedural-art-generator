package com.gmail.laurencewarne.artgenerator.spritecreation;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;

public class EnumSpriteGeneratorTest {

    private EnumSpriteGenerator gen1, gen2;
    // For convenience
    private final static EnumSpriteGenerator.CellState ALWAYS_EMPTY =
	EnumSpriteGenerator.CellState.ALWAYS_EMPTY;
    private final static EnumSpriteGenerator.CellState ALWAYS_FILLED =
	EnumSpriteGenerator.CellState.ALWAYS_FILLED;
    private final static EnumSpriteGenerator.CellState VARIED =
	EnumSpriteGenerator.CellState.VARIED;

    @Before
    public void setUp() {

	ICellGrid<EnumSpriteGenerator.CellState> grid1 =
	    new ArrayListCellGrid<>(10, 10, ALWAYS_EMPTY);
	ICellGrid<EnumSpriteGenerator.CellState> grid2 =
	    new ArrayListCellGrid<>(10, 50, ALWAYS_FILLED);
	gen1 = new EnumSpriteGenerator(grid1, 13423L);
	gen2 = new EnumSpriteGenerator(grid2, 31435435432L);
    }

    @Test
    public void testCellValuesAreDefault() {

	for ( int i = 0; i < gen1.getYLength(); i++ ){
	    for ( int j = 0; j < gen1.getXLength(); j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		assertEquals(ALWAYS_EMPTY, gen1.getCellStateAt(coord));
	    }
	}
	for ( int i = 0; i < gen2.getYLength(); i++ ){
	    for ( int j = 0; j < gen2.getXLength(); j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		assertEquals(ALWAYS_FILLED, gen2.getCellStateAt(coord));
	    }
	}
    }

    @Test
    public void testCellValuesChange() {
	
	gen1.setCellStateAt(new CellCoordinate(0, 0), ALWAYS_FILLED);
	gen1.setCellStateAt(new CellCoordinate(0, 1), ALWAYS_EMPTY);
	gen1.setCellStateAt(new CellCoordinate(6, 4), VARIED);

	assertEquals(gen1.getCellStateAt(new CellCoordinate(0, 0)), ALWAYS_FILLED);
	assertEquals(gen1.getCellStateAt(new CellCoordinate(0, 1)), ALWAYS_EMPTY);
	assertEquals(gen1.getCellStateAt(new CellCoordinate(6, 4)), VARIED);

	gen2.setCellStateAt(new CellCoordinate(0, 0), ALWAYS_FILLED);
	gen2.setCellStateAt(new CellCoordinate(0, 1), ALWAYS_EMPTY);
	gen2.setCellStateAt(new CellCoordinate(6, 46), VARIED);

	assertEquals(gen2.getCellStateAt(new CellCoordinate(0, 0)), ALWAYS_FILLED);
	assertEquals(gen2.getCellStateAt(new CellCoordinate(0, 1)), ALWAYS_EMPTY);
	assertEquals(gen2.getCellStateAt(new CellCoordinate(6, 46)), VARIED);	
    }

    @Test
    public void testOutputGridCorrectFromInitialGrid() {

	ICellGrid<Boolean> output1 = gen1.genSpriteAsCellGrid();
	ICellGrid<Boolean> output2 = gen2.genSpriteAsCellGrid();
	for ( int i = 0; i < output1.getYLength(); i++ ){
	    for ( int j = 0; j < output1.getXLength(); j++ ){
		assertEquals(output1.getValueAt(new CellCoordinate(j, i)), false);
	    }
	}
	assertEquals(output1, new ArrayListCellGrid<Boolean>(10, 10, false));
	assertEquals(output2, new ArrayListCellGrid<Boolean>(10, 50, true));
    }

    @Test
    public void testOutputGridCorrectFromNonRandomChangedGrid() {

	for ( int i = 0; i < gen1.getYLength(); i++ ){
	    for ( int j = 0; j < gen1.getXLength()/2; j++ ){
		gen1.setCellStateAt(new CellCoordinate(j, i), ALWAYS_FILLED);
	    }
	}
	ICellGrid<Boolean> output = gen1.genSpriteAsCellGrid();
	for ( int i = 0; i < gen1.getYLength(); i++ ){
	    for ( int j = 0; j < gen1.getXLength(); j++ ){
		if ( j < gen1.getXLength()/2 ){
		    assertEquals(output.getValueAt(new CellCoordinate(j, i)), true);
		}
		else {
		    assertEquals(output.getValueAt(new CellCoordinate(j, i)), false);
		}
	    }
	}
    }

    @Test
    public void testOutputGridCorrectFromRandomChangedGrid() {

	for ( int i = 0; i < gen1.getYLength(); i++ ){
	    for ( int j = 0; j < gen1.getXLength()/2; j++ ){
		if ( i == j ){
		    gen1.setCellStateAt(new CellCoordinate(j, i), VARIED);
		}
	    }
	}
	// We test the varied coordinates are not null.
	ICellGrid<Boolean> output = gen1.genSpriteAsCellGrid();
	for ( int i = 0; i < gen1.getYLength(); i++ ){
	    for ( int j = 0; j < gen1.getXLength(); j++ ){
		if ( i == j ){
		    assertNotEquals(output.getValueAt(new CellCoordinate(j, i)), null);
		}
		else {
		    assertEquals(output.getValueAt(new CellCoordinate(j, i)), false);
		}
	    }
	}
    }

    @Test
    public void testOutputGridSameForSameSeed() {

	for ( int i = 0; i < gen1.getYLength(); i++ ){
	    for ( int j = 0; j < gen1.getXLength()/2; j++ ){
		if ( i == j ){
		    gen1.setCellStateAt(new CellCoordinate(j, i), VARIED);
		}
	    }
	}
	ICellGrid<Boolean> output1 = gen1.genSpriteAsCellGrid();
	ICellGrid<Boolean> output2 = gen1.genSpriteAsCellGrid();
	assertEquals(output1, output2);
    }

    @Test
    public void testOutputGridDifferentForDifferentSeed() {

	for ( int i = 0; i < gen1.getYLength(); i++ ){
	    for ( int j = 0; j < gen1.getXLength()/2; j++ ){
		if ( i == j ){
		    gen1.setCellStateAt(new CellCoordinate(j, i), VARIED);
		}
	    }
	}
	ICellGrid<Boolean> output1 = gen1.genSpriteAsCellGrid();
	gen1.setSeed(gen1.getSeed() + 1);
	ICellGrid<Boolean> output2 = gen2.genSpriteAsCellGrid();
	assertNotEquals(output1, output2);
    }
}
