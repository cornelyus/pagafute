package ricardo.pagafute.example;


import greendroid.app.GDActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class PagaFuteActivity extends GDActivity {
	
	public static final int activityListaJogadoresMes = 1;
	public static final int activityListaJogadores = 2;
	public static final String PREFS_NAME = "MyPrefsFile";
	Spinner hubSpinner = null;
	public static final String thisMonth = "thisMonth";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        setActionBarContentView(R.layout.main);
        
        hubSpinner = (Spinner) findViewById(R.id.spinner_months);

        ArrayAdapter adapter = ArrayAdapter.createFromResource( this, R.array.meses , android.R.layout.simple_spinner_item); adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        hubSpinner.setAdapter(adapter); 
        
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);	    
	    hubSpinner.setSelection(settings.getInt("selMes", 0));
                
    }
    
    @Override
    protected void onStop(){
       super.onStop();
       Log.i("PagaFute", "onStop");
      
       // We need an Editor object to make preference changes.
       // All objects are from android.context.Context
       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       SharedPreferences.Editor editor = settings.edit();
       editor.putInt("selMes", hubSpinner.getSelectedItemPosition());

       // Commit the edits!
       editor.commit();
    }
    
    public void goClick(View v){
    	
//    	Toast.makeText(PagaFuteActivity.this, "click", Toast.LENGTH_SHORT).show();
    	
    	Intent i = new Intent(PagaFuteActivity.this, List12.class);
//		i.putExtra(playerNumber, v.getTag().toString());
//		i.putExtra(socketIsCommunicating,
//				socketCommunicating || !sc.getConnectionState());
		startActivityForResult(i, activityListaJogadores);
    }
    
 public void goListaClick(View v){
    	
//    	Toast.makeText(PagaFuteActivity.this, "click", Toast.LENGTH_SHORT).show();
    	
    	Intent i = new Intent(PagaFuteActivity.this, ListaJogadores.class);
		i.putExtra(thisMonth, hubSpinner.getSelectedItem().toString());
//		i.putExtra(socketIsCommunicating,
//				socketCommunicating || !sc.getConnectionState());
		startActivityForResult(i, activityListaJogadoresMes);
    }
}