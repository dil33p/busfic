package com.example.busfic_db;

// HOME SCREEN

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.Toast;


public class Home extends Activity {

	private Db_helper dbh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		
		//Creating object for Db class
		dbh=new Db_helper(Home.this);
        dbh.openDB();
        
        Toast.makeText(getApplicationContext(), " DB is created ", Toast.LENGTH_SHORT).show();
          
        //Sample function accessing local Db
        Cursor cursor = dbh.getCount_Stops();
        System.out.println("No of records " +cursor.getCount());
        Toast.makeText(getApplicationContext(), cursor.getCount() +" Stops got inserted", Toast.LENGTH_SHORT).show();
	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
