package com.gmail.laurencewarne.artgenerator.spriteoutline;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
	gen1 = new EnumSpriteOutlineGenerator(grid1);
	gen2 = new EnumSpriteOutlineGenerator(grid2);
	// Make the protected method genBaseGrid public so we can test it
	Field f = EnumSpriteOutlineGenerator.class;
    }

    @Test
    public void testCellValuesAreDefault() {

	for ( int i = 0; i < gen1.getGridYLength(); i++ ){
	    for ( int j = 0; j < gen1.getGridXLength(); j++ ){
		CellCoordinate coord = new CellCoordinate(j, i);
		assertEquals(ALWAYS_EMPTY, gen1.getCellStateAt(coord));
	    }
	}
	for ( int i = 0; i < gen2.getGridYLength(); i++ ){
	    for ( int j = 0; j < gen2.getGridXLength(); j++ ){
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
}
