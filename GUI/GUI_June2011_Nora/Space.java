package edu.cornell.opencomm;

import java.util.LinkedList;
import android.util.Log;
import android.content.Context;

/** A Space is a chat room that holds a group of people who can talk to 
 * one another. This is just the room object holding the data, its contents are displayed in a SpaceView 
 * 
 * A "mainspace" is a space where you can drag in anyone you like. You have sole control over this room
 * TODO should we change this?  
 */

public class Space{
	private static String LOG_TAG="OC_Space"; // for error checking
	Context context;
	LinkedList<Person> allPeople; // The people who are in this Space
    int spaceID; // the ID # that the network will use to identify this space
    boolean isMainSpace; // Is true if this is a mainspace and NOT a privateSpace
    Person moderator; // the person who has the power to manage the PrivateSpace, this person has special priveleges
	SpaceView screen; // The SpaceView object(UI screen) that shows this Space (room) on the UI screen
    PrivateSpaceView bottomIcon; // The icon at the scrollable bottom that represents this Space
    public static LinkedList<Space> allSpaces= new LinkedList<Space>(); // All the rooms that have been created ever
    
	
	/* Constructor: A completely NEW space
     * isMainSpace is true if this is a mainspace, mainspaces should be 
     * initiated in the beginning and should initialize allSpaces
	 * 1) Initialize all variables - create a new 
       2) Add this space to the list of all spaces
       3) Set up a PrivateSpaceView */
	public Space(Context context, boolean isMainSpace, int spaceID, Person moderator){
		//Log.v(LOG_TAG, "Creating a self-created SPACE");
		this.context = context;
        // (1)
		allPeople = new LinkedList<Person>();
        this.spaceID = spaceID;
        this.isMainSpace = isMainSpace;
        this.moderator = moderator;
        // (2)
        allSpaces.add(this);
        // (3) 
        bottomIcon = new PrivateSpaceView(context, this);
	}
    
    /* Constructor: An already existing privateSpace that YOU have just joined
     * 1) existingPeople - list of people who are already in the privatespace
     * 2) the ID of the privatespace so that the network can identify it with the others
     * Make sure to still make a SpaceView object and pass the existingPeople in,
     * also add this space to allSpaces. Don't forget to set up a PrivateSpaceView */
    public Space(Context context, LinkedList<Person> existingPeople, int spaceID, Person moderator){
    	//Log.v(LOG_TAG, "Creating an already existing SPACE");
    	this.context = context;
        allPeople = existingPeople;
        this.spaceID = spaceID;
        this.isMainSpace = false;
        this.moderator = moderator;
        allSpaces.add(this);
        bottomIcon = new PrivateSpaceView(context, this);
    }
    
    
    /* Create the SpaceView (screen) for this space */
    public void addSpaceView(SpaceView sv){
    	//Log.v(LOG_TAG, "Added this spaceview to Space " + spaceID);
    	screen = sv;
    	sv.invalidate();
    }
	
    /* Delete this private space from the static list of existing Spaces (allSpaces),
     * however you cannot delete a mainspace, also delete this space's corresponding
     * PS icon */
    public void deletePrivateSpace(){
        if(!isMainSpace){
            allSpaces.remove(this); 
            bottomIcon.deleteThisPSView();
        }
    }
    
    /* Add many people to this Space, make sure to add icons to the SpaceView for all these people */
    public void addManyPeople(LinkedList<Person> people){
        for(Person person : people){
            allPeople.add(person);
            screen.addPerson(person);
        }
    }
    
	/* Add this Person to this Space (room), also create a new icon (PersonView) for 
     * add it to this Space's corresponding SpaceView */
	public void addPerson(Person newPerson){
		//Log.v(LOG_TAG, "Person " + newPerson.getUsername() + " was added to Space " + getSpaceID() + "with mainspace=" + isMainSpace());
        allPeople.add(newPerson);
        screen.addPerson(newPerson);
	}
    
	/* Remove a Person from this Space (room), make sure to delete the icon (PersonView)
     * from this Space's corresponding SpaceView*/
	public void removePerson(Person badPerson){
		Log.v(LOG_TAG, "SPACE has " + allPeople.size() + " people");
        allPeople.remove(badPerson);
        Log.v(LOG_TAG, "SPACE now has " + allPeople.size() + " people");
        screen.deletePerson(badPerson);
	}
	
	/* Set the moderator of this space. Cannot change the moderator from YOU if 
	 * this space is a mainspace */
	public void setModerator(Person moderator){
		if(!isMainSpace())
			this.moderator = moderator;
	}
    
	
    //GETTERS
    
    /* Return the list of all the people who are in this space */
    public LinkedList<Person> getAllPeople(){
        return allPeople;
    }
    /* Return true if this Space is a mainspace, false otherwise */
    public boolean isMainSpace(){
        return isMainSpace;
    }
    /* Return the id of this space */
    public int getSpaceID(){
    	return spaceID;
    }
    /* Return the moderator of this space, (will be YOU if this is a mainspace) */
    public Person getModerator(){
        return moderator;
    }
    /* Return the SpaceView object/screen of this space */
    public SpaceView getSpaceView(){
        return screen;
    }
    /* Return the bottomIcon (PrivateSpaceView) for this Space */
    public PrivateSpaceView getPSView(){
        return bottomIcon;
    }

}


