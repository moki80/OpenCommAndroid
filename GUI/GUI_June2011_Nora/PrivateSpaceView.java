package edu.cornell.opencomm;

import java.util.LinkedList;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

/** An icon representing a private space room, will appear at the bottom scrollable bar of the screen */ 
public class PrivateSpaceView{
    Space space; // the private space that this icon represents
    boolean isSelected; // if this is true, then the icon should appear highlighted/darkened
    boolean isHighlighted; // if this is true, should also provide some feedback
    // the list of all current privatespace icons
    public static LinkedList<PrivateSpaceView> allPSIcons= new LinkedList<PrivateSpaceView>(); 
    
    /** Initialize all variables 
     * Set up a touch listener (call init) */
    public PrivateSpaceView(Space space){
        this.space = space;
        isSelected = false;
        isHighlighted = false;
        addThisPSView();
    }
    
    /** Add this PrivateSpace Icon officially to the GUI (because MainApplication needs
     * to add this button to the XML and can only be done in the MainApplication class */
    public void addThisPSView(){
        allPSIcons.add(this);
       // addPrivateSpaceButton(this); // TODO: put back!
    }
    
    /** Delete this PrivateSpace Icon */
    public void deleteThisPSView(){
        allPSIcons.remove(this);
        //delPrivateSpaceButton(this); // TODO: put back!
    }
    
    /** Set up a touch listener that will adjust the selections and highlights according
     * to if you touched the icon. 
     * 1) Click once should highlight button 
     * 2) Click a second time should open up that private space (call method from main application)
    public void init(){
    
    // TODO Copied from earlier code, will need some fixing
    
    		this.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View view, MotionEvent evt) {
				switch (evt.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					toggle(view);
					if (!clickedOnce) {
						clickedOnce = true;
						MainApplication.showPreview(space.people);

					} else if (clickedOnce) {
						openPrivateSpace(); // start new privateSpace activity!
											// (either create new or restart)
						clickedOnce = false;
						MainApplication.showPreview(null);
					}
					break;
				}
				return false;
			}
		});
		invalidate();
    
    }

    /** Draw this icon, draw darker (or brighter) if
     * this PrivateSpaceView is highlighted or selected */
  
    // commented out temporarilty to avoid errors
    
    /* public void draw(Canvas canvas){
    
    // TODO: Copied from old code, this particular version will draw a square for the PS icon
    // and then will fill in the square if highlighted or selected with color
    
    
    		super.onDraw(canvas); // Draw the regular stuff

		// Draw in 3 steps:
		// 1. Draw background to erase old state
		// 2. Draw this private space's color
		// 3. Draw smaller square if the private space isn't open or being
		// previewed //
		int backgroundColor = R.color.scroll_background;
		RectShape rect = new RectShape();
		ShapeDrawable normalShape = new ShapeDrawable(rect);
		normalShape.getPaint().setColor(color);
		normalShape.setBounds(2, 2, this.getWidth() - 2, this.getHeight() - 2);
		canvas.drawColor(backgroundColor);
		normalShape.draw(canvas);
		RectShape rect2 = new RectShape();
		if (!this.isSelected) {
			ShapeDrawable s = new ShapeDrawable(rect2);
			s.getPaint().setColor(backgroundColor);
			s.setBounds(6, 6, this.getHeight() - 4, this.getHeight() - 6);
			s.draw(canvas);
			showPreview = false;

		} else
			showPreview = true;
    
    } */
    
    // GETTERS
    public Space getSpace(){
        return space;
    }
    
    public boolean isHighlighted(){
        return isHighlighted;
    }
    
    public boolean isSelected(){
        return isSelected;
    }
    
    // SETTERS
    
    /** Set the icon to be highlighted or not (in cases where the icon is tapped) */
    public void setSelected(boolean selected){
        isSelected = selected;
    }
    
    /** Not the same as selected, but merely highlighted
     * for cases where you drag a person's icon over the privatespace icon and you
     * want to see some feedback */
    public void setHighlighted(boolean highlighted){
        isHighlighted = highlighted;
    }
}
