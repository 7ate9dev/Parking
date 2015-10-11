package karan.parking;

import android.os.AsyncTask;

/**
 * Created by karan on 11/10/15.
 */
public class MyAsyncTask extends AsyncTask<Void,Void,Void> {
    LocationHistory history;
    public MyAsyncTask(LocationHistory history)
    {
        this.history=history;
    }
    @Override
    protected Void doInBackground(Void... params) {
        history.save();
        return null;
    }
}
