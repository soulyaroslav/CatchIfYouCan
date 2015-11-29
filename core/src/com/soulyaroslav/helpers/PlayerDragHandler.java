package com.soulyaroslav.helpers;

import com.soulyaroslav.entities.Player;

public class PlayerDragHandler extends InputHandler{

    private Player player;

    public PlayerDragHandler(Player player) {
        this.player = player;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(player.getPlayerMove().contains(screenX, screenY)) {
            player.move(screenX);
            return true;
        }
        return true;
    }
}
