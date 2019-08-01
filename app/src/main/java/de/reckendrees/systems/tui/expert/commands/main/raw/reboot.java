package de.reckendrees.systems.tui.expert.commands.main.raw;

import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.commands.main.specific.RootCommand;
import de.reckendrees.systems.tui.expert.tuils.libsuperuser.Shell;

public class reboot implements CommandAbstraction, RootCommand {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        Shell.SU.run("svc power reboot");
        return info.res.getString(R.string.output_reboot);
    }
    @Override
    public int helpRes() {
        return R.string.help_reboot;
    }

    @Override
    public int[] argType() {
        return new int[0];
    }

    @Override
    public int priority() {
        return 1;
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