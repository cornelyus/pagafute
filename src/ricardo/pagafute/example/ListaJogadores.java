package ricardo.pagafute.example;

import greendroid.app.GDListActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaJogadores extends GDListActivity {

	ArrayAdapter jgdAdapt = null;
	private ArrayList<String> mStrings = new ArrayList<String>();
	private String my_sel_items;
	/** Called when the activity is first created. */
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    // TODO Auto-generated method stub
	    
//	    jgdAdapt = ArrayAdapter.createFromResource( this, R.array.jogadores, android.R.layout.simple_list_item_multiple_choice); 
	    
	    jgdAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mStrings);
	    
	    setListAdapter(jgdAdapt);
	    
	    final ListView listView = getListView();

        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3)
            {
                //List list = new ArrayList();
                my_sel_items=new String("");
                SparseBooleanArray a = listView.getCheckedItemPositions();

                for(int i = 0; i < jgdAdapt.getCount(); i++)
                {
                    if (a.valueAt(i))
                    {
                     /*
                        Long val = lView.getAdapter().getItemId(a.keyAt(i));
                        Log.v("MyData", "index=" + val.toString()
                             + "item value="+lView.getAdapter().getItem(i));
                        list.add(lView.getAdapter().getItemId((a.keyAt(i))));
                     */

                    	//RMS
//                        my_sel_items = my_sel_items + "," 
//                        + (String) lView.getAdapter().getItem(i);
                    	
                    	my_sel_items +=  i + ",";
                    }
                }
                Log.v("PagaFute",my_sel_items);
                
                if(!my_sel_items.equals(""))
            		WriteToFile(ListaJogadores.this, my_sel_items);
            }
        });
        
        String stringNomes = ReadFromFile(this);        
        
        if(stringNomes != null){        	
        	
        	String data[] = ReadFromFile(this).split(",");
	        for(int i = 0; i<data.length; i++){
	        	
	        	Log.d("PagaFute", "Data i : " +data[i]);
	        	jgdAdapt.add(data[i]);
	        }
	        
	        String choices = ReadFromChoicesFile(this);
	        
	        if(choices != null && !choices.equals("")){
	        	
	        	String choicesArray[] = choices.split(",");
		        for(int i = 0; i<choicesArray.length; i++){
		        	
		        	Log.d("PagaFute", "Data i : " + choicesArray[i]);
		        	if(Integer.parseInt(choicesArray[i]) < choicesArray.length)
		        		listView.setItemChecked(Integer.parseInt(choicesArray[i]), true);
		        }
	        }
	        
        }
	}
//	@Override
//    protected void onStop(){
//		Log.d("PagaFute", "onStop");
//    	
//    	Log.d("PagaFute", my_sel_items);
//    
//    	if(!my_sel_items.equals(""))
//    		WriteToFile(this, my_sel_items);
//    	super.onStop();
//    	
//    }
	
//	@Override
//    protected void onPause(){		
//    	super.onPause();
//    	Log.d("PagaFute", "onPause");
//    	
//    	Log.d("PagaFute", this.my_sel_items);
//    
//    	if(!my_sel_items.equals(""))
//    		WriteToFile(this, my_sel_items);
//    }
	
	public void WriteToFile(Context context, String data){
   	 
        FileOutputStream fOut = null;

        OutputStreamWriter osw = null;

        

        try{
         fOut = openFileOutput(getIntent().getExtras().getString(PagaFuteActivity.thisMonth),MODE_PRIVATE);      
         
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
    
    public String ReadFromChoicesFile(Context context){
   	 
        FileInputStream fIn = null;

//        InputStreamReader isr = null;

        BufferedReader buf = null; 
		 
		
//        char[] inputBuffer = new char[255];

        String data = new String();

        

        try{

         fIn = openFileInput(getIntent().getExtras().getString(PagaFuteActivity.thisMonth));      

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
