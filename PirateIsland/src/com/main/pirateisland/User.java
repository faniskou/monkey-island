package com.main.pirateisland;

public class User {
	
	 //private variables
    String _USERNAME;
    String _AGE;
    int _FAILSLEVEL1;
    int _FAILSLEVEL2;
    int _FAILSLEVEL3;
    int _FAILSLEVEL4;
    int _FAILSLEVEL5;
    int _FAILSLEVEL6;
    int _USERNEGATIVESCORE;
    int _MAXLEVEL;
    int _CURRENTLEVEL;
    int _DIFFICULTY;

    
    
   //  Empty constructor
    public User(){
         
    }
    public User(
    	    String USERNAME,
    	    String AGE,
    	    int FAILSLEVEL1,
    	    int FAILSLEVEL2,
    	    int FAILSLEVEL3,
    	    int FAILSLEVEL4,
    	    int FAILSLEVEL5,
    	    int FAILSLEVEL6,
    	    int USERNEGATIVESCORE,
    	    int MAXLEVEL,
    	    int CURRENTLEVEL,
    	    int DIFFICULTY
    		
    		){
          this._USERNAME = USERNAME;
          this._AGE = AGE;
          this._FAILSLEVEL1=FAILSLEVEL1;
          this._FAILSLEVEL2=FAILSLEVEL2;
          this._FAILSLEVEL3=FAILSLEVEL3;
          this._FAILSLEVEL4=FAILSLEVEL4;
          this._FAILSLEVEL5=FAILSLEVEL5;
          this._FAILSLEVEL6=FAILSLEVEL6;
          this._USERNEGATIVESCORE=USERNEGATIVESCORE;
          this._MAXLEVEL=MAXLEVEL;
          this._CURRENTLEVEL=CURRENTLEVEL;
          this._DIFFICULTY=DIFFICULTY;
    }
    

}
