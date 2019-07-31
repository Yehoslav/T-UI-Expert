package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import java.lang.reflect.Method;

import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.commands.main.specific.RootCommand;
import de.reckendrees.systems.tui.expert.tuils.libsuperuser.Shell;

public class cellular implements CommandAbstraction, RootCommand {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        String command = "svc data enable";
        boolean isEnabled = isEnabled(info.context);
        if(isEnabled) { command = "svc data disable"; }
        Shell.SU.run(command);
        return info.res.getString(R.string.output_cellular) + !isEnabled;
    }
    private boolean isEnabled(Context context){
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean)method.invoke(cm);
        } catch (Exception e) {
        }
        return mobileDataEnabled;
    }
    @Override
    public int helpRes() {
        return R.string.help_cellular;
    }

    @Override
    public int[] argType() {
        return new int[0];
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public String onNotArgEnough(ExecutePack info, int nArgs) {
        return null;
    }

    @Override
    public String onArgNotFound(ExecutePack info, int index) {
        return null;
    }



}
