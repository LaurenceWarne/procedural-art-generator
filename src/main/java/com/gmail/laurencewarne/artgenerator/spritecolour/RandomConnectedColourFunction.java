package com.gmail.laurencewarne.artgenerator.spritecolour;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;


public class RandomConnectedColourFunction extends PaletteHSVColourFunction implements IColourFunction {

    private final int xLength, yLength;
    private final ConnectedCoordinateCollection connectedCoords;
    private final Map<CellCoordinate> coordToHueMapping;

    public RandomConnectedColourFunction( final int xLength, final int yLength, final Map<Integer, Integer> hueWeightings, ConnectedCoordinateCollection connectedCoords ){

	super(hueWeightings);
	this.xLength = xLength;
	this.yLength = yLength;
	this.connectedCoords = connectedCoords;
	coordToHueMapping = new HashMap<>();
	for ( Set<CellCoordinate> cellSet : connectedCoords ){
	    for ( CellCoordinate coord : cellSet ){
		coordToHueMapping.put(coord, palette.getRandomHue());
	    }
	}
    }

    public int[] getRGBColourAtCoordinate( final int x, final int y )
	throws UnsupportedOperationException {

	throw new UnsupportedOperationException("Method not implemented");
    }

    public float[] getHSVColourAtCoordinate( final int x, final int y ) {

	return null;
    }    
}
