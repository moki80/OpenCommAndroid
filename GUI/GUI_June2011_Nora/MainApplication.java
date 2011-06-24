package edu.cornell.opencomm;

import java.util.LinkedList;

/** The MainApplication handles and manages the PrivateSpaces for every 
 * Person involved. Receives its notifications from the GUI, and then
 * updates the data of the private space, and talks with the network. */
public class MainApplication{
    // Your buddy list! Has been previously saved from the network 
    // This may not be the best place to put it, but for now seems logical
    public static LinkedList allBuddies; 
    // the official mainSpace, the one space that has EVERYBODY in it
    public static Space mainspace= null; 
    // the person object that represents YOU, the user of this program 
    public static Person user_you;
    // The space that is currently on the screen
    public static Space current_space;
    
    /** Start the application! You are given the user's id (username), so...
     * 1) Connect with network
     * 2) Open first Space (the mainspace) and put this user in it
     * 3) Fetch the buddy list from network
     * 4) Create the main buttons: menu, trash, add private space 
    public MainApplication(String personID_username){
        // (1) TODO network: connect with network
        // (2) 
        if (mainspace == null){
            // YOU are the moderator of this new space
            Person you = new Person(personID_username, "", -1, "") // -1 for image means no image
            init_createPrivateSpace(true);
        }
        // (3) TODO network: fetch buddy list from network
        // (4)
        initializeButtons();
    }
    
    /** You are exiting the application! Definitely tell the network so it can tell
     * EVERYONE else and remove you from rooms that you were in. Save any history
     * (new friends made, recordings of discussions, chats, etc.), and delete the
     * the unimportant data. Delete privateSpaces that you were moderator of */
    public void disconnect(){
        // TODO network: needs find out where to store this info
        // TODO network: needs to disconnect
    }
    
    /** YOU created a new PrivateSpace, therefore can call createPrivateSpace
     * however in addition need to notify the network of the newly created
     * Private Space */
    public Space init_createPrivateSpace(boolean isMainSpace){
        // TODO network: notify network of new private space to create
        // TODO network: Network should return a spaceID and store it in this variable
        int newSpaceID=0;
        return createPrivateSpace(true, isMainSpace, null, newSpaceID, user_you);
    }
    
    /** Create a new Private Space, makes sure to put yourself (Person) in the 
     * Private Space. The creation can be done in the PrivateSpace constructor, 
     * if preferred but called from here. 
     * This method also called if someone else invited you to a PrivateSpace and 
     * accepted. If is an already existing privateSpace, then existing_buddies will
     * hold all the people already in that PrivateSpace. If YOU are creating this
     * privateSpace, then existing buddies should be null and the spaceID should be -1
     * to indicate that you should create a new ID */ 
	public Space createPrivateSpace(boolean ICreatedThis, boolean isMainSpace, LinkedList<Person> existing_buddies, int spaceID, Person moderator){
        if (ICreatedThis) // then is a completely new privatespace
            return new Space(isMainSpace, spaceID, moderator);
        else
            return new Space(existing_buddies, spaceID, moderator);
    }
    
    /** YOU remove an existing PrivateSpace, with the intention of deleting this 
     * PrivateSpace for EVERYBODY who was taking part in it. Therefore does same thing
     * as deletePrivateSpace except in addition needs to notify the network
     * of this deletion, the network will delete this privatespace for everyone */
    public void init_deletePrivateSpace(Space SpaceToDelete){
        //TODO network: notify the network to delete this space
        deletePrivateSpace(SpaceToDelete);
    }
    
    /** Remove an existing PrivateSpace for yourself. Make sure to also delete the 
     * PrivateSpace's corresponding PrivateSpaceView and SpaceView. 
     * This method called if someone else deleted a PrivateSpace that you
     * were a part of, or if you decided to leave */
    public void deletePrivateSpace(Space SpaceToDelete){
        SpaceToDelete.deletePrivateSpace();
    }
    
    /** Need to add the new PrivateSpace button to the bottom GUI by altering the XML code */
    public void addPrivateSpaceButton(PrivateSpaceView psv){
        // TODO: Copied from earlier code, might need some fixing
        /*
        LinearLayout bottomBar = (LinearLayout) findViewById(R.id.privateSpaceLinearLayout);
		PrivateSpace p = new PrivateSpace(this);
		PrivateSpaceView pv = p.getPrivateSpaceView();
		pv.setLayoutParams(PSparams);
		pv.setMinimumWidth(50);
		bottomBar.addView(pv);
		bottomBar.invalidate(); */
    }
    
    /** Need to delete the this PrivateSpace button to the bottom GUI by altering the XML code */
    public void delPrivateSpaceButton(PrivateSpaceView psv){
        // TODO: copied form earlier code, might need some fixing
        
      /*  LinearLayout bottomBar = (LinearLayout) findViewById(R.id.privateSpaceLinearLayout);
		bottomBar.removeView(pv);
		bottomBar.invalidate();
		PrivateSpaceView.currentSpaces.remove(pv);
		PrivateSpaceView.privateSpaceCounter--;
		// Tell network that you are deleting this private space
		sendDeleteNewPrivateSpace(pv.getId()); */
    }
    
    /** YOU invited this person to the PrivateSpace, so invite them over the network, you must 
     * still wait for their response though 9through accept_invigte() or decline_invite()) before you
     * can take any further action. 
     * If is a mainspace then no need for an invitation, just add it to your own (addPerson). 
     * Make sure every time you add a person to confirm if this person is real in your buddylist */
    public void init_addPerson(Space space, Person person){
        if(space.isMainSpace())
            addPerson(space, person);
        send_invite(space, person);
    }
    
    /** You want to invite someone to chat in this group, so invite them over the network, you must
     * still wait for their response though (through accept_invite() or decline_invite()) before you
     * can take any action */
    public void send_invite(Space space, Person person){
        // TODO network: notify netowrk that you have invited "person" to "space"
    }
    
    /** Add this new person to this Space, make sure to also make an icon for this person and add it.
     * Could be situations where: you added this person to mainspace through buddylist,
     * this person joined a privatespace you are in (without you knowing), you invited this
     * this person to the privatespace and s/he accepted */
    public void addPerson(Space space, Person person){
        space.addPerson(person);
    }
    
    /** Accept invitation to join a PrivateSpace. Network needs to know to tell everyone
     * that you joined and needs to update the privatespace */
    public void accept_invite(Space space){
        // TODO network: notify network that you accepted the invitation, the Network
        // should later get back to you (through addPerson()) to update your privtaespaces
    }
    
    /** Decline invitation to join a PrivateSpace. Network needs to know so that it can stop
     * waiting for your response */
    public void decline_invite(Space space){
        // TODO network: notify network that you declined the invitation
    }
    
    /** YOU kick this person out of the PrivateSpace, BUT only if you are moderator,
     * make sure to tell the network */
    public void init_deletePerson(Space space, Person person){
        if(space.getModerator()==user_you){
            //TODO network: tell the the network that this person is no longer in this space, so it 
            // can send notifications to everyone in that space to delete that person
            deletePerson(space, person);
        }
    }
    
    /** Remove this person from this space, take away that person's icon from the SpaceView
     * as well. Could be situations where: you do not want this person in your mainspace,
     * this person removed him/herself from the privatespace, the privatespace got deleted, 
     * you kicked someone out of the group (if you are moderator) */
    public void deletePerson(Space space, Person person){
        space.removePerson(person);
    }
    
    /** Notify the network with the icon you moved so that it can update the sound simulation */
    public void movedPersonIcon(Space space, PersonView icon, int x, int y){
        // TODO network: send this to network to update the sound spatialization
    }
    
    /** Notify the network that you have opened up a different space so that it can update the 
     * sound spatialization */
    public void setCurrentSpace(Space newSpace){
        // TODO network: notify network of the currentspace switch and adjust sound spatialization
        current_space = newSpace;
    }
    
    /** Create the buttons (that are android widgets and put into xml)
     * These buttons will include the main, menu, and add buttons
     * Add touch listeners to the buttons */
	public void initializeButtons() {
    
        // TODO: Copied form earlier code - might need some fixing
    
		// Initialize main, add, and trash button functionality
		// TODO: the add button is temporary

		// set listener to main button
	/*	Button mainButton = (Button) findViewById(R.id.main_button);
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
						// if you are deleting someone from a privatespace then tell the network
						
						
					}
					break;
				}
				return false;
			}
		}); */
	}
    
}
