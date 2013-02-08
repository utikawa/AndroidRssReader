package com.tmm.android.rssreader;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.tmm.android.rssreader.reader.RssReader;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class RssActivity extends ListActivity {

	private RssListAdapter adapter;
	private List<JSONObject> jobs;

	private void RssGetFeed(String feed) {
		jobs = new ArrayList<JSONObject>();
		try {
			jobs = RssReader.getLatestRssFeed(feed);
		} catch (Exception e) {
			Log.e("RSS ERROR", "Error loading RSS Feed Stream >> " + e.getMessage() + " //" + e.toString());
			Toast.makeText(RssActivity.this, "Erro ao receber feed", Toast.LENGTH_SHORT).show();
		}

		adapter = new RssListAdapter(this,jobs);
		setListAdapter(adapter);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RssGetFeed("http://www1.caixa.gov.br/rss/asp/geraXML_rss_loterias.asp?canal=MEGASENA");
	}

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }
 
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {
        case R.id.menu_bookmark:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
            finish();
            return true;
 
        case R.id.menu_save:
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("URL");
            alert.setMessage("Digite a URL do feed:");

            // Set an EditText view to get user input 
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
              String feed = input.getText().toString();
              RssGetFeed(feed);
              }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
              }
            });

            alert.show();
            return true;
 
        case R.id.menu_search:
            Toast.makeText(RssActivity.this, "Search is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }   
 
}