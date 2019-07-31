package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.commands.main.specific.APICommand;
import de.reckendrees.systems.tui.expert.commands.main.specific.RootCommand;
import de.reckendrees.systems.tui.expert.tuils.libsuperuser.Shell;

public class battery implements CommandAbstraction, APICommand, RootCommand {

    @Override
    public String exec(ExecutePack pack) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            MainPack info = (MainPack) pack;
            String command = "settings put global low_power 1";
            boolean isEnabled = isEnabled(info.context);
            if(isEnabled) { command = "settings put global low_power 0"; }
            Shell.SU.run(command);
            return info.res.getString(R.string.output_battery) + !isEnabled;
        }
        return null;
    }
    private boolean isEnabled(Context context){
        PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return powerManager.isPowerSaveMode();
        }
        return false;
    }
    @Override
    public int helpRes() {
        return R.string.help_battery;
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

    @Override
    public boolean willWorkOn(int api) {
        return api >= Build.VERSION_CODES.LOLLIPOP;
    }

}
