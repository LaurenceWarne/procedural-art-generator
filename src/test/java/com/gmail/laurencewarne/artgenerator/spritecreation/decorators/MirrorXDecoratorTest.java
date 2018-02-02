package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.spritecreation.EnumSpriteOutlineGenerator;


public class MirrorXDecoratorTest {

    MirrorXDecorator gen1, gen2, gen3, gen4;
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
	grid2 = new ArrayListCellGrid<>(20, 10, VARIED);
	grid3 = new ArrayListCellGrid<>(3, 2, ALWAYS_FILLED);
	gen1 = new MirrorXDecorator(new EnumSpriteOutlineGenerator(grid1, 13L), true);
	gen2 = new MirrorXDecorator(new EnumSpriteOutlineGenerator(grid2, 13L), false);
	EnumSpriteOutlineGenerator e = new EnumSpriteOutlineGenerator(grid3, 34L);
	e.setCellStateAt(new CellCoordinate(2, 0), ALWAYS_EMPTY);
	e.setCellStateAt(new CellCoordinate(2, 1), ALWAYS_EMPTY);	
	gen3 = new MirrorXDecorator(e, true);
	gen4 = new MirrorXDecorator(e, false);
    }

    @Test
    public void testOutputArrayXLengthIsDoubled() {

	assertEquals(grid1.getXLength() * 2, gen1.getXLengthOfOutline());
	assertEquals(grid2.getXLength() * 2, gen2.getXLengthOfOutline());
	assertEquals(grid3.getXLength() * 2, gen3.getXLengthOfOutline());
	assertEquals(grid3.getXLength() * 2, gen4.getXLengthOfOutline());	
    }

    @Test
    public void testOutputArrayIsSymmetrical() {

	boolean[][] output1 = gen1.genSpriteOutline();
	for ( int i = 0; i < gen1.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen1.getXLengthOfOutline(); j++ ){
		assertEquals(output1[i][j],
			     output1[i][gen1.getXLengthOfOutline() - j - 1]);
	    }
	}
	boolean[][] output2 = gen2.genSpriteOutline();	
	for ( int i = 0; i < gen2.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen2.getXLengthOfOutline(); j++ ){
		assertEquals(output2[i][j],
			     output2[i][gen2.getXLengthOfOutline() - j - 1]);
	    }
	}	
    }

    @Test
    public void testOutputArrayIsCorrectForRelflectLeftForSmallGenerator() {

	boolean[][] output1 = gen3.genSpriteOutline();
	boolean[][] output2 = gen4.genSpriteOutline();	
	//undecorated array = new boolean[] {true, true, false};
	boolean[] leftArray = new boolean[] {false, true, true, true, true, false};
	boolean[] rightArray = new boolean[] {true, true, false, false, true, true};
	for ( boolean[] arr : output1 ){
	    assertTrue(Arrays.equals(arr, leftArray));
	}
	for ( boolean[] arr : output2 ){
	    assertTrue(Arrays.equals(arr, rightArray));
	}
    }
}
