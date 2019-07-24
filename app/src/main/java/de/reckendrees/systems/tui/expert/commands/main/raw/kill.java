package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import de.reckendrees.systems.tui.expert.LauncherActivity;
import de.reckendrees.systems.tui.expert.MainManager;
import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.UIManager;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.tuils.Tuils;

public class kill implements CommandAbstraction {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        String packageName = info.getLaunchInfo().componentName.getPackageName();
        killAppBypackage(packageName,info.context);
        Log.d("TUI-E",packageName);
        return info.res.getString(R.string.output_kill);
    }
    private void killAppBypackage(String packageTokill, Context context){
        ActivityManager am = (ActivityManager)context.getSystemService(Activity.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(packageTokill);
    }
    @Override
    public int helpRes() {
        return R.string.help_kill;
    }

    @Override
    public int[] argType() {
        return new int[]{CommandAbstraction.VISIBLE_PACKAGE};
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public String onNotArgEnough(ExecutePack pack, int nArgs) {
        MainPack info = (MainPack) pack;
        return info.res.getString(helpRes());
    }

    @Override
    public String onArgNotFound(ExecutePack pack, int index) {
        MainPack info = (MainPack) pack;
        return info.res.getString(R.string.output_appnotfound);
    }

}