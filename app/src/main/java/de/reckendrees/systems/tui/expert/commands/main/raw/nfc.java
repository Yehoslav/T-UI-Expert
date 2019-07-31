package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;

import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.commands.main.specific.RootCommand;
import de.reckendrees.systems.tui.expert.tuils.libsuperuser.Shell;

public class nfc implements CommandAbstraction, RootCommand {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        String command = "svc nfc enable";
        boolean isEnabled = isEnabled(info.context);
        if(isEnabled) { command = "svc nfc disable"; }
        Shell.SU.run(command);
        return info.res.getString(R.string.output_nfc) + !isEnabled;
    }
    private boolean isEnabled(Context context){
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();
        if (adapter != null && adapter.isEnabled()) { return true; }
        return false;
    }
    @Override
    public int helpRes() {
        return R.string.help_nfc;
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
