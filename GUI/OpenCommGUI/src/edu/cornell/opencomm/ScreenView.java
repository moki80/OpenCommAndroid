package edu.cornell.opencomm;

import java.util.LinkedList;
import java.util.ListIterator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ScreenView extends LinearLayout {
	
	public ScreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	
	public ScreenView(Context context, PrivateSpace space){
		super(context);
		this.context = context;
		this.space = space;
		setFocusable(true);
		setFocusableInTouchMode(true);	
		
//		createIcons(space);
	}

	private Context context;
	private PrivateSpace space; // the group of people this is displaying
	private LinkedList<PersonView> icons;
	int x =0, y=0, w=320, h = 430;
	private PersonView selectedIcon;
	int initialX=0, initialY=0; // position of the icon before you moved it
	// NORA - I guestimated the mainScreenH and adjusted it, we might need a better method for this
	int mainScreenH = 340; // the height of the main screen (the whole screen height - bar height)
	
	
	
	public void setSpace(PrivateSpace s){
		space = s;
		createIcons(s);
	}
	
	/** Takes the people who are in this private space, and creates an icon (PersonView) for them 
	 * for only this screen */
	public void createIcons(PrivateSpace space){
		icons = new LinkedList<PersonView>();
		LinkedList<Person> people = space.getPeople();
		
		ListIterator<Person> i= people.listIterator();
		while(i.hasNext()){
			Person p = i.next();
			icons.add(new PersonView(context, p, (int)(Math.floor(w*Math.random())), 
					(int)(Math.floor(mainScreenH*Math.random())), 50, 50));
			i=people.listIterator(i.nextIndex());
		}
		
	}
	
	protected void onDraw(Canvas canvas){
		// draw background
		canvas.drawColor(space.getColor()); 
		// draw people icons
		ListIterator<PersonView> i= icons.listIterator();
		while(i.hasNext()){
			PersonView icon = i.next();
			canvas.drawBitmap(icon.getIcon(), icon.getX(), icon.getY(), null);
		}
	} 
	
	public boolean onTouchEvent(MotionEvent event){
		int eventaction = event.getAction();
		int mouseX = (int)event.getX();
		int mouseY = (int)event.getY();
		
		
		switch(eventaction){
			case MotionEvent.ACTION_DOWN:
				selectedIcon=null;
				ListIterator<PersonView> i= icons.listIterator();
				while(i.hasNext() == true){
					PersonView icon = i.next();
					if(icon.clickedInside(mouseX, mouseY)){
						selectedIcon = icon;
						initialX = icon.getX();
						initialY = icon.getY();
					}
					i= icons.listIterator(i.nextIndex());
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if(selectedIcon!=null){
					selectedIcon.setX(mouseX-(selectedIcon.getW()/2));
					selectedIcon.setY(mouseY-(selectedIcon.getH()/2));
					// Nora experimenting
				/*	for(PrivateSpace p : PrivateSpace.currentSpaces){
						//int x = p.getLeft();
						if(mouseX>=p.getLeft() && mouseX<=p.getRight() && mouseY>=p.getTop() && mouseY<=p.getBottom())
							p.clicked2(p);
					} */
				}
				break;
			case MotionEvent.ACTION_UP:
				if(selectedIcon!=null){
					if(mouseY>=mainScreenH && mouseY<=h){
						selectedIcon.setX(initialX);
						selectedIcon.setY(initialY);
					}
					selectedIcon = null;
				}
				break;
		}  
		invalidate();
		return true;
	}

}
