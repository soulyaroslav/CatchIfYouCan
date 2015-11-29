package com.soulyaroslav.spawners;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.soulyaroslav.entities.Leaf;
import com.soulyaroslav.entitiesstate.ObjectState;

import java.util.ArrayList;
import java.util.List;

public class LeafSpawner {

    private List<Leaf> leafs;
    private Pool<Leaf> leafPool;

    private float timeSpawn;

    public LeafSpawner() {
        this.leafs = new ArrayList<Leaf>();
        this.leafPool = new Pool<Leaf>() {
            @Override
            protected Leaf newObject() {
                return new Leaf();
            }
        };
    }

    public void update(float delta){
        if (TimeUtils.nanoTime() - timeSpawn > 1000000000 / 4) {
            spawn();
        }

        free(delta);
    }

    private void spawn() {
        // Створення або отримання обєкта з пула
        Leaf leaf = leafPool.obtain();
        // ініціалізація обєкта
        leaf.initObject();
        // додавання обєкта в колекцію
        leafs.add(leaf);
        // час генерації обєкта
        timeSpawn = TimeUtils.nanoTime();
    }
    // Вивільнення обєктів
    private void free(float delta) {
        for (int i = 0; i < leafs.size(); i++) {
            Leaf leaf = leafs.get(i);
            if (leaf.getState() == ObjectState.STATE_ALIVE) {
                leaf.update(delta);
            } else {
                leafs.remove(i);
                leafPool.free(leaf);
            }
        }
    }


    public List<Leaf> getLeafs(){
        return leafs;
    }
}
