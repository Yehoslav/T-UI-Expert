package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;

import de.reckendrees.systems.tui.expert.BuildConfig;
import de.reckendrees.systems.tui.expert.LauncherActivity;
import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.UIManager;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.CommandsPreferences;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;
import de.reckendrees.systems.tui.expert.commands.main.specific.ParamCommand;
import de.reckendrees.systems.tui.expert.managers.xml.XMLPrefsManager;
import de.reckendrees.systems.tui.expert.tuils.Tuils;
import de.reckendrees.systems.tui.expert.tuils.interfaces.Reloadable;
import de.reckendrees.systems.tui.expert.tuils.stuff.PolicyReceiver;

/**
 * Created by francescoandreuzzi on 10/06/2017.
 */

public class tui extends ParamCommand {

    private enum Param implements de.reckendrees.systems.tui.expert.commands.main.Param {

        rm {
            @Override
            public String exec(ExecutePack pack) {
                MainPack info = (MainPack) pack;

                DevicePolicyManager policy = (DevicePolicyManager) info.context.getSystemService(Context.DEVICE_POLICY_SERVICE);
                ComponentName name = new ComponentName(info.context, PolicyReceiver.class);
                policy.removeActiveAdmin(name);

                Uri packageURI = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                info.context.startActivity(uninstallIntent);

                return null;
            }
        },
        termux {
            @Override
            public String exec(ExecutePack pack) {
                pack.context.startActivity(Tuils.webPage("https://github.com/v1nc/TUI-Expert/wiki/Termux-integration"));
                return null;
            }
        },
        download_termux {
            @Override
            public String exec(ExecutePack pack) {
                pack.context.startActivity(Tuils.webPage("https://github.com/v1nc/termux-app/releases/latest"));
                return null;
            }
        },
        about {
            @Override
            public String exec(ExecutePack pack) {
                MainPack info = (MainPack) pack;
                return "Version:" + Tuils.SPACE + BuildConfig.VERSION_NAME + " (code: " + BuildConfig.VERSION_CODE + ")" +
                        (BuildConfig.DEBUG ? Tuils.NEWLINE + BuildConfig.BUILD_TYPE : Tuils.EMPTYSTRING) +
                        Tuils.NEWLINE + Tuils.NEWLINE + info.res.getString(R.string.output_about);
            }
        },
        log {
            @Override
            public int[] args() {
                return new int[] {CommandAbstraction.PLAIN_TEXT};
            }

            @Override
            public String exec(ExecutePack pack) {
                Intent i = new Intent(UIManager.ACTION_LOGTOFILE);
                i.putExtra(UIManager.FILE_NAME, pack.getString());
                LocalBroadcastManager.getInstance(pack.context.getApplicationContext()).sendBroadcast(i);

                return null;
            }

            @Override
            public String onNotArgEnough(ExecutePack pack, int n) {
                return pack.context.getString(R.string.help_tui);
            }
        },
        priority {
            @Override
            public int[] args() {
                return new int[] {CommandAbstraction.COMMAND, CommandAbstraction.INT};
            }

            @Override
            public String exec(ExecutePack pack) {
                File file = new File(Tuils.getFolder(), "cmd.xml");
                return XMLPrefsManager.set(file, pack.get().getClass().getSimpleName() + CommandsPreferences.PRIORITY_SUFFIX, new String[] {XMLPrefsManager.VALUE_ATTRIBUTE}, new String[] {String.valueOf(pack.getInt())});
            }

            @Override
            public String onNotArgEnough(ExecutePack pack, int n) {
                return pack.context.getString(R.string.help_tui);
            }

            @Override
            public String onArgNotFound(ExecutePack pack, int index) {
                return pack.context.getString(R.string.output_invalidarg);
            }
        },
        telegram {
            @Override
            public String exec(ExecutePack pack) {
                pack.context.startActivity(Tuils.webPage("https://t.me/t_ui_dev"));
                return null;
            }
        },
        googlep {
            @Override
            public String exec(ExecutePack pack) {
                pack.context.startActivity(Tuils.webPage("https://t.me/s/NoGoolag"));
                return null;
            }
        },
        twitter {
            @Override
            public String exec(ExecutePack pack) {
                pack.context.startActivity(Tuils.webPage("https://notwitter.com/"));
                return null;
            }
        },
        sourcecode {
            @Override
            public String exec(ExecutePack pack) {
                pack.context.startActivity(Tuils.webPage("https://github.com/v1nc/TUI-Expert"));
                return null;
            }
        },
        reset {
            @Override
            public String exec(ExecutePack pack) {
                Tuils.deleteContentOnly(Tuils.getFolder());

                ((LauncherActivity) pack.context).addMessage(pack.context.getString(R.string.tui_reset), null);
                ((Reloadable) pack.context).reload();
                return null;
            }
        },
        folder {
            @Override
            public String exec(ExecutePack pack) {

                Uri selectedUri = Uri.parse(Tuils.getFolder().getAbsolutePath());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(selectedUri, "resource/folder");

                if (intent.resolveActivityInfo(pack.context.getPackageManager(), 0) != null) {
                    pack.context.startActivity(intent);
                } else {
                    return Tuils.getFolder().getAbsolutePath();
                }

                return null;
            }
        }
//        ,
//        exclude_message {
//            @Override
//            public int[] args() {
//                return new int[] {CommandAbstraction.INT};
//            }
//
//            @Override
//            public String exec(ExecutePack pack) {
//                return null;
//            }
//
//            @Override
//            public String onNotArgEnough(ExecutePack pack, int n) {
//
//            }
//        }
        ;

        @Override
        public int[] args() {
            return new int[0];
        }

        static Param get(String p) {
            p = p.toLowerCase();
            Param[] ps = values();
            for (Param p1 : ps)
                if (p.endsWith(p1.label()))
                    return p1;
            return null;
        }

        static String[] labels() {
            Param[] ps = values();
            String[] ss = new String[ps.length];

            for(int count = 0; count < ps.length; count++) {
                ss[count] = ps[count].label();
            }

            return ss;
        }

        @Override
        public String label() {
            return Tuils.MINUS + name();
        }

        @Override
        public String onArgNotFound(ExecutePack pack, int index) {
            return null;
        }

        @Override
        public String onNotArgEnough(ExecutePack pack, int n) {
            return null;
        }
    }

    @Override
    protected de.reckendrees.systems.tui.expert.commands.main.Param paramForString(MainPack pack, String param) {
        return Param.get(param);
    }

    @Override
    protected String doThings(ExecutePack pack) {
        return null;
    }

    @Override
    public String[] params() {
        return Param.labels();
    }

    @Override
    public int priority() {
        return 4;
    }

    @Override
    public int helpRes() {
        return R.string.help_tui;
    }
}
