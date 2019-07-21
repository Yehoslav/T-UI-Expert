package de.reckendrees.systems.tui.expert.commands.main.specific;

import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;

/**
 * Created by francescoandreuzzi on 29/01/2017.
 */

public abstract class PermanentSuggestionCommand implements CommandAbstraction {

    public abstract String[] permanentSuggestions();
}
