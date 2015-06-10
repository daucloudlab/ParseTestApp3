package kz.abcsoft.parse.android;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView mListView ;
    private List<ParseObject> mObject ;
    private ProgressDialog mProgressDialog ;
    private ArrayAdapter<String> mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetNameTask().execute() ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class GetNameTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this) ;
            mProgressDialog.setTitle("Parse.com ListView");
            mProgressDialog.setMessage("Загружаем...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Cats") ;
            query.orderByDescending("_created_at") ;

            try{
                mObject = query.find() ;
            }catch (ParseException e){
                Log.e("ERROR", e.getMessage()) ;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mListView = (ListView)findViewById(R.id.listView) ;
            mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1) ;

            for(ParseObject cats : mObject){
                mAdapter.add((String)cats.get("Name")) ;
            }

            mListView.setAdapter(mAdapter);
            mProgressDialog.dismiss();

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(), mObject.get(i).getString("Name"),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
