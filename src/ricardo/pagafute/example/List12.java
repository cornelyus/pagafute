/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ricardo.pagafute.example;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.LoaderActionBarItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Demonstrates the using a list view in transcript mode
 *
 */
public class List12 extends GDActivity implements OnClickListener, OnKeyListener {

    private EditText mUserText;
    
    private ArrayAdapter<String> mAdapter;
    private ListView lv1;
    
    private ArrayList<String> mStrings = new ArrayList<String>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setActionBarContentView(R.layout.list_12);
        //setContentView(R.layout.list_12);
        
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings);
        
        lv1 = (ListView) findViewById(R.id.list);
        lv1.setAdapter(mAdapter);
        
        mUserText = (EditText) findViewById(R.id.userText);

        mUserText.setOnClickListener(this);
        mUserText.setOnKeyListener(this);
        
        
        String stringNomes = ReadFromFile(this);
        
        if(stringNomes == null)
        	Log.d("PagaFute", "stringNomes" );
        
        if(stringNomes != null){
        	
        	Log.d("PagaFute", "stringNomes Length > 0" );
        	
        	String data[] = ReadFromFile(this).split(",");
	        for(int i = 0; i<data.length; i++){
	        	
	        	Log.d("PagaFute", "Data i : " +data[i]);
	        	mAdapter.add(data[i]);
	        }
        }
    }
    
    @Override
    protected void onStop(){
    	super.onStop();
    	Log.d("PagaFute", "onStop");
    	
    	String longString = "";
    	
    	for(int i = 0; i<mStrings.size(); i++){
    		Log.d("PagaFute", "String S " + Integer.toString(i) + " : " + mStrings.get(i));
    		if(mStrings.get(i) != ""){
    			Log.d("PagaFute", "String S i : " + mStrings.get(i));
    			longString += mStrings.get(i) + ",";
    		}
		}
    	
    	Log.d("PagaFute", longString);
    
    	WriteToFile(this, longString);
    }

    public void onClick(View v) {
        sendText();
    }

    private void sendText() {
        String text = mUserText.getText().toString();
        Log.d("PagaFute", "text" + text);
        if(text.length() > 0){
//        	Log.d("PagaFute", "mStrings size " + mStrings.size());
        	mAdapter.add(text);
//        	Log.d("PagaFute", "text not empty ");
//        	Log.d("PagaFute", "mStrings size " + mStrings.size());
        }
        else
        	mUserText.setError("Required");
        Log.d("PagaFute", Integer.toString(mStrings.size()));
        mUserText.setText(null);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                    sendText();
                    return true;
            }
        }
        return false;
    }
    
    public void WriteToFile(Context context, String data){
    	 
        FileOutputStream fOut = null;

        OutputStreamWriter osw = null;

        

        try{
         fOut = openFileOutput("playerList.dat",MODE_PRIVATE);      
         
         osw = new OutputStreamWriter(fOut);

         osw.write(data);

         osw.flush();

         Toast.makeText(context, "List saved",Toast.LENGTH_SHORT).show();

         }

         catch (Exception e) {      

         e.printStackTrace();

         Toast.makeText(context, "List not saved",Toast.LENGTH_SHORT).show();

         }

         finally {

            try {

                   osw.close();

                   fOut.close();

                   } catch (IOException e) {

                   e.printStackTrace();

                   }

         }

    }
    
    public String ReadFromFile(Context context){
    	 
        FileInputStream fIn = null;

//        InputStreamReader isr = null;

        BufferedReader buf = null; 
		 
		
//        char[] inputBuffer = new char[255];

        String data = new String();

        

        try{

         fIn = openFileInput("playerList.dat");      

         buf = new BufferedReader(new InputStreamReader(fIn));
//         isr = new InputStreamReader(fIn);
//
//         isr.read(inputBuffer);
//
//         data = new String(inputBuffer);
         
         
         data = buf.readLine();

         Toast.makeText(context, "List read",Toast.LENGTH_SHORT).show();

         }

         catch (Exception e) {      

         e.printStackTrace();

         Toast.makeText(context, "List not read",Toast.LENGTH_SHORT).show();

         }

         finally {

            try {

//                   isr.close();

            	if(buf != null)
            		buf.close();
            	
            	if(fIn != null)
                   fIn.close();
            		

                   } catch (IOException e) {

                   e.printStackTrace();

                   }

         }

               return data;

    }
    

}
