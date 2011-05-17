package edu.cornell.opencomm;

import android.content.Context;

/**
 * Hold information about private space:
 * 
 * 1) Private Space icon (really the color) 2) People in private space
 * 
 * @author jnf34
 * 
 */

public class PrivateSpace extends Space {
	PrivateSpaceView psv;
	MainApplication activity = null;
	static int numPrivateSpaces = 0;
	int idNumber;
	private String mucName = "";

	public PrivateSpace(Context c) {
		super(c);
		psv = new PrivateSpaceView(context, this);
		PrivateSpaceView.currentSpaces.add(psv);
		idNumber = numPrivateSpaces;
		numPrivateSpaces++;
	}
	
	private PrivateSpace(){
		
	}
	
	public PrivateSpace clone(){
		PrivateSpace p = new PrivateSpace();
		p.idNumber = this.idNumber;
		p.psv = null;
		p.mucName = this.mucName;
		return p;
	}

	public PrivateSpaceView getPrivateSpaceView() {
		return psv;
	}

	public MainApplication getActivity() {
		return activity;
	}

	public void setActivity(MainApplication activity) {
		this.activity = activity;
	}

	public int getID() {
		return idNumber;
	}

	/**
	 * @return the mucName where chat is held on the xmpp server
	 */
	public String getMucName() {
		return mucName;
	}

	/**
	 * @param mucName the mucName which is associated with this private space at the XMPP server
	 */
	public void setMucName(String mucName) {
		this.mucName = mucName;
	}

}
