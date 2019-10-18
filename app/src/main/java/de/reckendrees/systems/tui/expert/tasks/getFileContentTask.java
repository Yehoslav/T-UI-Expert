package de.reckendrees.systems.tui.expert.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

import de.reckendrees.systems.tui.expert.managers.xml.XMLPrefsManager;
import de.reckendrees.systems.tui.expert.managers.xml.options.Expert;
import de.reckendrees.systems.tui.expert.tuils.Tuils;
import de.reckendrees.systems.tui.expert.tuils.interfaces.OnFileContentUpdate;

public class getFileContentTask extends AsyncTask<String, Integer, String> {

    private OnFileContentUpdate onFileContentUpdate;
    public getFileContentTask(OnFileContentUpdate on){
        onFileContentUpdate = on;
    }
    @Override
    protected String doInBackground(String... commands) {
        int start = (int)System.currentTimeMillis()/1000;
        int end = (XMLPrefsManager.getInt(Expert.termux_timeout))+start;
        String fileContent = "";
        ArrayList<String> outputs = new ArrayList<String>();
        int currentLength = 0;
        while(!fileContent.contains("outputend")&&  ( (int)System.currentTimeMillis()/1000)< end){
            try {
                Thread.sleep(500);
                fileContent = readFile(commands[0]);

                if(fileContent.equals("tui_termux_error")){
                    onFileContentUpdate.printError("Termux needs file permissions to work with TUI Expert. Please use 'apps -st Termux' and set file permissions.");
                    return "";
                }else{
                    if (fileContent.length()> currentLength){
                        currentLength = fileContent.length();
                        String[] lines = fileContent.split("\n");
                        for(int i= outputs.size();i<lines.length;i++) {
                            onFileContentUpdate.printOutput(lines[i].replace("outputend",""));
                            outputs.add(lines[i]);
                            Log.d("TUI-E while",lines[i]);
                        }
                    }


                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (fileContent.length()> currentLength){
            String[] lines = fileContent.split("\n");
            for(int i= outputs.size();i<lines.length;i++) {
                onFileContentUpdate.printOutput(lines[i].replace("outputend",""));
                Log.d("TUI-E afterwhile",lines[i]);
            }
        }
        onFileContentUpdate.output_finished(commands[0]);
        try{
            for(String line: readFile(commands[1]).split("\n")){
                onFileContentUpdate.printError(line);
            }
        }catch(Exception e){
            Log.d("TUIE ", "no error:)");
        }

        Log.d("TUI-E endWhile",fileContent);
        return fileContent;
    }


    @Override
    protected void onPostExecute(String output) {
        Log.d("TUI-E onPost",output);
        //onFileContentUpdate.printOutput(output);
    }
    private String readFile(String filename){
        File file = new File(Tuils.getFolder(), filename);
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            try {
                in.read(bytes);
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "tui_termux_error";
        }catch (IOException f){
            f.printStackTrace();
            return "";
        }

       return new String(bytes);
    }

}
