package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.spritecreation.EnumSpriteGenerator;

public class MirrorYDecoratorTest {

    MirrorYDecorator<Boolean> gen1, gen2, gen3, gen4;
    private final static EnumSpriteGenerator.CellState ALWAYS_EMPTY =
	EnumSpriteGenerator.CellState.ALWAYS_EMPTY;
    private final static EnumSpriteGenerator.CellState ALWAYS_FILLED =
	EnumSpriteGenerator.CellState.ALWAYS_FILLED;
    private final static EnumSpriteGenerator.CellState VARIED =
	EnumSpriteGenerator.CellState.VARIED;
    ICellGrid<EnumSpriteGenerator.CellState> grid1, grid2, grid3;

    @Before
    public void setUp() {

	grid1 = new ArrayListCellGrid<>(10, 10, VARIED);
	grid2 = new ArrayListCellGrid<>(20, 10, VARIED);
	grid3 = new ArrayListCellGrid<>(3, 3, ALWAYS_FILLED);
	gen1 = new MirrorYDecorator<>(new EnumSpriteGenerator(grid1, 13L), true);
	gen2 = new MirrorYDecorator<>(new EnumSpriteGenerator(grid2, 13L), false);
	EnumSpriteGenerator e = new EnumSpriteGenerator(grid3, 34L);
	e.setCellStateAt(new CellCoordinate(0, 0), ALWAYS_EMPTY);
	e.setCellStateAt(new CellCoordinate(1, 1), ALWAYS_EMPTY);
	e.setCellStateAt(new CellCoordinate(2, 2), ALWAYS_EMPTY);	
	gen3 = new MirrorYDecorator<>(e, true);
	gen4 = new MirrorYDecorator<>(e, false);
    }

    @Test
    public void testOutputArrayYLengthIsDoubled() {

	assertEquals(grid1.getYLength() * 2, gen1.getYLength());
	assertEquals(grid2.getYLength() * 2, gen2.getYLength());
	assertEquals(grid3.getYLength() * 2, gen3.getYLength());
	assertEquals(grid3.getYLength() * 2, gen4.getYLength());	
    }

    @Test
    public void testOutputArrayIsSymmetrical() {

	ICellGrid<Boolean> output1 = gen1.genSpriteAsCellGrid();
	for ( int i = 0; i < gen1.getYLength(); i++ ){
	    for ( int j = 0; j < gen1.getXLength(); j++ ){
		assertEquals(output1.getValueAt(new CellCoordinate(j, i)), output1.getValueAt(new CellCoordinate(j, gen1.getYLength() - i - 1)));
	    }
	}
	ICellGrid<Boolean> output2 = gen2.genSpriteAsCellGrid();	
	for ( int i = 0; i < gen2.getYLength(); i++ ){
	    for ( int j = 0; j < gen2.getXLength(); j++ ){
		assertEquals(output2.getValueAt(new CellCoordinate(j, i)), output2.getValueAt(new CellCoordinate(j, gen2.getYLength() - i - 1)));
	    }
	}
    }

    @Test
    public void testSmallOutputArrayIsCorrect() {
	
	ICellGrid<Boolean> output1 = gen3.genSpriteAsCellGrid();
	ICellGrid<Boolean> output2 = gen4.genSpriteAsCellGrid();
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
