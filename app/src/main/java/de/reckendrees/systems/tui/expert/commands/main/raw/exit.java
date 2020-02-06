package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.content.Context;

import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.commands.main.specific.HideableCommand;
import de.reckendrees.systems.tui.expert.managers.xml.XMLPrefsManager;
import de.reckendrees.systems.tui.expert.managers.xml.options.Expert;
import de.reckendrees.systems.tui.expert.managers.xml.options.Theme;
import de.reckendrees.systems.tui.expert.tuils.Tuils;

/**
 * Created by francescoandreuzzi on 21/05/2017.
 */

public class exit implements CommandAbstraction, HideableCommand {

    @Override
    public boolean show(){
        return XMLPrefsManager.getBoolean(Expert.show_exit);
    }
    @Override
    public String exec(ExecutePack pack) throws Exception {
        String password = XMLPrefsManager.getString(Expert.exit_password);
        MainPack info = (MainPack) pack;
        String inputPassword = "";
        try{
            inputPassword = info.get().toString();
        }catch(Exception e){
            e.printStackTrace();
        }

        if(password.length() > 0 && !inputPassword.equals(password)){
            //eturn inputPassword + " - " + password;
            wrongPassword(pack);
        }else{
            Tuils.resetPreferredLauncherAndOpenChooser(pack.context);
        }
        return null;
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
    public int helpRes() {
        return R.string.help_exit;
    }

    @Override
    public String onArgNotFound(ExecutePack pack, int indexNotFound) {
        return  pack.context.getString(R.string.wrong_password);
    }

    private void wrongPassword(ExecutePack pack){
        Tuils.sendOutput(XMLPrefsManager.getColor(Theme.app_uninstalled_color), pack.context, pack.context.getString(R.string.wrong_password));
    }
    @Override
    public String onNotArgEnough(ExecutePack pack, int nArgs) {
        if(XMLPrefsManager.getString(Expert.exit_password).length() > 0 ){
            wrongPassword(pack);
        }else{
            Tuils.resetPreferredLauncherAndOpenChooser(pack.context);
        }
        return null;
    }

}
