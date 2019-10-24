package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.content.Context;
import android.util.Log;

import com.dcastalia.localappupdate.DownloadApk;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.managers.xml.XMLPrefsManager;
import de.reckendrees.systems.tui.expert.managers.xml.options.Theme;
import de.reckendrees.systems.tui.expert.tuils.Tuils;
import de.reckendrees.systems.tui.expert.tuils.interfaces.Reloadable;

public class update implements CommandAbstraction {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        Context context = info.context;
        int outputColor = XMLPrefsManager.getColor(Theme.output_color);
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(info.context)
                .setUpdateFrom(UpdateFrom.GITHUB)
                .setGitHubUserAndRepo("v1nc", "TUI-Expert")
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        String version = update.getLatestVersion().toString();
                        Log.d("Latest Version", " "+update.getLatestVersion());
                        Log.d("Release notes", " "+update.getReleaseNotes());
                        Log.d("URL"," "+ update.getUrlToDownload().toString());
                        if(isUpdateAvailable){
                            Tuils.sendOutput(outputColor, context, context.getString(R.string.update2));
                            String update_url = "https://github.com/v1nc/TUI-Expert/releases/download/v"+version+"/de.reckendrees.systems.tui.expert_fdroid_v."+version+".apk";
                            update_url = "https://raw.githubusercontent.com/v1nc/TUI-Expert/master/update.apk";
                            DownloadApk downloadApk = new DownloadApk(context);
                            downloadApk.startDownloadingApk(update_url);
                        }else{
                            Tuils.sendOutput(outputColor, context, context.getString(R.string.update3));
                        }

                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                        Tuils.sendOutput(outputColor, context, context.getString(R.string.update4));
                    }
                });
        appUpdaterUtils.start();
        return context.getString(R.string.update1);
    }

    @Override
    public int[] argType() {
        return new int[0];
    }

    @Override
    public int priority() {
        return 4;
    }

    @Override
    public int helpRes() {
        return R.string.help_restart;
    }

    @Override
    public String onArgNotFound(ExecutePack info, int index) {
        return null;
    }

    @Override
    public String onNotArgEnough(ExecutePack info, int nArgs) {
        return null;
    }

}
