package com.gmail.laurencewarne.artgenerator.spritecreation.decorators.colourfunctions;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Comparator;

import com.gmail.laurencewarne.artgenerator.cellgrid.CellCoordinate;
import com.gmail.laurencewarne.artgenerator.spritecreation.ConnectedCoordinateCollection;


public class RandomConnectedColourFunction extends PaletteHSVColourFunction implements IColourFunction {

    private final ConnectedCoordinateCollection connectedCoords;
    private final Map<CellCoordinate, Integer> coordToHueMapping;

    public RandomConnectedColourFunction( final Map<Integer, Integer> hueWeightings, final ConnectedCoordinateCollection connectedCoords ){

	super(hueWeightings);
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

	Set<CellCoordinate> connected = new TreeSet<>
	    (new Comparator<CellCoordinate>() {

		    @Override
		    public int compare( CellCoordinate o1, CellCoordinate o2 ) {

			//Sort by x coordinate
			if ( o1.x > o2.x ){
			    return 1;
			}
			else if ( o1.x == o2.x ){
			    return 0;
			}
			else {
			    return -1;
			}
		    }
		}
	);
	connected.addAll
	    (connectedCoords.getValidCellsConnectedTo(new CellCoordinate(x, y)));
	return null;
    }
}
