package de.reckendrees.systems.tui.expert.tuils.interfaces;

public interface OnFileContentUpdate {
    void printOutput(String output);
    void output_finished(String filename);
    void printError(String error);
}

