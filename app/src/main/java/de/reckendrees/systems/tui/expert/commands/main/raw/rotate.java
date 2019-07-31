package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.content.Context;
import android.provider.Settings;

import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.commands.main.specific.RootCommand;
import de.reckendrees.systems.tui.expert.tuils.libsuperuser.Shell;

public class rotate implements CommandAbstraction , RootCommand {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        String command = "settings put system accelerometer_rotation 1";
        boolean isEnabled = isEnabled(info.context);
        if(isEnabled) { command = "settings put system accelerometer_rotation 0"; }
        Shell.SU.run(command);
        return info.res.getString(R.string.output_rotation) + !isEnabled;
    }
    private boolean isEnabled(Context context){
        return Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) != 0;
    }
    @Override
    public int helpRes() {
        return R.string.help_rotation;
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