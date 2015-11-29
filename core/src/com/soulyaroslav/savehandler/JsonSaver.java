package com.soulyaroslav.savehandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.soulyaroslav.assets.SaveHandler;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.states.HighScoreState;
import com.soulyaroslav.states.SettingState;

public class JsonSaver {

    private SettingSave settingSave;
    private Score highScore;
    //private Score minutes, seconds;
    // inner value to check if file empty
    private boolean isEmpty;

    public JsonSaver() {
        settingSave = new SettingSave();
        highScore = new Score();
    }

    public static class SettingSave {
        // can music play
        public boolean musicPlay;
        // can sound play
        public boolean soundPlay;
        // can vibes play
        public boolean vibesPlay;
    }

    public static class Score {
        // player score
        public int highScore;
        // time
        public int minutes;
        public int seconds;
    }

    public void saveScore(){
        Json json = new Json();
        // high score
        highScore.highScore = HighScoreState.highScore;
        highScore.minutes = HighScoreState.minutes;
        highScore.seconds = HighScoreState.seconds;
        writeFile("score.save", json.toJson(highScore));
        //System.out.println(json.prettyPrint(highScore));
    }

    public void save(){
        Json json = new Json();
        // audio
        settingSave.musicPlay = SaveValue.musicPlay;
        settingSave.soundPlay = SaveValue.soundPlay;
        settingSave.vibesPlay = SaveValue.vibes;
        writeFile("game.save", json.toJson(settingSave));
        //System.out.println(json.prettyPrint(settingSave));
    }

    public void load(){
        String save = readFile("game.save");
        if(!save.isEmpty()){
            Json json = new Json();
            settingSave = json.fromJson(SettingSave.class, save);
        }
    }

    public void loadScore(){
        String save = readFile("score.save");
        if(!save.isEmpty()){
            Json json = new Json();
            highScore = json.fromJson(Score.class, save);
        }

    }

    private void writeFile(String fileName, String s) {
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
    }

    private String readFile(String fileName) {
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String s = file.readString();
            if (!s.isEmpty()) {
                isEmpty = false;
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            } else {
                isEmpty = true;
            }
        }
        return "";
    }

    public SettingSave getSettingSave() {
        return settingSave;
    }

    public Score getHighScore() {
        return highScore;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
