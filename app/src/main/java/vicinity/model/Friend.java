package vicinity.model;
import android.content.ContentValues;
import java.sql.SQLException;
import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import vicinity.ConnectionManager.WiFiP2pService;

/**
 * A structure class for holding friends information
 * this class extends WiFiP2pService
 */
public class Friend extends WiFiP2pService{

    private static final String TAG = "FriendClass";


    private ArrayList<VicinityMessage> _privateMessages;
    private String _aliasName;

    /**
     * Default public constructor
     */
    public Friend (){

    }

    /**
     * Public constructor
     * @param context application context
     * @param username A String that contains the friend's name
     * @param _id A User's device ID
     */
    public Friend(Context context,String username,String _id) {
        //super(username);
        Globals.dbH = new DBHandler(context);

    }


    /**
     *
     * Setters-getters
     */
    public String getAliasName(){
        return this._aliasName;
    }
    public void setAliasName(String newName){_aliasName=newName;}
    public ArrayList<VicinityMessage> getPrivateMessages(){
        return this._privateMessages;
    }


    //INCOMPLETE: must add column: AliasName to the db in Friend's table. -AFNAN
    /**
     * This method calls nameValidation from MainController to validate the new username
     * then adds it to the database as an alias name
     * @param aliasName A string that contains a new alias name for a certain friend
     * @param friendID A string that contains the id of the friend with the alias name
     * @return isUpdated A boolean that is true if the name was changed, false otherwise
     */
    public boolean changeName(String aliasName, String friendID) throws SQLException{
        boolean isUpdated=false;

        //Validate the given Alias name first
        if(Globals.controller.nameValidation(aliasName))
        {
            this._aliasName = aliasName;
            try{
                Globals.database = Globals.dbH.getReadableDatabase();
                Globals.dbH.openDataBase();
                ContentValues args = new ContentValues();
                args.put("aliasname", aliasName);
                isUpdated= Globals.database.update("User", args, "_id=" + friendID, null)>0;
                Globals.dbH.close();
            }
            catch(SQLiteException e){
                Log.i(TAG,"SQLiteException > Friend > ChangeName");
                e.printStackTrace();
            }
        }
        Log.i(TAG,"Is Updated? "+isUpdated);
        return isUpdated;
    }


    public boolean sendMessage(VicinityMessage newMessage){
        return false;
    }


}
