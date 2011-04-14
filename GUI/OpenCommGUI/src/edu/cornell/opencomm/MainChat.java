package edu.cornell.opencomm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/** The main chat screen with EVERYBODY'S icon in it 
 * 
 * 1) Menu button @ bottom-left, should open up into an "Add Private Space" and "Delete PrivateSpace"
 * 2) Bottom bar with icons representing all existing Private Spaces
 * 3) Main button @ bottom-right, should take you back to this window
 */



public class MainChat extends Activity {
    /** Called when the activity is first created. */
	private LinearLayout root;
	
    @Override
    /** Initialize all views (buttons, bars, etc.) and set OnClickListeners for necessary components
     * 
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
    }
}