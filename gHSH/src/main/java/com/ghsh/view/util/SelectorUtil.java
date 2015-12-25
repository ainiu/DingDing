package com.ghsh.view.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;


public class SelectorUtil {
	/**创建StateListDrawable*/
	public static StateListDrawable createSelectorDrawable(Context context,Drawable normalDrawable,Drawable pressedDrawable) {
		StateListDrawable bg = new StateListDrawable();
		bg.addState(new int[] { android.R.attr.state_enabled,android.R.attr.state_pressed }, pressedDrawable);
		bg.addState(new int[] { android.R.attr.state_enabled,android.R.attr.state_focused }, pressedDrawable);
		bg.addState(new int[] { android.R.attr.state_enabled,android.R.attr.state_selected }, pressedDrawable);
		bg.addState(new int[] { android.R.attr.state_enabled,android.R.attr.state_checked }, pressedDrawable);
		bg.addState(new int[] { android.R.attr.state_enabled }, normalDrawable);
		bg.addState(new int[] { android.R.attr.state_focused }, pressedDrawable);
		bg.addState(new int[] { android.R.attr.state_window_focused }, pressedDrawable);
		bg.addState(new int[] {}, normalDrawable);
		return bg;
	}
}
