package de.reckendrees.systems.tui.expert.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.reckendrees.systems.tui.expert.tuils.interfaces.OnCommandUpdate;


public class getCommandOutputTask extends AsyncTask<String, Integer, String> {

    private Runnable runnable;
    private OnCommandUpdate onCommandUpdate;
    public getCommandOutputTask(Runnable ru, OnCommandUpdate newCommandUpdate){
        runnable=ru;
        onCommandUpdate = newCommandUpdate;
    }
    @Override
    protected String doInBackground(String... commands) {
        try {
            Process process = Runtime.getRuntime().exec(commands[0]);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder log = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line + "\n");
            }
            Log.d("TUI-E",log.toString());
            return log.toString();

        } catch (IOException e) {
            return "error";
        }
    }


    @Override
    protected void onPostExecute(String output) {
        Log.d("TUI-E",output);
        try{
            onCommandUpdate.update(output,runnable);
        }catch(Exception e){
            Log.e("TUI-E",e.toString());
        }

    }
}
