package edu.cornell.opencomm;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Hold information about private space:
 * 
 * 1) Private Space icon (really the color) 2) People in private space
 * 
 * @author jnf34
 * 
 */

public class PrivateSpace extends Space {
	PrivateSpaceView psv;
	MainApplication activity=null;
	static int numPrivateSpaces=0;
	int idNumber;
	
	
	public PrivateSpace(Context c) {
		super(c);
		psv = new PrivateSpaceView(context, this);
		PrivateSpaceView.currentSpaces.add(psv);
		idNumber = numPrivateSpaces;
		numPrivateSpaces ++;
	}
	
	public PrivateSpaceView getPrivateSpaceView(){
		return psv;
	}
	
	public MainApplication getActivity(){
		return activity;
	}
	
	public void setActivity(MainApplication activity){
		this.activity = activity;
	}
	public int getID(){
		return idNumber;
	}
	

}
