package de.reckendrees.systems.tui.expert.tuils;

import android.support.v4.content.FileProvider;

import de.reckendrees.systems.tui.expert.BuildConfig;

public class GenericFileProvider extends FileProvider {
    public static final String PROVIDER_NAME = BuildConfig.APPLICATION_ID + ".FILE_PROVIDER";
}
