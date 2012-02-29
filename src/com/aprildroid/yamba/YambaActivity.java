package com.aprildroid.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class YambaActivity extends Activity implements OnClickListener{
	private static final String TAG = "StatusActivity";
	EditText editText;
	Button updateButton;
	Twitter twitter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        
        // Find views
        editText = (EditText)findViewById(R.id.editText);
        updateButton = (Button)findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(this);
        
        twitter = new Twitter("student", "password");
        twitter.setAPIRootUrl("http://yamba.marakana.com/api");
    }
    
    // Asynchronously posts to twitter
    class PostToTwitter extends AsyncTask<String, Integer, String>{
    	//Called to initiate the background activity
		@Override
		protected String doInBackground(String... statuses) {
			try{
				Twitter.Status status = twitter.updateStatus(statuses[0]);
				return status.text;
			}catch(TwitterException e){
				Log.e(TAG, e.toString());
				e.printStackTrace();
				return "Failed to post";
			}
			
		}
		
		// Called when there's status to be updated
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			//Not used in this case
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(YambaActivity.this, result, Toast.LENGTH_LONG).show();
		}
    	
    	
    }
    
    // Called when button is clicked 
	@Override
	public void onClick(View v) {
//		twitter.setStatus(editText.getText().toString());
		String status = editText.getText().toString();
		new PostToTwitter().execute(status);
		Log.d(TAG, "onClicked");
	}
}