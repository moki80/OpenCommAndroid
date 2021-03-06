package edu.cornell.opencomm;

import java.util.LinkedList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author noranq
 * 
 */
public class MainApplication extends Activity {

	// TODO: make mainspace static
	public static Space mainspace = null;
	static Space space; // the space that you are updating
	static LinkedList<Person> allPeople;
	public static final String PS_ID = "edu.cornell.opencomm.which_ps";

	LinearLayout.LayoutParams PSparams = new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT,
			ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);

	public void onCreate(Bundle savedInstanceState) {
		// Create activity and make it listen to XML file
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Spaceview is already specified in XML file
		SpaceView spaceview = (SpaceView) findViewById(R.id.space_view);
		/*
		 * If first starting program, then you always start with the mainchat
		 * room this room must be initialized to hold all people involved in
		 * conversation
		 */
		if (mainspace == null) {
			space = new Space(this);
			mainspace = space;
			initializeMainSpace(space); // initialize people
		}

		/*
		 * If creating a new private space, then need to know which already
		 * existing PrivateSpace it is using
		 */
		else {
			int ID = getIntent().getIntExtra(PS_ID, -1);
			// space = (PrivateSpace)PrivateSpaceView.currentSpaces
			for (PrivateSpaceView pv : PrivateSpaceView.currentSpaces) {
				createPrivateSpaceIcon(pv);
				PrivateSpace p = (PrivateSpace) (pv.getSpace());
				if (p.getID() == ID) {
					space = p;
					p.setActivity(this);
				}
			}
		}
		space.addSpaceView(spaceview);
		initializeButtons();
		initializePrivateSpaces();
	}

	/*
	 * onStart - draw privatespaceviews and personviews again, need to update in
	 * case model was changed
	 */
	protected void onStart() {
		super.onStart();
	}

	protected void onStop() {
		super.onStop();
	}

	/**
	 * If is the first main space created (for main conference chat, then make
	 * sure that everyone is added to this list space. Create everybody to begin
	 * with.
	 */
	public void initializeMainSpace(Space mainspace) {
		Person nora = new Person("Nora", "She's the best!", R.drawable.nora);
		Person najla = new Person("Najla", "Is dating Jack Sparrow",
				R.drawable.naj);
		Person makoto = new Person("Makoto", "Doesn't respond to texts",
				R.drawable.mak);
		Person risa = new Person("Risa", "Is destined to marry her dog",
				R.drawable.risa);
		// add all people to static array of everybody
		allPeople = new LinkedList<Person>();
		allPeople.add(nora);
		allPeople.add(najla);
		allPeople.add(makoto);
		allPeople.add(risa);
		// add all the people to this main chat space
		mainspace.add(nora);
		mainspace.add(najla);
		mainspace.add(makoto);
		mainspace.add(risa);
	}

	/** Add touch listeners to the buttons */
	public void initializeButtons() {
		// Initialize main, add, and trash button functionality
		// TODO: the add button is temporary

		// set listener to main button
		Button mainButton = (Button) findViewById(R.id.main_button);
		mainButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent evt) {
				switch (evt.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					// returns you to the main conversation
					Space main = MainApplication.mainspace;
					Intent intent = ((MainApplication) (main.context))
							.getIntent();
					startActivity(intent);
					finish();
					break;
				}
				return false;
			}
		});

		// temporary button that adds private spaces
		Button addButton = (Button) findViewById(R.id.add_button);
		addButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent evt) {
				switch (evt.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					// create a new privateSpace icon
					createNewPrivateSpace();
					break;
				}
				return false;
			}
		});

		// set listener to trash button
		Button trashButton = (Button) findViewById(R.id.trash_button);
		trashButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent evt) {
				switch (evt.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					// remove private spaces if they are highlighted
					// TODO for now only deletes one at a time, but later should
					// delete multiple
					PrivateSpaceView deleteSpace = null;
					for (PrivateSpaceView p : PrivateSpaceView.currentSpaces) {
						if (p.isSelected())
							deleteSpace = p;
					}
					removePrivateSpace(deleteSpace);

					// remove people from private spaces (only works in private
					// space)
					// TODO for now only deletes one at a time, but later can
					// delete multiple
					if (space instanceof PrivateSpace) {
						PersonView deleteIcon = null;
						for (PersonView icon : space.getPeople()) {
							if (icon.isSelected())
								deleteIcon = icon;

						}
						removeIcon(space, deleteIcon);
					}
					break;
				}
				return false;
			}
		});
	}

	/**
	 * Automatically initialize all private spaces to the bottom bar no matter
	 * where
	 */
	public void initializePrivateSpaces() {
		/*
		 * if(PrivateSpaceView.currentSpaces!=null){ for(PrivateSpaceView pv :
		 * PrivateSpaceView.currentSpaces){ createPrivateSpaceIcon(pv); } }
		 */
	}

	/** Draw PrivateSpace icon to the screen, add to XML file */
	public void createPrivateSpaceIcon(PrivateSpaceView pv) {
		LinearLayout bottomBar = (LinearLayout) findViewById(R.id.privateSpaceLinearLayout);
		PrivateSpaceView copy = pv.clone();
		copy.setLayoutParams(PSparams);
		copy.setMinimumWidth(50);
		bottomBar.addView(copy);
		bottomBar.invalidate();
	}

	/** Create a new private space, and make icon appear at bottom of screen */
	public void createNewPrivateSpace() {
		LinearLayout bottomBar = (LinearLayout) findViewById(R.id.privateSpaceLinearLayout);
		PrivateSpace p = new PrivateSpace(this);
		PrivateSpaceView pv = p.getPrivateSpaceView();
		pv.setLayoutParams(PSparams);
		pv.setMinimumWidth(50);
		bottomBar.addView(pv);
		bottomBar.invalidate();
	}

	/**
	 * remove a private space button from the screen, and delete that
	 * privateSpace
	 */
	public void removePrivateSpace(PrivateSpaceView pv) {
		LinearLayout bottomBar = (LinearLayout) findViewById(R.id.privateSpaceLinearLayout);
		bottomBar.removeView(pv);
		bottomBar.invalidate();
		PrivateSpaceView.currentSpaces.remove(pv);
		PrivateSpaceView.privateSpaceCounter--;
	}

	/** Creates a new activity for this PrivateSpace */
	public void openNewPSActivity(PrivateSpace p) {
		Intent intent = new Intent(MainApplication.this, MainApplication.class);
		int PSid = p.getID();
		intent.putExtra(PS_ID, PSid);
		startActivity(intent);
	}

	/** Reopens the activity for this PrivateSpace */
	public void restartPSActivity(PrivateSpace p) {
		Intent intent = (p.getActivity()).getIntent();
		startActivity(intent);
		finish();
	}

	/** Remove an icon from this PrivateSpace */
	public void removeIcon(Space space, PersonView icon) {
		// remove from private space object
		(space.getPeople()).remove(icon);
		// remove from privateSpaceView
		LinkedList<PersonView> icons = (space.getSpaceView()).getIcons();
		icons.remove(icon);
		LinearLayout screen = (LinearLayout) findViewById(R.id.space_view);
		screen.invalidate();

	}

	public static void showPreview(LinkedList<PersonView> psv) {
		space.spaceView.setPreview(psv);

	}

}
