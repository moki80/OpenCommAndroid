package edu.cornell.opencomm;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

/** Hold information about private space:
 * 
 * 1) Private Space icon (really the color)
 * 2) People in private space
 * 
 * @author jnf34
 *
 */

public class PrivateSpace extends ImageButton {
	
	/**
	 * All the private spaces open in the app
	 */
	public static LinkedList<PrivateSpace> currentSpaces = new LinkedList<PrivateSpace>();
	/**
	 * Colors private spaces can have
	 */
	public static int[] COLORS = {Color.BLUE,Color.YELLOW,Color.GREEN,Color.MAGENTA,Color.CYAN,Color.DKGRAY};
	/**
	 * Number to assign to new private spaces
	 */
	public static int privateSpaceCounter = 0;
	
	protected LinkedList<Person> peopleInSpace = new LinkedList<Person>();

	private int spaceId = -1;
	private int color = Color.BLUE;
	
	protected boolean isSelected = false;
	protected boolean isHovered = false;
	public View.OnTouchListener ontouchlistener;
	
	public PrivateSpace(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public PrivateSpace(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public PrivateSpace(Context context) {
		super(context);
		init();
	}

	/**
	 * Initialize the object's spaceId, color, and add it to the list of all spaces
	 */
	private final synchronized void init(){
		this.spaceId = PrivateSpace.privateSpaceCounter++;
		this.color = PrivateSpace.COLORS[spaceId%PrivateSpace.COLORS.length];
		/*this.setOnClickListener(new OnClickListener(){
			
			public void onClick(View arg0) {
				clicked(arg0);
			}			
		}); */
		View v = null;
		
		// Nora adding this
		
//		this.setOnTouchListener( new View.OnTouchListener() {
//			
//			public boolean onTouch(View view, MotionEvent evt) {
//				switch(evt.getAction()){
//				case MotionEvent.ACTION_DOWN:
//					clicked(view);
//					break;
//				case MotionEvent.ACTION_MOVE:
//					//clicked(view);
//					break;
//				case MotionEvent.ACTION_UP:
//					clicked(view);
//					break;
//				}
//				
//				// TODO Auto-generated method stub
//				return false;
//			}
//		});
		
		
		
		PrivateSpace.currentSpaces.add(this);
	}

	/**
	 * Change state of selected variable, make sure only one space is selected at a time
	 * ***This part may need to be moved to the MainChat class***
	 * @param arg0
	 */
	protected synchronized void clicked(View arg0) {
		this.isSelected = !isSelected;
		if (isSelected){ //make all others not selected
			for(PrivateSpace p : PrivateSpace.currentSpaces){
				if(p.equals(this)) continue;
				
				p.isSelected = false;
			}
		}
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas); //Draw the regular stuff
		
		//Draw in 3 steps:
		// 1. Draw background to erase old state
		// 2. Draw this private space's color
		// 3. Draw smaller square if the private space isn't open or being previewed
		int backgroundColor = R.color.scroll_background;
		RectShape rect = new RectShape();
		ShapeDrawable normalShape = new ShapeDrawable(rect);
		normalShape.getPaint().setColor(color);
		normalShape.setBounds(2, 2, this.getWidth() - 2, this.getHeight() - 2);
		canvas.drawColor(backgroundColor);
		normalShape.draw(canvas);
		RectShape rect2 = new RectShape();
		if (!this.isSelected && !this.isHovered){
			ShapeDrawable s = new ShapeDrawable(rect2);
			s.getPaint().setColor(backgroundColor);
			s.setBounds(6, 6, this.getHeight() - 4, this.getHeight() - 6);
			s.draw(canvas);
		}
	}
	
	/**
	 * Adds the person to this private space
	 * @param p
	 */
	public void add(Person p){
		this.clicked(this);
		if (!this.peopleInSpace.contains(p)){
			this.peopleInSpace.add(p);
		}
	}
	
	/**
	 * Removes the person p from this space
	 * @param p
	 */
	public void removeFromSpace(Person p){
		this.peopleInSpace.remove(p);
	}
	
	/**
	 * Returns list of person objects for the people in this space
	 * @return
	 */
	public LinkedList<Person> getPeople(){
		return this.peopleInSpace;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	// Nora added isHovered, will work with it more
	public boolean isHovered(){
		return isHovered;
	}
	
	public void setHovered(boolean isHovered){
		this.isHovered = isHovered;
	}

	/**
	 * Returns true if the coordinates given are within this view
	 * @param y
	 * @param x
	 * @return
	 */
	public boolean contains(int x, int y) {
		int[] location = new int[2];
		this.getLocationOnScreen(location);
		if (!this.isShown()) return false;
		return (x > location[0]  && x < location[0] + this.getWidth()
				&& y > location[1] - this.getHeight() && y < location[1] + this.getHeight());
	}
	
	// Nora experimenting
	/*
	public void clicked2(View view) {
		this.isSelected = !isSelected;
		if (isSelected){ //make all others not selected
			for(PrivateSpace p : PrivateSpace.currentSpaces){
				if(p.equals(this)) continue;
				
				p.isSelected = false;
			}
		}
	} */
}
