package com.gmail.laurencewarne.artgenerator.spriteoutline.decorators;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.spriteoutline.EnumSpriteOutlineGenerator;


public class MirrorXDecoratorTest {

    MirrorXDecorator gen1, gen2;
    private final static EnumSpriteOutlineGenerator.CellState ALWAYS_EMPTY =
	EnumSpriteOutlineGenerator.CellState.ALWAYS_EMPTY;
    private final static EnumSpriteOutlineGenerator.CellState ALWAYS_FILLED =
	EnumSpriteOutlineGenerator.CellState.ALWAYS_FILLED;
    private final static EnumSpriteOutlineGenerator.CellState VARIED =
	EnumSpriteOutlineGenerator.CellState.VARIED;    
    ICellGrid<EnumSpriteOutlineGenerator.CellState> grid1, grid2;

    @Before
    public void setUp() {


	grid1 = new ArrayListCellGrid<>(10, 10, VARIED);
	grid2 = new ArrayListCellGrid<>(20, 10, VARIED);
	gen1 = new MirrorXDecorator(new EnumSpriteOutlineGenerator(grid1, 13L), true);
	gen2 = new MirrorXDecorator(new EnumSpriteOutlineGenerator(grid2, 13L), false);
    }

    @Test
    public void testOutputArrayXLengthIsDoubled() {

	assertEquals(grid1.getXLength() * 2, gen1.getXLengthOfOutline());
	assertEquals(grid2.getXLength() * 2, gen2.getXLengthOfOutline());	
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
}
