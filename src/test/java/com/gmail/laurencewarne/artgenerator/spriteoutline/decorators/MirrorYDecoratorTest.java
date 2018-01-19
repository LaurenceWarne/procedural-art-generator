package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.spriteoutline.EnumSpriteOutlineGenerator;

public class MirrorYDecoratorTest {

    MirrorYDecorator gen1, gen2, gen3, gen4;
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
	grid3 = new ArrayListCellGrid<>(3, 3, ALWAYS_FILLED);
	gen1 = new MirrorYDecorator(new EnumSpriteOutlineGenerator(grid1, 13L), true);
	gen2 = new MirrorYDecorator(new EnumSpriteOutlineGenerator(grid2, 13L), false);
	EnumSpriteOutlineGenerator e = new EnumSpriteOutlineGenerator(grid3, 34L);
	e.setCellStateAt(new CellCoordinate(0, 0), ALWAYS_EMPTY);
	e.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_EMPTY);
	e.setCellStateAt(new CellCoordinate(2, 2), ALWAYS_EMPTY);	
	gen3 = new MirrorYDecorator(e, true);
	gen4 = new MirrorYDecorator(e, false);
    }

    @Test
    public void testOutputArrayYLengthIsDoubled() {

	assertEquals(grid1.getYLength() * 2, gen1.getYLengthOfOutline());
	assertEquals(grid2.getYLength() * 2, gen2.getYLengthOfOutline());
	assertEquals(grid3.getYLength() * 2, gen3.getYLengthOfOutline());
	assertEquals(grid3.getYLength() * 2, gen4.getYLengthOfOutline());	
    }

    @Test
    public void testOutputArrayIsSymmetrical() {

	boolean[][] output1 = gen1.genSpriteOutline();
	for ( int i = 0; i < gen1.getYLengthOfOutline(); i++ ){
	    assertArrayEquals(output1[i],
			 output1[gen1.getYLengthOfOutline() - i - 1]);
	}
	boolean[][] output2 = gen2.genSpriteOutline();	
	for ( int i = 0; i < gen2.getYLengthOfOutline(); i++ ){
		assertArrayEquals(output2[i],
			     output2[gen2.getYLengthOfOutline() - i - 1]);
	}
    }

    @Test
    public void testSmallOutputArrayIsCorrect() {
	
	boolean[][] output1 = gen3.genSpriteOutline();
	boolean[][] output2 = gen4.genSpriteOutline();
	// Undecorated array:
	// { {false, true, true},
	//   {true, false, true},
	//   {true, true, false} }
	boolean[][] reflectedUp = new boolean[][] {
	    {true, true, false},
	    {true, false, true},
	    {false, true, true},
	    //-----------------
	    {false, true, true},
	    {true, false, true},
	    {true, true, false},
	};
	boolean[][] reflectedDown = new boolean[][] {
	    {false, true, true},
	    {true, false, true},
	    {true, true, false},
	    //-----------------
	    {true, true, false},
	    {true, false, true},
	    {false, true, true},	    
	};
	assertTrue(Arrays.deepEquals(output1, reflectedUp));
	assertTrue(Arrays.deepEquals(output2, reflectedDown));	
    }
}
