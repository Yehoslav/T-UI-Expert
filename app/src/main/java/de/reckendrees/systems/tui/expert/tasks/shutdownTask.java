package de.reckendrees.systems.tui.expert.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.reckendrees.systems.tui.expert.tuils.interfaces.OnCommandUpdate;
import de.reckendrees.systems.tui.expert.tuils.libsuperuser.Shell;

public class shutdownTask extends AsyncTask<Integer, Integer, String> {

    @Override
    protected String doInBackground(Integer... commands) {
        int start = (int)System.currentTimeMillis()/1000;
        int end = (commands[commands.length-1]*60)+start;
        Log.d("TUI-E time:",start+"-"+end);
        while((int)System.currentTimeMillis()/1000 < end){

        }
        return "";
    }


    @Override
    protected void onPostExecute(String output) {
        Log.d("TUI-E time","finished!");
        Shell.SU.run("svc power shutdown");
    }
}