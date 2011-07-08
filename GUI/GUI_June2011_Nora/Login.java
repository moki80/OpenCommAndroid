package edu.cornell.opencomm;

import android.app.Activity;
import android.os.Bundle;

public class Login extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    /* NEED:
     * 1) Have username slot to type in
     * 2) Have password slot to type in
     * 3) Have a clickable submit button
     * 4) Look up the accounts of people in the network
     * 5a) If found, then call the Main Application activity
     * 5b) If not found, then refresh same menu with a message that says "Oops" or something like that 
     */
    
   
}