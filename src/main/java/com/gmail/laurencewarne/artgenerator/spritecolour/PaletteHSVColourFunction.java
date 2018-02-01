package com.gmail.laurencewarne.artgenerator.spritecolour;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Random;


public abstract class PaletteHSVColourFunction implements IColourFunction {

    protected Palette palette;

    public PaletteHSVColourFunction( final Map<Integer, Integer> hueWeightings ) {

	palette = new PaletteHSVColourFunction.Palette(hueWeightings);
    }

    public Palette getPalette() {

	return palette;
    }

    public void setPalette( final Palette palette ) {

	this.palette = palette;
    }

    public static class Palette {

	private final NavigableMap<Integer, Integer> accumWeightToHueMapping;
	private int total;
	private final Random random = new Random();

	public Palette( final Map<Integer, Integer> hueWeigtings )
	    throws IllegalArgumentException {

	    accumWeightToHueMapping = new TreeMap<>();
	    total = 0;
	    for ( Map.Entry<Integer, Integer> entry : accumWeightToHueMapping.entrySet() ){
		if ( isHueValid(entry.getKey()) && entry.getValue() > 0 ){
		    total += entry.getValue();
		    accumWeightToHueMapping.put(total, entry.getKey());
		}
		else {
		    throw new IllegalArgumentException("Invalid key or value found in input Map");
		}
	    }
	}

	public void addHue( final int hue, final int weighting )
	    throws IllegalArgumentException {

	    if ( !isHueValid(hue) ){
		throw new IllegalArgumentException("Hue values must be between 0 and 360 inclusive");
	    }
	    else if ( weighting <= 0 ){
		throw new IllegalArgumentException("The weighting must be greater than 0");
	    }
	    else {
		total += weighting;
		accumWeightToHueMapping.put(total, hue);
	    }
	}

	public int getRandomHue() {
	    
	    Map.Entry<Integer, Integer> generatedEntry = accumWeightToHueMapping.
		ceilingEntry(random.nextInt(total));
	    return generatedEntry.getValue().intValue();
	}

	public void setHueValue( final int hue, final int weighting )
	    throws IllegalArgumentException {

	    if ( weighting < 0 ){
		throw new IllegalArgumentException("Weighting must be greater than or equal to zero.");
	    }
	    int prevWeighting = 0;
	    for ( int key : accumWeightToHueMapping.keySet() ){
		if ( accumWeightToHueMapping.get(weighting) == hue ){
		    prevWeighting = key; break;
		}
	    }
	    int diff = prevWeighting - weighting;
	    total -= diff;
	    for ( int key : accumWeightToHueMapping.tailMap(prevWeighting, true).keySet() ){
		int newValue = accumWeightToHueMapping.get(key) - diff;
		accumWeightToHueMapping.put(key, newValue);
	    }
	    if ( weighting == 0 ){
		accumWeightToHueMapping.remove(prevWeighting);
	    }
	}

	public boolean isHueValid( final int hue ) {

	    if ( hue >= 0 && hue <= 360 ){
		return true;
	    }
	    else {
		return false;
	    }
	}
    }
}
