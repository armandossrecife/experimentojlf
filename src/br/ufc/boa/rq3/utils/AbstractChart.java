package br.ufc.boa.rq3.utils;

/*
 * Copyright 2013-2014 Iowa State University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY IOWA STATE UNIVERSITY ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL IOWA STATE UNIVERSITY OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of Iowa State University.
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

import br.ufc.boa.rq3.features.java.*;

/**
 * Base class for all charts.
 * Helps maintain consistency across charts.
 *
 * @author Robert Dyer (rdyer@iastate.edu)
 */
public abstract class AbstractChart {
	protected int fontBase = 13;
	protected int fontScale = 1;
	protected int width = 990;
	protected int height = 525;

	protected static final String[] features = {
		"AnnotDefine",
		"AnnotUse",
		"Assert",
		"BinaryLit",
		"Diamond",
		"EnhancedFor",
		"Enums",
		"GenDefField",
		"GenDefMethod",
		"GenDefType",
		"GenWildcard",
		"GenExtends",
		"GenSuper",
		"MultiCatch",
		"SafeVarargs",
		"TryResources",
		"UnderscoreLit",
		"Varargs"
	};

    protected void save(final JFreeChart chart, final String outfilename) {
		try {
			final String pngfilename = outfilename + ".png";
			System.out.println("Saving to " + pngfilename);
			ChartUtilities.saveChartAsPNG(new File(pngfilename), chart, width, height, null, true, 0);
		} catch (IOException e) { }
    }
	
	protected DateAxis getDateAxis() {
		return new DateAxis() {
			@SuppressWarnings("unchecked")
			@Override
			protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
				List ticks = super.refreshTicksHorizontal(g2, dataArea, edge);
				List ret = new ArrayList();
				for (Tick tick : (List<Tick>)ticks)
					if (tick instanceof DateTick) {
						DateTick dateTick = (DateTick)tick;
						ret.add(new DateTick(dateTick.getDate(), dateTick.getText(), dateTick.getTextAnchor(), dateTick.getRotationAnchor(), getLabelAngle()));
					} else {
						ret.add(tick);
					}
				return ret;
			}
		};
	}

	protected void scaleDateAxis(DateAxis axis) {
		axis.setVerticalTickLabels(true);
		axis.setLabelAngle(-0.5 * Math.PI);
		if (axis.getRange().getLength() < 90000000000L) {
			axis.setDateFormatOverride(new SimpleDateFormat("yyyy MM"));
			axis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 6));
		} else if (axis.getRange().getLength() < 300000000000L) {
			axis.setDateFormatOverride(new SimpleDateFormat("yyyy"));
			axis.setTickUnit(new DateTickUnit(DateTickUnitType.YEAR, 1));
		} else {
			axis.setDateFormatOverride(new SimpleDateFormat("yyyy"));
			axis.setTickUnit(new DateTickUnit(DateTickUnitType.YEAR, 2));
		}
	}

	protected void setTheme(final JFreeChart chart, final Plot plot) {
		final StandardChartTheme chartTheme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();
		chartTheme.setExtraLargeFont(new Font("Tahoma", Font.BOLD, (fontBase + 14) * fontScale));
		chartTheme.setLargeFont(new Font("Tahoma", Font.BOLD, (fontBase + 10) * fontScale));
		chartTheme.setRegularFont(new Font("Tahoma", Font.BOLD, (fontBase + 4) * fontScale));
		chartTheme.setSmallFont(new Font("Tahoma", Font.BOLD, fontBase * fontScale));
		chartTheme.apply(chart);

		plot.setBackgroundPaint(new Color(0.8f, 0.8f, 0.8f, 1f));
	}

	protected void setXYTheme(final JFreeChart chart, final XYPlot plot) {
		setTheme(chart, plot);

		plot.setDomainGridlinePaint(new Color(1f, 1f, 1f, 1f));
		plot.setRangeGridlinePaint(plot.getDomainGridlinePaint());
		BasicStroke domainStroke = (BasicStroke) plot.getDomainGridlineStroke();
		BasicStroke rangeStroke = (BasicStroke) plot.getRangeGridlineStroke();
		plot.setDomainGridlineStroke(new BasicStroke(3 * domainStroke.getLineWidth(), domainStroke.getEndCap(), domainStroke.getLineJoin(), domainStroke.getMiterLimit(), domainStroke.getDashArray(), domainStroke.getDashPhase()));
		plot.setRangeGridlineStroke(new BasicStroke(3 * rangeStroke.getLineWidth(), rangeStroke.getEndCap(), rangeStroke.getLineJoin(), rangeStroke.getMiterLimit(), rangeStroke.getDashArray(), rangeStroke.getDashPhase()));
	}

	protected void setScatterTheme(final JFreeChart chart, final XYPlot plot) {
		setTheme(chart, plot);

		plot.setDomainGridlinePaint(new Color(1f, 1f, 1f, 1f));
		plot.setRangeGridlinePaint(plot.getDomainGridlinePaint());
		BasicStroke domainStroke = (BasicStroke) plot.getDomainGridlineStroke();
		BasicStroke rangeStroke = (BasicStroke) plot.getRangeGridlineStroke();
		plot.setDomainGridlineStroke(new BasicStroke(3 * domainStroke.getLineWidth(), domainStroke.getEndCap(), domainStroke.getLineJoin(), domainStroke.getMiterLimit(), domainStroke.getDashArray(), domainStroke.getDashPhase()));
		plot.setRangeGridlineStroke(new BasicStroke(3 * rangeStroke.getLineWidth(), rangeStroke.getEndCap(), rangeStroke.getLineJoin(), rangeStroke.getMiterLimit(), rangeStroke.getDashArray(), rangeStroke.getDashPhase()));
	}
	
	protected void setCategoryTheme(final JFreeChart chart, final CategoryPlot plot) {
		setTheme(chart, plot);

		plot.setDomainGridlinePaint(new Color(1f, 1f, 1f, 1f));
		plot.setRangeGridlinePaint(plot.getDomainGridlinePaint());
		BasicStroke domainStroke = (BasicStroke) plot.getDomainGridlineStroke();
		BasicStroke rangeStroke = (BasicStroke) plot.getRangeGridlineStroke();
		plot.setDomainGridlineStroke(new BasicStroke(3 * domainStroke.getLineWidth(), domainStroke.getEndCap(), domainStroke.getLineJoin(), domainStroke.getMiterLimit(), domainStroke.getDashArray(), domainStroke.getDashPhase()));
		plot.setRangeGridlineStroke(new BasicStroke(3 * rangeStroke.getLineWidth(), rangeStroke.getEndCap(), rangeStroke.getLineJoin(), rangeStroke.getMiterLimit(), rangeStroke.getDashArray(), rangeStroke.getDashPhase()));
	}

	protected void setMarkers(final XYPlot plot, final String feature) {
		if (LanguageFeature.jlsFeatures.get(feature) == LanguageFeature.Version_Feature_JSL2)
			addMarker(plot, "JLS2", 1013018400000L);
		if (LanguageFeature.jlsFeatures.get(feature) == LanguageFeature.Version_Feature_JSL3)
			addMarker(plot, "JLS3", 1096563600000L);
		if (LanguageFeature.jlsFeatures.get(feature) == LanguageFeature.Version_Feature_JSL4)
			addMarker(plot, "JLS4", 1311872400000L);
	}

	protected void setMarkers(final CategoryPlot plot, final String feature) {
		if (LanguageFeature.jlsFeatures.get(feature) == LanguageFeature.Version_Feature_JSL2)
			addMarker(plot, "JLS2", 200202);
		if (LanguageFeature.jlsFeatures.get(feature) == LanguageFeature.Version_Feature_JSL3)
			addMarker(plot, "JLS3", 200409);
		if (LanguageFeature.jlsFeatures.get(feature) == LanguageFeature.Version_Feature_JSL4)
			addMarker(plot, "JLS4", 201107);
	}

	private void addMarker(XYPlot plot, String name, long time) {
		final Marker mark = new ValueMarker(time);
		final Color c = new Color(0.1f, 0.1f, 0.6f, 0.9f);
		mark.setPaint(c);
		mark.setStroke(new BasicStroke(2 * fontScale));
		mark.setLabel(" " + name);
		mark.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
		mark.setLabelFont(plot.getRenderer().getBaseItemLabelFont());
		mark.setLabelTextAnchor(TextAnchor.TOP_LEFT);
		mark.setLabelPaint(c);
		plot.addDomainMarker(mark);
	}
	
	private void addMarker(CategoryPlot plot, String name, int time) {
		final CategoryMarker mark = new CategoryMarker(time);
		final Color c = new Color(0.1f, 0.1f, 0.6f, 0.9f);
		mark.setPaint(c);
		mark.setStroke(new BasicStroke(fontScale));
		mark.setLabel(" " + name);
		mark.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
		mark.setLabelFont(plot.getRenderer().getBaseItemLabelFont());
		mark.setLabelTextAnchor(TextAnchor.TOP_LEFT);
		mark.setLabelPaint(c);
		mark.setDrawAsLine(true);
		plot.addDomainMarker(mark);
	}

	protected String readStringFromFile(String inputFile) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFile));
			byte[] bytes = new byte[(int) new File(inputFile).length()];
			in.read(bytes);
			in.close();
			return new String(bytes);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}
}
