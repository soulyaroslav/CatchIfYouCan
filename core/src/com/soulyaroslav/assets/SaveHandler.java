package com.soulyaroslav.assets;

import com.soulyaroslav.savehandler.JsonSaver;

public class SaveHandler {

    public SaveHandler(JsonSaver save) {
        SaveValue.soundPlay = save.getSettingSave().soundPlay;
        SaveValue.musicPlay = save.getSettingSave().musicPlay;
        SaveValue.vibes = save.getSettingSave().vibesPlay;
        SaveValue.highScore = save.getHighScore().highScore;
        SaveValue.minutes = save.getHighScore().minutes;
        SaveValue.seconds = save.getHighScore().seconds;
    }
}
