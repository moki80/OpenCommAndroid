package edu.cornell.opencomm;

import java.util.LinkedList;
import android.content.Context;
import android.util.AttributeSet;

/**
 * The main space for the entire application,
 * it has added functionality compared to the private spaces like
 * the ability to initialize everyone in the space
 * @author jnfrye
 *
 */
class MainSpace extends PrivateSpace {
	
	public static final int MAINSPACE_BG = R.color.main_background;
	
	
	public MainSpace(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();//Not the same init() as in Private space
	}
	
	public MainSpace(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();//Not the same init() as in Private space
	}
	
	public MainSpace(Context context) {
		super(context);
		init(); //Not the same init() as in Private space
	}
	
	private void init(){
		
	}
	
	/** Initialize everyone, intended only for the main chat */
	public void initializeEveryone(){
		peopleInSpace = new LinkedList<Person>(); 
		peopleInSpace.add(new Person("Nora", "She's the best!", R.drawable.nora));
		peopleInSpace.add(new Person("Najla", "Is dating Jack Sparrow" , R.drawable.naj));
		peopleInSpace.add(new Person("Makoto", "Doesn't respond to texts", R.drawable.mak));
		peopleInSpace.add(new Person("Risa", "Is destined to marry her dog", R.drawable.risa));
	}

	@Override
	public int getColor() {
		return MainSpace.MAINSPACE_BG;
	}
	
	
}
