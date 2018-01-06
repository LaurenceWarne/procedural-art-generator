package com.gmail.laurencewarne.artgenerator.cellgrid;


public interface ICellGrid<E> {

    CellCoordinate[] getCoordsOfMooreNeighbours( CellCoordinate coord );

    CellCoordinate[] getCoordsOfNeumannNeighbours( CellCoordinate coord );

    CellCoordinate[] getCoordsOfCellsEqualTo( E value );

    E getValueAt( CellCoordinate coord );

    void setValueAt( CellCoordinate coord, E value );

}
