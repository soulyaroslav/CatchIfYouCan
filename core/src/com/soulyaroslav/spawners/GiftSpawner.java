package com.soulyaroslav.spawners;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.soulyaroslav.entities.Gift;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameworld.Path;
import com.soulyaroslav.gameworld.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GiftSpawner {
    // world instance
    private final World world;
    // pool for fine
    private Pool<Gift> giftPool;
    //
    private Path path;
    //
    private Random random;
    private float timeSpawn;
    private float time = 1400000000;

    public GiftSpawner(World world) {
        this.world = world;
        giftPool = new Pool<Gift>() {
            @Override
            protected Gift newObject() {
                return new Gift();
            }
        };
        random = new Random();
    }

    public void update(float delta){
        if (TimeUtils.nanoTime() - timeSpawn > time) {
            spawnGift();
        }
        // free object
        free(delta);
    }

    public void spawnGift() {
        if(random.nextFloat() > 0.95f &&
                world.getNutSpawner().getSpawnCount() % 10 == 0) {
            // Створення або отримання обєкта з пула
            Gift gift = giftPool.obtain();
            // ініціалізація обєкта
            gift.initObject(path);
            // додавання обєкта в колекцію
            world.addDynamicGameObject(gift);
            // час генерації обєкта
            timeSpawn = TimeUtils.nanoTime();
        }
    }
    // Вивільнення обєктів
    public void free(float delta) {
        for (int i = 0; i < world.getAllGameObjects().size(); i++) {
            if(world.getAllGameObjects().get(i) instanceof Gift) {
                Gift gift = (Gift) world.getAllGameObjects().get(i);
                if (gift.getState() == ObjectState.STATE_ALIVE) {
                    gift.update(delta);
                }  else {
                    world.getAllGameObjects().remove(i);
                    giftPool.free(gift);
                }
            }
        }
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
