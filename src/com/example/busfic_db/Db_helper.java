package com.example.busfic_db;

// All Database related queries are written here

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Db_helper extends SQLiteOpenHelper{


  Context context;

  private static final String DB_NAME = "Bus_V14.db";
  private static final int DB_VERSION_NUMBER = 1;
 
  public static final String DB_BUS_STOPS= "bus_stops";
  public static final String DB_BUS_INFO = "bus_info";
  public static final String DB_BUS_ROUTES = "bus_routes";
  
  private SQLiteDatabase sqlIns = null;
   
  public Db_helper(Context context)
  {
      super(context, DB_NAME, null, DB_VERSION_NUMBER);
      this.context = context;
      Log.i("constructor", "Calling Constructor...");
  }
  
  //Calls queries present in the asset folder
  public void onCreate(SQLiteDatabase database) 
  {
	 Log.i("onCreate", "DB initializing...");
  	 executeSQLScript(database, "stop.sql");
  }

  public void onUpgrade(SQLiteDatabase database, int oldVersion,
      int newVersion) {
	  Log.i("onUpgrade", "Calling onUpgrade...");
  }
  
  
  public void openDB() throws SQLException
  {
      Log.i("openDB", "Checking sqliteDBInstance...");
      if(this.sqlIns == null)
      {
    	  //onCreate(sqlIns);
          Log.i("openDB", "Creating sqliteDBInstance...");
          this.sqlIns = this.getWritableDatabase();
      }
  }
  
    
  public void closeDB()
  {
      if(this.sqlIns != null)
      {
          if(this.sqlIns.isOpen())
              this.sqlIns.close();
      }
      
     
  }
  
  //Get Count Function
  public Cursor getCount_Stops() {
      Cursor c = sqlIns.rawQuery("SELECT * FROM "+DB_BUS_STOPS, null);
      return c;

  }
  
  //Executes the queries from a file
  private void executeSQLScript(SQLiteDatabase database,String dbname) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    byte buf[] = new byte[1024];
		    int len;
		    AssetManager assetManager = context.getAssets();
		    InputStream inputStream = null;
		         
		    try{
		        inputStream = assetManager.open(dbname);
		        while ((len = inputStream.read(buf)) != -1) {
		            outputStream.write(buf, 0, len);
		        }
		        outputStream.close();
		        inputStream.close();
		             
		        String[] createScript = outputStream.toString().split(";");

		        for (int i = 0; i < createScript.length; i++) {
		                String sqlStatement = createScript[i].trim();
	        
		                if (sqlStatement.length() > 0) {
		                    database.execSQL(sqlStatement + ";");
		                }
		        }
		    } catch (IOException e){
		        // TODO Handle Script Failed to Load
		    } catch (SQLException e) {
		        // TODO Handle Script Failed to Execute
		    }
		}
  
  //SOME Functions
   
  /*public void insertAlert(String dest,Integer kms)
  {
	  
	      
      ContentValues values = new ContentValues();
	
      	System.out.println(dest+kms);
      	
          values.put(COLUMN_DEST,dest);
          values.put(COLUMN_KMS,kms);
          values.put(COLUMN_VALID,1);
                 
          sqlIns.insert(TABLE_ALERT, null, values);
          
          Log.i(this.toString() + "  insertPlace", "Inserting: " + dest);
      
  }
  
  public boolean checkAlert(String nPlace, int ndist)
  {
	  int dist,valid,flag;
	  Cursor cursor = sqlIns.rawQuery("SELECT "+COLUMN_KMS+","+COLUMN_VALID+" FROM "+TABLE_ALERT+ " where "+COLUMN_DEST+"='"+nPlace+"'", null);
	  //Cursor cursor=sqlIns.query(TABLE_ALERT, new String[]{COLUMN_KMS,COLUMN_VALID}, nPlace+"="+COLUMN_DEST,
		//	  				null, null, null, null);
	  ndist=ndist/2;
	  while (cursor.moveToNext())
      {
    	  System.out.println("Number of Places"+cursor.getCount());
           dist = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_KMS)));
           valid = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_VALID)));
           System.out.println(dist+valid);
           
           if(dist>=ndist && valid==1)
           {
        	   return true;
           }
      }
	return false;
  }
  */
  
  
} 