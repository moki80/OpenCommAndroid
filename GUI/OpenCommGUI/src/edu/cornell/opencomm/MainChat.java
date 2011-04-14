package edu.cornell.opencomm;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/** The main chat screen with EVERYBODY'S icon in it 
 * 
 * 1) Menu button @ bottom-left, should open up into an "Add Private Space" and "Delete PrivateSpace"
 * 2) Bottom bar with icons representing all existing Private Spaces
 * 3) Main button @ bottom-right, should take you back to this window
 */



public class MainChat extends Activity {
    /** Called when the activity is first created. */
	private ScreenView root;
	private MainSpace space;
	
    @Override
    /** Initialize all views (buttons, bars, etc.) and set OnClickListeners for necessary components
     * 
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(){

			public void uncaughtException(Thread thread, Throwable ex) {
				Log.e("TAG_STRING", "Uncaught Exception Caught", ex);
			}
        
        });
        setContentView(R.layout.main);

        // Create a space with everyone in it (the main space)
        space = new MainSpace(this);
        space.initializeEveryone(); // add everyone to it!
        
        root = (ScreenView) findViewById(R.id.space_view);
        root.setSpace(space);
        root.invalidate();
//        root = new ScreenView(this, space);
//        setContentView(root);
    }
}