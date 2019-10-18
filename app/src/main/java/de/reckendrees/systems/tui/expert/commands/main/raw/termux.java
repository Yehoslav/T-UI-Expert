package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;

import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.Command;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.managers.xml.XMLPrefsManager;
import de.reckendrees.systems.tui.expert.managers.xml.options.Theme;
import de.reckendrees.systems.tui.expert.tasks.getFileContentTask;
import de.reckendrees.systems.tui.expert.tuils.Tuils;
import de.reckendrees.systems.tui.expert.tuils.interfaces.OnFileContentUpdate;
import de.reckendrees.systems.tui.expert.tuils.libsuperuser.Shell;

public class termux implements CommandAbstraction, OnFileContentUpdate {

    public static final String TERMUX_SERVICE = "com.termux.app.TermuxService";
    public static final String ACTION_EXECUTE = "com.termux.service_execute";
    private Context context;
    private File inputFile, outputFile, errorFile,checkFile;
    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        context = info.context;
        String inputCommand = info.get().toString();
        String commandFileStr = Tuils.getFolder() +"/.termux_cmd";
        String outputFileStr = Tuils.getFolder() +"/.termux_out";
        String errorFileStr = Tuils.getFolder()+"/.termux_error";
        String chekcFileStr = Tuils.getFolder()+"/.termux_check";
        //Shell.SH.run("echo ''>"+commandFile);
        inputFile = new File(Tuils.getFolder(), "/.termux_cmd");
        outputFile = new File(Tuils.getFolder(), "/.termux_out");
        //errorFile = new File(Tuils.getFolder(),"/.termux_error");
        //checkFile = new File(Tuils.getFolder(),"/.termux_check");
        deleteFiles();
        try{
            FileOutputStream stream = new FileOutputStream(inputFile);
            try {
                String cmd = "(("+inputCommand+")>"+outputFileStr+") 2>"+errorFileStr+";echo outputend >>"+outputFileStr;
                Log.d("TUI-EX", cmd);
                stream.write((cmd).getBytes());
            } finally {
                stream.close();
            }
        }catch(java.io.IOException e){
            e.printStackTrace();
            printError(info.res.getString(R.string.termux_permissions));
            return Tuils.EMPTYSTRING;
        }
        inputFile.setReadable(true);
        inputFile.setExecutable(true);
        Uri scriptUri = new Uri.Builder().scheme("com.termux.file").path(commandFileStr).build();
        Log.d("TUI-EX",Tuils.getFolder() + inputCommand);
        try{
            Intent executeIntent = new Intent(ACTION_EXECUTE, scriptUri);
            executeIntent.setClassName("com.termux", TERMUX_SERVICE);
            executeIntent.putExtra("com.termux.execute.background", true);
            //executeIntent.putExtra(PluginBundleManager.EXTRA_ARGUMENTS, list.toArray(new String[list.size()]));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                info.context.startForegroundService(executeIntent);
            } else {
                info.context.startService(executeIntent);
            }
        }catch(SecurityException se){
            printError(info.res.getString(R.string.termux_permissions));
            return Tuils.EMPTYSTRING;
        }
        (new getFileContentTask(this)).execute(".termux_out", ".termux_error",info.res.getString(R.string.termux_file_permission) );
        return Tuils.EMPTYSTRING;
    }

    private void deleteFiles(){
        outputFile.delete();
        inputFile.delete();
       // errorFile.delete();
        //checkFile.delete();
    }
    @Override
    public void printError(String error){
       // Log.d("TUIE  error",error);
        String orginalError = error;
        String toSplit = inputFile.getAbsolutePath()+": ";
        String[] errors  = error.split(toSplit);
      //  Log.d("TUIE  error",error);
        if(errors[errors.length-1].length()> 1){
            error = errors[errors.length-1];
            if(error.length() != orginalError.length()){
                error = error+ "\nmaybe termux is missing a package?";
            }
            int outputColor = XMLPrefsManager.getColor(Theme.app_uninstalled_color);
            Tuils.sendOutput(outputColor, context,error);
        }


    }
    @Override
    public void printOutput(String output){
        int outputColor = XMLPrefsManager.getColor(Theme.output_color);
        //Tuils.sendOutput(outputColor, context,output.split("outputend")[0]);
        //for(String outLine: output.split("outputend")[0].split("\n")){
        Tuils.sendOutput(outputColor, context,output);
        //}
        //deleteFiles();

    }

    @Override
    public void output_finished(String filename){
        deleteFiles();
    }
    @Override
    public int helpRes() {
        return R.string.help_termux;
    }

    @Override
    public int[] argType() {
        return new int[]{CommandAbstraction.PLAIN_TEXT};
    }

    @Override
    public int priority() {
        return 4;
    }

    @Override
    public String onNotArgEnough(ExecutePack pack, int nArgs) {
        MainPack info = (MainPack) pack;
        return info.res.getString(helpRes());
    }

    @Override
    public String onArgNotFound(ExecutePack pack, int index) {
        MainPack info = (MainPack) pack;
        return info.res.getString(R.string.output_filenotfound);
    }

}