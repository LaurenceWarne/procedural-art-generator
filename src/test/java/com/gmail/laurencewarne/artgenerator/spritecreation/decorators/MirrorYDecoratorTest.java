package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.spritecreation.EnumSpriteOutlineGenerator;

public class MirrorYDecoratorTest {

    MirrorYDecorator<Boolean> gen1, gen2, gen3, gen4;
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
	gen1 = new MirrorYDecorator<>(new EnumSpriteOutlineGenerator(grid1, 13L), true);
	gen2 = new MirrorYDecorator<>(new EnumSpriteOutlineGenerator(grid2, 13L), false);
	EnumSpriteOutlineGenerator e = new EnumSpriteOutlineGenerator(grid3, 34L);
	e.setCellStateAt(new CellCoordinate(0, 0), ALWAYS_EMPTY);
	e.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_EMPTY);
	e.setCellStateAt(new CellCoordinate(2, 2), ALWAYS_EMPTY);	
	gen3 = new MirrorYDecorator<>(e, true);
	gen4 = new MirrorYDecorator<>(e, false);
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

	ICellGrid<Boolean> output1 = gen1.genSpriteOutlineAsCellGrid();
	for ( int i = 0; i < gen1.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen1.getXLengthOfOutline(); j++ ){
		assertEquals(output1.getValueAt(new CellCoordinate(j, i)), output1.getValueAt(new CellCoordinate(j, gen1.getYLengthOfOutline() - i - 1)));
	    }
	}
	ICellGrid<Boolean> output2 = gen2.genSpriteOutlineAsCellGrid();	
	for ( int i = 0; i < gen2.getYLengthOfOutline(); i++ ){
	    for ( int j = 0; j < gen2.getXLengthOfOutline(); j++ ){
		assertEquals(output2.getValueAt(new CellCoordinate(j, i)), output2.getValueAt(new CellCoordinate(j, gen2.getYLengthOfOutline() - i - 1)));
	    }
	}
    }

    @Test
    public void testSmallOutputArrayIsCorrect() {
	
	ICellGrid<Boolean> output1 = gen3.genSpriteOutlineAsCellGrid();
	ICellGrid<Boolean> output2 = gen4.genSpriteOutlineAsCellGrid();
	// Undecorated array:
	// { {false, true, true},
	// {true, false, true},
	// {true, true, false} }
	ICellGrid<Boolean> reflectedUp = new ArrayListCellGrid<Boolean>(3, 6, true);
	reflectedUp.setValueAt(new CellCoordinate(2, 0), false);
	reflectedUp.setValueAt(new CellCoordinate(1, 1), false);
	reflectedUp.setValueAt(new CellCoordinate(0, 2), false);
	reflectedUp.setValueAt(new CellCoordinate(0, 3), false);
	reflectedUp.setValueAt(new CellCoordinate(1, 4), false);
	reflectedUp.setValueAt(new CellCoordinate(2, 5), false);
	/*
	boolean[][] reflectedUp = new boolean[][] {
	    {true, true, false},
	    {true, false, true},
	    {false, true, true},
	    //-----------------
	    {false, true, true},
	    {true, false, true},
	    {true, true, false},
	};
	*/
	ICellGrid<Boolean> reflectedDown = new ArrayListCellGrid<Boolean>(3, 6, true);
	reflectedDown.setValueAt(new CellCoordinate(0, 0), false);
	reflectedDown.setValueAt(new CellCoordinate(1, 1), false);
	reflectedDown.setValueAt(new CellCoordinate(2, 2), false);
	reflectedDown.setValueAt(new CellCoordinate(2, 3), false);
	reflectedDown.setValueAt(new CellCoordinate(1, 4), false);
	reflectedDown.setValueAt(new CellCoordinate(0, 5), false);	
	/*
	boolean[][] reflectedDown = new boolean[][] {
	    {false, true, true},
	    {true, false, true},
	    {true, true, false},
	    //-----------------
	    {true, true, false},
	    {true, false, true},
	    {false, true, true},	
	};
	*/
	assertEquals(output1, reflectedUp);
	assertEquals(output2, reflectedDown);
    }
}
