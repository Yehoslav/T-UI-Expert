package de.reckendrees.systems.tui.expert.tuils.interfaces;

import de.reckendrees.systems.tui.expert.commands.main.specific.RedirectCommand;

/**
 * Created by francescoandreuzzi on 03/03/2017.
 */

public interface Redirectator {

    void prepareRedirection(RedirectCommand cmd);
    void cleanup();
}
