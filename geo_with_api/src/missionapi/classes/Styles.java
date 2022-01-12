package missionapi.classes;

import java.awt.Color;

import org.geotools.styling.Font;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.TextSymbolizer;


public class Styles {

	public enum POINT_TYPE {
		DEFAULT,
		RELEASE,
		WAYPOINT,
		TARGET,
		DOT
	}
	
	private Color defaultPointColor = Color.RED;
	private Color defaultPathColor = Color.RED;
	private Color defaultPolygonColor = Color.BLUE;
	
	public Color getDefaultPointColor() {
		return defaultPointColor;
	}
	
	public void setDefaultPointColor(Color col) {
		defaultPointColor = col;
	}
	
	public Color getDefaultPathColor() {
		return defaultPathColor;
	}
	
	public void setDefaultPathColor(Color col) {
		defaultPathColor = col;
	}
	
	public Color getDefaultPolygonColor() {
		return defaultPolygonColor;
	}
	
	public void setDefaultPolygonColor(Color col) {
		defaultPolygonColor = col;
	}
	
	/**
	 * Get text style.
	 * 
	 * */
	public Style getTextStyle(String text, Color col, String fontname, int size) {
		StyleBuilder styleBuilder = new StyleBuilder();
		Font font = styleBuilder.createFont(fontname, size);
		Style style = SLD.createPointStyle("X", Color.WHITE, Color.WHITE, 0.0f, 0.0001f);
		TextSymbolizer textSymb = styleBuilder.createStaticTextSymbolizer(col, font, text);
		Rule rule = styleBuilder.createRule(textSymb);
		style.featureTypeStyles().get(0).rules().add(rule);
		return style;
	}
	
	/**
	 * Get point style.
	 * 
	 * @param pointNameWhereFromShow : look at LayerBuilder.createPointBuilderType builder.length(15).add("Name:", String.class);
	 * <p>
	 * This example uses "Name:" parameter.
	 * */
	public Style getPointStyle(POINT_TYPE pStyle, Color col, float size, String pointNameWhereFromShow, Font font) {
		switch (pStyle) {
		case DEFAULT:
			return SLD.createPointStyle("X", col, col, 0.0f, size, pointNameWhereFromShow, font);
		case RELEASE:
			return SLD.createPointStyle("Circle", col, col, 0.0f, size, pointNameWhereFromShow, font);
		case WAYPOINT:
			return SLD.createPointStyle("Circle", col, col, 0.0f, size, pointNameWhereFromShow, font);
		case TARGET:
			return SLD.createPointStyle("Triangle", col, col, 0.0f, size, pointNameWhereFromShow, font);
		case DOT:
			return SLD.createPointStyle("Circle", col, col, 1, size);
		default:
			return SLD.createPointStyle("X", col, col, 0.0f, size, pointNameWhereFromShow, font);
		}
	}

	/**
	 * Get point style with default size.
	 * 
	 * @param pointNameWhereFromShow : look at LayerBuilder.createPointBuilderType 
	 * <p>
	 * builder.length(15).add("Name:", String.class);
	 * <p>
	 * This example uses "Name:" parameter.
	 * */
	public Style getPointStyle(POINT_TYPE pStyle, Color col, String pointNameToShow, Font font) {
		switch (pStyle) {
		case DEFAULT:
			return SLD.createPointStyle("X", col, col, 0.0f, 5, pointNameToShow, font);
		case RELEASE:
			return SLD.createPointStyle("Circle", col, col, 0.0f, 10, pointNameToShow, font);
		case WAYPOINT:
			return SLD.createPointStyle("Circle", col, col, 0.0f, 20, pointNameToShow, font);
		case TARGET:
			return SLD.createPointStyle("Triangle", col, col, 0.0f, 20, pointNameToShow, font);
		case DOT:
			return SLD.createPointStyle("Circle", col, col, 1, 1);
		default:
			return SLD.createPointStyle("X", col, col, 0.0f, 5, pointNameToShow, font);
		}
	}
	
	/**
	 * Get point style with default size and color.
	 * 
	 * */
	public Style getPointStyle(POINT_TYPE point_TYPE) {
		switch (point_TYPE) {
		case DEFAULT:
			return SLD.createPointStyle("X", defaultPointColor, defaultPointColor, 0.5f, 5, "Name:", null);
		case RELEASE:
			return SLD.createPointStyle("Circle", defaultPointColor, defaultPointColor, 0.0f, 10, "Name:", null);
		case WAYPOINT:
			return SLD.createPointStyle("Circle", defaultPointColor, defaultPointColor, 0.0f, 20, "Name:", null);
		case TARGET:
			return SLD.createPointStyle("Triangle", defaultPointColor, defaultPointColor, 0.0f, 20, "Name:", null);
		case DOT:
			return SLD.createPointStyle("Circle", defaultPointColor, defaultPointColor, 1, 1);
		default:
			return SLD.createPointStyle("X", defaultPointColor, defaultPointColor, 0.5f, 5, "Name:", null);
		}
	}
	
	/**
	 * Default
	 * 
	 * */
	public Style getPolygonStyle() {
		return getPolygonStyle(defaultPolygonColor, null, 1);
	}
	
	public Style getPolygonStyle(Color col, Color fillCol, float opacity) {
        return SLD.createPolygonStyle(col, fillCol, opacity);
	}
	
	/**
	 * Default
	 * 
	 * */
	public Style getLineStyle() {
		return getLineStyle(defaultPathColor, 1);
	}
	
	public Style getLineStyle(Color col, float width) {
		return SLD.createLineStyle(col, width);
	}
}
