package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.commands.main.specific.ParamCommand;
import de.reckendrees.systems.tui.expert.tasks.shutdownTask;
import de.reckendrees.systems.tui.expert.tuils.Tuils;

public class sleeptimer extends ParamCommand {
    private static shutdownTask st;

    private enum Param implements de.reckendrees.systems.tui.expert.commands.main.Param {

        set {
            @Override
            public int[] args() {
                return new int[] {CommandAbstraction.INT};
            }

            @Override
            public String exec(ExecutePack pack) {

                st = new shutdownTask();
                st.execute(pack.getInt());
                return  pack.context.getString(R.string.start_sleep);
            }

            @Override
            public String onArgNotFound(ExecutePack pack, int index) {
                return pack.context.getString(R.string.invalid_integer);
            }
        },
        del{
            @Override
            public int[] args() {
                return new int[]{};
            }

            @Override
            public String exec(ExecutePack pack) {
                st.cancel(true);
                return pack.context.getString(R.string.stop_sleep);
            }

            @Override
            public String onArgNotFound(ExecutePack pack, int index) {
                return "";
            }

            @Override
            public String onNotArgEnough(ExecutePack pack, int n) {
                return "";
            }
        };

        static sleeptimer.Param get(String p) {
            p = p.toLowerCase();
            sleeptimer.Param[] ps = values();
            for (sleeptimer.Param p1 : ps) if (p.endsWith(p1.label())) return p1;
            return null;
        }

        static String[] labels() {
            sleeptimer.Param[] ps = values();
            String[] ss = new String[ps.length];

            for (int count = 0; count < ps.length; count++) {
                ss[count] = ps[count].label();
            }
            Log.d("T-UI E",ss.length+ ", ");
            return ss;
        }

        @Override
        public String label() {
            return Tuils.MINUS + name();
        }

        @Override
        public String onNotArgEnough(ExecutePack pack, int n) {
            return pack.context.getString(R.string.help_sleep);
        }
    }

    @Override
    public String[] params() {
        return sleeptimer.Param.labels();
    }

    @Override
    protected de.reckendrees.systems.tui.expert.commands.main.Param paramForString(MainPack pack, String param) {
        return sleeptimer.Param.get(param);
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public int helpRes() {
        return R.string.help_sleep;
    }

    @Override
    protected String doThings(ExecutePack pack) {
        return null;
    }
}

