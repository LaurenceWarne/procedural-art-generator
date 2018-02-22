package com.gmail.laurencewarne.artgenerator.spritecreation.decorators;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.gmail.laurencewarne.artgenerator.cellgrid.ICellGrid;
import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.cellgrid.ArrayListCellGrid;
import com.gmail.laurencewarne.artgenerator.spritecreation.EnumSpriteGenerator;


public class MirrorXDecoratorTest {

    MirrorXDecorator<Boolean> gen1, gen2, gen3, gen4;
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
	grid3 = new ArrayListCellGrid<>(3, 2, ALWAYS_FILLED);
	gen1 = new MirrorXDecorator<>(new EnumSpriteGenerator(grid1, 13L), true);
	gen2 = new MirrorXDecorator<>(new EnumSpriteGenerator(grid2, 13L), false);
	EnumSpriteGenerator e = new EnumSpriteGenerator(grid3, 34L);
	e.setCellStateAt(new CellCoordinate(2, 0), ALWAYS_EMPTY);
	e.setCellStateAt(new CellCoordinate(2, 1), ALWAYS_EMPTY);	
	gen3 = new MirrorXDecorator<>(e, true);
	gen4 = new MirrorXDecorator<>(e, false);
    }

    @Test
    public void testOutputArrayXLengthIsDoubled() {

	assertEquals(grid1.getXLength() * 2, gen1.getXLength());
	assertEquals(grid2.getXLength() * 2, gen2.getXLength());
	assertEquals(grid3.getXLength() * 2, gen3.getXLength());
	assertEquals(grid3.getXLength() * 2, gen4.getXLength());	
    }

    @Test
    public void testOutputArrayIsSymmetrical() {

	ICellGrid<Boolean> output1 = gen1.genSpriteAsCellGrid();
	for ( int i = 0; i < gen1.getYLength(); i++ ){
	    for ( int j = 0; j < gen1.getXLength(); j++ ){
		assertEquals(output1.getValueAt(new CellCoordinate(j, i)),
			     output1.getValueAt(new CellCoordinate(gen1.getXLength() - j - 1, i)));
	    }
	}
	ICellGrid<Boolean> output2 = gen2.genSpriteAsCellGrid();	
	for ( int i = 0; i < gen2.getYLength(); i++ ){
	    for ( int j = 0; j < gen2.getXLength(); j++ ){
		assertEquals(output2.getValueAt(new CellCoordinate(j, i)),
			     output2.getValueAt(new CellCoordinate(gen2.getXLength() - j - 1, i)));
	    }
	}	
    }

    @Test
    public void testOutputArrayIsCorrectForRelflectLeftForSmallGenerator() {

	ICellGrid<Boolean> output1 = gen3.genSpriteAsCellGrid();
	ICellGrid<Boolean> output2 = gen4.genSpriteAsCellGrid();	
	//undecorated array = new Boolean[] {true, true, false};
	//leftArray = new Boolean[] {false, true, true, true, true, false};
	//rightArray = new Boolean[] {true, true, false, false, true, true};
	ICellGrid<Boolean> leftGrid = new ArrayListCellGrid<Boolean>(6, output1.getYLength(), true);
	for ( int i = 0; i < output1.getYLength(); i++ ){
	    leftGrid.setValueAt(new CellCoordinate(0, i), false);
	    leftGrid.setValueAt(new CellCoordinate(5, i), false);
	}
	ICellGrid<Boolean> rightGrid = new ArrayListCellGrid<Boolean>(6, output2.getYLength(), true);
	for ( int i = 0; i < output2.getYLength(); i++ ){
	    rightGrid.setValueAt(new CellCoordinate(2, i), false);
	    rightGrid.setValueAt(new CellCoordinate(3, i), false);
	}
	assertEquals(output1, leftGrid);
	assertEquals(output2, rightGrid);
    }
}
