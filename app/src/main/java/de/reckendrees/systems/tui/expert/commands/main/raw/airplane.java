package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.commands.main.specific.APICommand;
import de.reckendrees.systems.tui.expert.commands.main.specific.APIRootCommand;
import de.reckendrees.systems.tui.expert.commands.main.specific.RootCommand;
import de.reckendrees.systems.tui.expert.managers.xml.XMLPrefsManager;
import de.reckendrees.systems.tui.expert.managers.xml.options.Expert;
import de.reckendrees.systems.tui.expert.tuils.libsuperuser.Shell;

/**
 * Created by andre on 03/12/15.
 */
public class airplane implements CommandAbstraction, APIRootCommand {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        boolean isEnabled = isEnabled(info.context);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.System.putInt(info.context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);

            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", !isEnabled);
            info.context.sendBroadcast(intent);

            return info.res.getString(R.string.output_airplane) + !isEnabled;
        }else{
            String command = "settings put global airplane_mode_on 1 && am broadcast -a android.intent.action.AIRPLANE_MODE --ez state true";
            if(isEnabled){command = "settings put global airplane_mode_on 0 && am broadcast -a android.intent.action.AIRPLANE_MODE --ez state false";}
            Shell.SU.run(command);
            return info.res.getString(R.string.output_airplane) + !isEnabled;
        }
    }

    private boolean isEnabled(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public int[] argType() {
        return new int[0];
    }

    @Override
    public int helpRes() {
        return R.string.help_airplane;
    }

    @Override
    public String onArgNotFound(ExecutePack info, int index) {
        return null;
    }

    @Override
    public String onNotArgEnough(ExecutePack info, int nArgs) {
        return null;
    }

    @Override
    public boolean willWorkOn(int api) {
        if (api < Build.VERSION_CODES.JELLY_BEAN_MR1){ return true;}
        if(XMLPrefsManager.getBoolean(Expert.use_root)){return true;}
        return false;
    }
}
