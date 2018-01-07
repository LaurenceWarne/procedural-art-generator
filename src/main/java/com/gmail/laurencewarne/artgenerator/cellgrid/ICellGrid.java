package com.gmail.laurencewarne.artgenerator.cellgrid;

import java.util.List;


public interface ICellGrid<E> {

    List<CellCoordinate> getCoordsOfMooreNeighbours( CellCoordinate coord )
	throws IllegalArgumentException;

    List<CellCoordinate> getCoordsOfNeumannNeighbours( CellCoordinate coord )
	throws IllegalArgumentException;

    List<CellCoordinate> getCoordsOfCellsEqualTo( E value )
	throws IllegalArgumentException;

    E getValueAt( CellCoordinate coord )
	throws IllegalArgumentException;

    void setValueAt( CellCoordinate coord, E value )
	throws IllegalArgumentException;

}
