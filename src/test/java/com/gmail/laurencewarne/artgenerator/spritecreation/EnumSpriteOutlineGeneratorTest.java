package com.gmail.laurencewarne.artgenerator.spritecreation;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;

public class EnumSpriteOutlineGeneratorTest {

    private EnumSpriteOutlineGenerator gen1, gen2;
    // For convenience
    private final static EnumSpriteOutlineGenerator.CellState ALWAYS_EMPTY =
	EnumSpriteOutlineGenerator.CellState.ALWAYS_EMPTY;
    private final static EnumSpriteOutlineGenerator.CellState ALWAYS_FILLED =
	EnumSpriteOutlineGenerator.CellState.ALWAYS_FILLED;
    private final static EnumSpriteOutlineGenerator.CellState VARIED =
	EnumSpriteOutlineGenerator.CellState.VARIED;

    @Before
    public void setUp() {

	ICellGrid<EnumSpriteOutlineGenerator.CellState> grid1 =
	    new ArrayListCellGrid<>(10, 10, ALWAYS_EMPTY);
	ICellGrid<EnumSpriteOutlineGenerator.CellState> grid2 =
	    new ArrayListCellGrid<>(10, 50, ALWAYS_FILLED);
	gen1 = new EnumSpriteOutlineGenerator(grid1, 13423L);
	gen2 = new EnumSpriteOutlineGenerator(grid2, 31435435432L);
    }

    @Test
    public void testCellValuesAreDefault() {

	for ( int i = 0; i < gen1.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen1.getXLengthOfOutline(); j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		assertEquals(ALWAYS_EMPTY, gen1.getCellStateAt(coord));
	    }
	}
	for ( int i = 0; i < gen2.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen2.getXLengthOfOutline(); j++ ){
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

	ICellGrid<Boolean> output1 = gen1.genSpriteOutlineAsCellGrid();
	ICellGrid<Boolean> output2 = gen2.genSpriteOutlineAsCellGrid();
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

	for ( int i = 0; i < gen1.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen1.getXLengthOfOutline()/2; j++ ){
		gen1.setCellStateAt(new CellCoordinate(j, i), ALWAYS_FILLED);
	    }
	}
	ICellGrid<Boolean> output = gen1.genSpriteOutlineAsCellGrid();
	for ( int i = 0; i < gen1.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen1.getXLengthOfOutline(); j++ ){
		if ( j < gen1.getXLengthOfOutline()/2 ){
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

	for ( int i = 0; i < gen1.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen1.getXLengthOfOutline()/2; j++ ){
		if ( i == j ){
		    gen1.setCellStateAt(new CellCoordinate(j, i), VARIED);
		}
	    }
	}
	// We test the varied coordinates are not null.
	ICellGrid<Boolean> output = gen1.genSpriteOutlineAsCellGrid();
	for ( int i = 0; i < gen1.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen1.getXLengthOfOutline(); j++ ){
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

	for ( int i = 0; i < gen1.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen1.getXLengthOfOutline()/2; j++ ){
		if ( i == j ){
		    gen1.setCellStateAt(new CellCoordinate(j, i), VARIED);
		}
	    }
	}
	ICellGrid<Boolean> output1 = gen1.genSpriteOutlineAsCellGrid();
	ICellGrid<Boolean> output2 = gen1.genSpriteOutlineAsCellGrid();
	assertEquals(output1, output2);
    }

    @Test
    public void testOutputGridDifferentForDifferentSeed() {

	for ( int i = 0; i < gen1.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen1.getXLengthOfOutline()/2; j++ ){
		if ( i == j ){
		    gen1.setCellStateAt(new CellCoordinate(j, i), VARIED);
		}
	    }
	}
	ICellGrid<Boolean> output1 = gen1.genSpriteOutlineAsCellGrid();
	gen1.setSeed(gen1.getSeed() + 1);
	ICellGrid<Boolean> output2 = gen2.genSpriteOutlineAsCellGrid();
	assertNotEquals(output1, output2);
    }
}
