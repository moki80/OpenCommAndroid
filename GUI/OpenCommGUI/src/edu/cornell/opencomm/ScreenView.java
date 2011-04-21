package edu.cornell.opencomm;

import java.util.LinkedList;
import java.util.ListIterator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ScreenView extends LinearLayout {

	private Context context;
	// the group of people this is displaying
	private PrivateSpace space;
	private LinkedList<PersonView> icons;
	int x = 0, y = 0, w = 320, h = 430;
	private PersonView selectedIcon;
	// position of the icon before you moved it
	int initialX = 0, initialY = 0;
	// NORA - I guestimated the mainScreenH and adjusted it, we might need a
	// better method for this the height of the main screen (the whole screen
	// height - bar height)
	int mainScreenH = 340;
	// the Private Space that is being hovered over
	PrivateSpace hoveredPrivSpace = null;

	public ScreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	public ScreenView(Context context, PrivateSpace space) {
		super(context);
		this.context = context;
		this.space = space;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	public void setSpace(PrivateSpace s) {
		space = s;
		createIcons(s);
	}

	/**
	 * Takes the people who are in this private space, and creates an icon
	 * (PersonView) for them for only this screen
	 */
	public void createIcons(PrivateSpace space) {
		icons = new LinkedList<PersonView>();
		LinkedList<Person> people = space.getPeople();

		ListIterator<Person> i = people.listIterator();
		while (i.hasNext()) {
			Person p = i.next();
			icons.add(new PersonView(context, p, (int) (Math.floor(w
					* Math.random())), (int) (Math.floor(mainScreenH
					* Math.random())), 55, 55));
			i = people.listIterator(i.nextIndex());
		}

	}

	protected void onDraw(Canvas canvas) {
		// draw background
		canvas.drawColor(space.getColor());

		// draw people icons
		ListIterator<PersonView> i = icons.listIterator();
		while (i.hasNext()) {

			PersonView icon = i.next();
			icon.draw(canvas);
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		int mouseX = (int) event.getX();
		int mouseY = (int) event.getY();

		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			selectedIcon = null;
			ListIterator<PersonView> i = icons.listIterator();
			while (i.hasNext() == true) {
				PersonView icon = i.next();
				if (icon.clickedInside(mouseX, mouseY)) {
					selectedIcon = icon;

					initialX = icon.getX();
					initialY = icon.getY();
				}
				i = icons.listIterator(i.nextIndex());
			}
			break;
			
		case MotionEvent.ACTION_MOVE:
			// If a person icon is selected, then move the icon to the current
			// position
			if (selectedIcon != null) {
				selectedIcon.moved = true;
				selectedIcon.setX(mouseX - (selectedIcon.getW() / 2));
				selectedIcon.setY(mouseY - (selectedIcon.getH() / 2));
				//
				// if icon is dragged over private space, then highlight that
				// private space icon
				if (hoveredPrivSpace == null) {
					for (PrivateSpace p : PrivateSpace.currentSpaces) {
						if (p.contains(mouseX, mouseY)) {
							p.setHovered(true);
							hoveredPrivSpace = p;
						}
					}
				} else if (hoveredPrivSpace != null) {
					if (!hoveredPrivSpace.contains(mouseX, mouseY)) {
						hoveredPrivSpace.setHovered(false);
						hoveredPrivSpace = null;
					}
				}
			}
			break;
			
		case MotionEvent.ACTION_UP:
			if (selectedIcon != null) {
				if (!selectedIcon.moved) {
					selectedIcon.changeSelected();
				}
				selectedIcon.moved = false;
				for (PrivateSpace p : PrivateSpace.currentSpaces) {
					if (p.contains(mouseX, mouseY)) {
						p.add(selectedIcon.getPerson());
						break;
						// return true;
					}
				}
				// Draw in bounds
				if (mouseY >= mainScreenH && mouseY <= h) {
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
