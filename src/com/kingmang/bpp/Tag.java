package com.kingmang.bpp;

import java.awt.Color;



public class Tag {

	private final static Color[] TAG_COLORS = {
			new Color(1.0f, 0.6f, 0.6f),
			new Color(0.6f, 0.6f, 1.0f),
			new Color(0.6f, 0.6f, 1.0f),
			new Color(0.4f, 1.0f, 0.8f),
			new Color(1.0f, 0.8f, 0.4f),
			new Color(0.4f, 0.8f, 1.0f)
	};
  public final static int tagNumber = 0;
  public final static int tagString = 1;
  public final static int tagCharacter = 2;
  public final static int tagName = 3;
  public final static int tagSymbol = 4;
  public final static int tagSystem = 5;
  public final static int tagEnd = 10;
  private final static String[] TAG_NAMES = {
	"Number",
	"String",
	"Character",
	"Name",
	"Symbol",
	"System",
	"",
	"",
	"",
	"",
	"End"
  };

  public final static String toName(int tag) {
	if (tag >= 0 && tag < TAG_NAMES.length)
	{
		return TAG_NAMES[tag];
	}
	return String.valueOf(tag);
  }

  public final static Color toColor(int tag) {
	if (tag >= 0 && tag < TAG_COLORS.length)
	{
		return TAG_COLORS[tag];
	}
	return Color.black;
  }
}