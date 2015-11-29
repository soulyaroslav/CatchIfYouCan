package com.soulyaroslav.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.AssetsConstants;
import com.soulyaroslav.entities.Acorn;
import com.soulyaroslav.entities.BadAcorn;
import com.soulyaroslav.entities.Bomb;
import com.soulyaroslav.entities.Fine;
import com.soulyaroslav.entities.Gift;
import com.soulyaroslav.entities.GoldAcorn;
import com.soulyaroslav.entities.Man;
import com.soulyaroslav.entities.OldMan;
import com.soulyaroslav.entities.Player;
import com.soulyaroslav.entitiesstate.GameLevel;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameobject.DynamicGameObject;
import com.soulyaroslav.entities.Ufo;
import com.soulyaroslav.gameui.Button;
import com.soulyaroslav.tween.BonusTween;
import com.soulyaroslav.tween.SplashTween;

import java.util.ArrayList;
import java.util.List;

public class WorldRenderer {
    // ссилка на наш мир
    private World world;
    private Assets assets;
    // splash tween for change screen
    private SplashTween splashTween;
    private BonusTween bonusTween;
    // path
    private List<Path> paths;
    // game objects
    private Ufo ufo;
    private Man man;
    private OldMan oldMan;
    private Player player;
    // Textures
    // game objects texture
    private TextureRegion acornTexture, badAcornTexture;
    private TextureRegion bombTexture, fineTexture;
    private TextureRegion giftTexture, goldAcornTexture;
    private TextureRegion ufoTexture, manTexture;
    private TextureRegion oldManTexture, playerTexture;
    // dialog lists
    private List<TextureRegion> rightDialogs;
    private List<TextureRegion> leftDialogs;
    // game assets
    private TextureRegion gameBg, grass;
    private TextureRegion treePol,
            tree_part1, tree_part2, tree_part3, tree_part4;
    private Sprite x1, x5, x10, x15, x20, x50;
    // pause
    private TextureRegion pauseUiBg, settingsTree;
    private TextureRegion pauseBg;
    // score bar
    private TextureRegion scoreBarTexture;
    // animation
    private Animation ufoSpectrAnim;
    private Animation owlAnim;
    private Animation thingAnim;
    private float currentTime = 0;
    // font
    private BitmapFont whiteScoreFont64, whiteBombFont32;
    private BitmapFont whiteTimeFont32, whiteGiftTime32;
    private BitmapFont blackScoreFont64, blackBombFont32;
    private BitmapFont blackTimeFont32, blackGiftTime32;
    // rectangles
    private Rectangle ufoAnimPlay;
    //
    private int randomRightDialog = 0;
    private int randomLeftDialog = 0;

    public WorldRenderer(World world,
                         Assets assets, SplashTween splashTween){
        this.world = world;
        this.assets = assets;
        this.splashTween = splashTween;
        // Инициализацыя вспомагательных методов
        initGameObject();
        initAssets();
    }

    private void initGameObject(){
        //
        paths = world.getPathManager().getPaths();
        //notFreePaths = worldUpdate.getPathManager().getNoFreePaths();
        ufo = world.getUfo();
        man = world.getMan();
        oldMan = world.getOldMan();
        player = world.getPlayer();
        // init rectangles
        ufoAnimPlay = new Rectangle(-124, 0, 844, 1280);
        // tween
        bonusTween = world.getBonusTween();
    }

    private void initAssets(){
        initBonusAssets();
        initTreeAssets();
        initGameAssets();
        initUI();
        initDialogs();
    }

    private void initBonusAssets(){
        x1 = new Sprite(assets.getBonusAtlas().findRegion(AssetsConstants.X1));
        x1.setPosition(35, 40);
        x1.setScale(3, 3);
        x5 = new Sprite(assets.getBonusAtlas().findRegion(AssetsConstants.X5));
        x5.setPosition(35, 40);
        x5.setScale(3, 3);
        x10 = new Sprite(assets.getBonusAtlas().findRegion(AssetsConstants.X10));
        x10.setPosition(35, 40);
        x10.setScale(3, 3);
        x15 = new Sprite(assets.getBonusAtlas().findRegion(AssetsConstants.X15));
        x15.setPosition(35, 40);
        x15.setScale(3, 3);
        x20 = new Sprite(assets.getBonusAtlas().findRegion(AssetsConstants.X20));
        x20.setPosition(35, 40);
        x20.setScale(3, 3);
        x50 = new Sprite(assets.getBonusAtlas().findRegion(AssetsConstants.X50));
        x50.setPosition(35, 40);
        x50.setScale(3, 3);
    }

    private void initTreeAssets(){
        gameBg = assets.getGameAtlas().findRegion(AssetsConstants.GAME_BG);
        treePol = assets.getGameAtlas().findRegion(AssetsConstants.TREE_POL);
        tree_part1 = assets.getGameAtlas().findRegion(AssetsConstants.TREE_PART1);
        tree_part2 = assets.getGameAtlas().findRegion(AssetsConstants.TREE_PART2);
        tree_part3 = assets.getGameAtlas().findRegion(AssetsConstants.TREE_PART3);
        tree_part4 = assets.getGameAtlas().findRegion(AssetsConstants.TREE_PART4);
        grass = assets.getGameAtlas().findRegion(AssetsConstants.GRASS);
    }

    private void initGameAssets(){
        // game objects texture
        acornTexture = assets.getGameAtlas().findRegion(AssetsConstants.ACORN);
        badAcornTexture = assets.getGameAtlas().findRegion(AssetsConstants.BAD_ACORN);
        bombTexture = assets.getGameAtlas().findRegion(AssetsConstants.BOMB);
        fineTexture = assets.getGameAtlas().findRegion(AssetsConstants.FINE);
        giftTexture = assets.getGameAtlas().findRegion(AssetsConstants.GIFT);
        goldAcornTexture = assets.getGameAtlas().findRegion(AssetsConstants.GOLD_ACORN);
        ufoTexture = assets.getGameAtlas().findRegion(AssetsConstants.UFO);
        manTexture = assets.getGameAtlas().findRegion(AssetsConstants.MAN);
        oldManTexture = assets.getGameAtlas().findRegion(AssetsConstants.OLD_MAN);
        playerTexture = assets.getGameAtlas().findRegion(AssetsConstants.PLAYER);
        // animation
        ufoSpectrAnim = new Animation(1/5f, assets.getUfoSpectrAnimAtlas().getRegions());
        owlAnim = new Animation(1/3f, assets.getOwlAnim().getRegions());
        thingAnim = new Animation(1/15f, assets.getThingAnim().getRegions());
    }

    private void initUI(){
        // pause state
        pauseBg = assets.getGameAtlas().findRegion(AssetsConstants.BG_BLACK);
        pauseUiBg = assets.getUiAtlas().findRegion(AssetsConstants.PAUSE_BG);
        settingsTree = assets.getUiAtlas().findRegion(AssetsConstants.SETTINGS_TREE);
        // score bar
        scoreBarTexture = assets.getGameAtlas().findRegion(AssetsConstants.BAR);
        // white font
        whiteScoreFont64 = new BitmapFont(Gdx.files.internal("data/font/white_font_64.fnt"), true);
        whiteBombFont32 = new BitmapFont(Gdx.files.internal("data/font/white_font_32.fnt"), true);
        whiteTimeFont32 = new BitmapFont(Gdx.files.internal("data/font/white_font_32.fnt"), true);
        whiteGiftTime32 = new BitmapFont(Gdx.files.internal("data/font/white_font_32.fnt"), true);
        // black font
        blackScoreFont64 = new BitmapFont(Gdx.files.internal("data/font/black_font_64.fnt"), true);
        blackBombFont32 = new BitmapFont(Gdx.files.internal("data/font/black_font_32.fnt"), true);
        blackTimeFont32 = new BitmapFont(Gdx.files.internal("data/font/black_font_32.fnt"), true);
        blackGiftTime32 = new BitmapFont(Gdx.files.internal("data/font/black_font_32.fnt"), true);
    }

    private void initDialogs(){
        leftDialogs = new ArrayList<TextureRegion>();
        rightDialogs = new ArrayList<TextureRegion>();
        //
        leftDialogs.add(assets.getDialogAtlas().findRegion(AssetsConstants.LEFT_TWO));
        leftDialogs.add(assets.getDialogAtlas().findRegion(AssetsConstants.LEFT_FOUR));
        leftDialogs.add(assets.getDialogAtlas().findRegion(AssetsConstants.LEFT_FIVE));
        //
        rightDialogs.add(assets.getDialogAtlas().findRegion(AssetsConstants.RIGHT_ONE));
        rightDialogs.add(assets.getDialogAtlas().findRegion(AssetsConstants.RIGHT_THREE));
        rightDialogs.add(assets.getDialogAtlas().findRegion(AssetsConstants.RIGHT_SIX));
    }

    public void render(Batch batch, ShapeRenderer sRenderer){
        batch.begin();
        // begin draw
        drawWorldBackground(batch);
        drawTreePol(batch);
        drawGrass(batch);
        drawOwlAnim(batch);
        drawAllGameObjects(batch);
        drawTree(batch);
        drawPauseBtn(batch);
        // end draw
        batch.end();
        // splash tween
        drawSplashTween(sRenderer);
    }

    private void drawBonus(Batch batch){
        switch(world.getCurrentScoreLevel()){
            case X1:
                bonusTween.prepareTransition(x1);
                x1.draw(batch);
                break;
            case X5:
                bonusTween.prepareTransition(x5);
                x5.draw(batch);
                break;
            case X10:
                bonusTween.prepareTransition(x10);
                x10.draw(batch);
                break;
            case X15:
                bonusTween.prepareTransition(x15);
                x15.draw(batch);
                break;
            case X20:
                bonusTween.prepareTransition(x20);
                x20.draw(batch);
                break;
            case X50:
                bonusTween.prepareTransition(x50);
                x50.draw(batch);
                break;
        }
    }

    private void drawPauseBtn(Batch batch) {
        if(!World.pause) {
            world.getButtonPause().drawButton(batch);
            drawBonusTime(batch);
            drawScoreBar(batch);
            drawBonus(batch);
            drawScore(batch);
            drawBombCount(batch);
            drawTime(batch);
            drawMan(batch);
            drawOldMan(batch);
            drawUfo(batch);
            drawDialogMan(batch);
            drawDialogOldMan(batch);
            drawPlayer(batch);
        } else {
            drawPause(batch);
            drawThingAnimInPause(batch);
        }
    }

    private void drawScoreBar(Batch batch){
        // draw bar
        batch.draw(scoreBarTexture, 5, 5, 301, 213);
    }

    private void drawWorldBackground(Batch batch){
        batch.disableBlending();
        // рисуем задний фон
        batch.draw(gameBg, 0, 0, 720, 1280);
        // включаем прозрачность
        batch.enableBlending();
    }

    private void drawSplashTween(ShapeRenderer sRenderer){
        splashTween.draw(sRenderer);
    }

    private void drawTreePol(Batch batch){
        batch.draw(treePol, 60, 280, treePol.getRegionWidth(), treePol.getRegionHeight());
    }
    private void drawTree(Batch batch){
        batch.draw(tree_part4, 40, 150, tree_part4.getRegionWidth(), tree_part4.getRegionHeight());
        batch.draw(tree_part3, 80, 445, tree_part3.getRegionWidth(), tree_part3.getRegionHeight());
        batch.draw(tree_part1, 0, 180,tree_part1.getRegionWidth(), tree_part1.getRegionHeight());
        batch.draw(tree_part2, 357, 370, tree_part2.getRegionWidth(), tree_part2.getRegionHeight());
    }
    private void drawGrass(Batch batch){
        batch.draw(grass, 220, 1130, grass.getRegionWidth(), grass.getRegionHeight());
    }

    private void drawPause(Batch batch){
        batch.draw(pauseBg, 0, 0);
        batch.draw(pauseUiBg, 20, 196, pauseUiBg.getRegionWidth(), pauseUiBg.getRegionHeight());
        for(Button button : world.getButtons()){
            button.drawButton(batch);
        }
        batch.draw(settingsTree, 115 ,293, settingsTree.getRegionWidth(), settingsTree.getRegionHeight());
    }

    private void drawAllGameObjects(Batch batch){
        for(DynamicGameObject dynamicGameObject : world.getAllGameObjects()) {
            if (dynamicGameObject instanceof Acorn) {
                Acorn acorn = (Acorn) dynamicGameObject;
                if (acorn.getState() == ObjectState.STATE_ALIVE) {
                    batch.draw(acornTexture, acorn.getPosition().x,
                            acorn.getPosition().y, Acorn.ACORN_WIDTH, Acorn.ACORN_HEIGHT);
                }
            }
            //
            if (dynamicGameObject instanceof BadAcorn) {
                BadAcorn badAcorn = (BadAcorn) dynamicGameObject;
                if (badAcorn.getState() == ObjectState.STATE_ALIVE) {
                    batch.draw(badAcornTexture, badAcorn.getPosition().x,
                            badAcorn.getPosition().y, BadAcorn.BAD_NUT_WIDTH, BadAcorn.BAD_NUT_HEIGHT);
                }
            }
            //
            if (dynamicGameObject instanceof Fine) {
                Fine fine = (Fine) dynamicGameObject;
                if (fine.getState() == ObjectState.STATE_ALIVE) {
                    batch.draw(fineTexture, fine.getPosition().x,
                            fine.getPosition().y, Fine.FINE_WIDTH, Fine.FINE_HEIGHT);
                }
            }
            //
            if (dynamicGameObject instanceof Gift) {
                Gift gift = (Gift) dynamicGameObject;
                if (gift.getState() == ObjectState.STATE_ALIVE) {
                    batch.draw(giftTexture, gift.getPosition().x,
                            gift.getPosition().y, Gift.GIFT_WIDTH, Gift.GIFT_HEIGHT);
                }
            }
            //
            if (dynamicGameObject instanceof Bomb) {
                Bomb bomb = (Bomb) dynamicGameObject;
                if (bomb.getState() == ObjectState.STATE_ALIVE) {
                    batch.draw(bombTexture, bomb.getPosition().x,
                            bomb.getPosition().y, Bomb.BOMB_WIDTH, Bomb.BOMB_HEIGHT);
                }
            }
            //
            if(dynamicGameObject instanceof GoldAcorn){
                GoldAcorn goldAcorn = (GoldAcorn) dynamicGameObject;
                if (goldAcorn.getState() == ObjectState.STATE_ALIVE){
                    batch.draw(goldAcornTexture, goldAcorn.getPosition().x,
                            goldAcorn.getPosition().y, GoldAcorn.GOLD_ACORN_WIDTH, GoldAcorn.GOLD_ACORN_HEIGHT);
                }
            }
        }
    }

    private void drawMan(Batch batch){
        batch.draw(manTexture, man.getPosition().x,
                man.getPosition().y, Man.MAN_WIDTH, Man.MAN_HEIGHT);
    }

    private void drawOldMan(Batch batch){
        batch.draw(oldManTexture, oldMan.getPosition().x,
                oldMan.getPosition().y, OldMan.OLD_MAN_WIDTH, OldMan.OLD_MAN_HEIGHT);
    }

    private void drawDialogMan(Batch batch){
        if(man.getPosition().x == man.getMaxPosX()) {
            batch.draw(rightDialogs.get(randomRightDialog), man.getPosition().x - 50, man.getPosition().y - 120);
        } else {
            randomRightDialog = MathUtils.random(0, rightDialogs.size() - 1);
        }
    }

    private void drawDialogOldMan(Batch batch){
        if(oldMan.getPosition().x == oldMan.getMaxPosX()) {
            batch.draw(leftDialogs.get(randomLeftDialog), oldMan.getPosition().x + 50, oldMan.getPosition().y - 100);
        } else {
            randomLeftDialog = MathUtils.random(0, leftDialogs.size() - 1);
        }
    }

    private void drawUfo(Batch batch){
        if(world.getCurrentLevel() == GameLevel.VERY_HARD) {
            if (ufoAnimPlay.contains(ufo.getPosition().x, ufo.getPosition().y)) {
                batch.draw(ufoSpectrAnim.getKeyFrame(currentTime += Gdx.graphics.getDeltaTime(), true),
                        ufo.getPosition().x + 52, ufo.getPosition().y + 100);
            }
            batch.draw(ufoTexture, ufo.getPosition().x, ufo.getPosition().y, Ufo.UFO_WIDTH, Ufo.UFO_HEIGHT);
        }
    }

    private void drawOwlAnim(Batch batch){
        batch.draw(owlAnim.getKeyFrame(currentTime += Gdx.graphics.getDeltaTime(), true), 265, 720);
    }

    private void drawThingAnimInPause(Batch batch){
        batch.draw(thingAnim.getKeyFrame(currentTime += Gdx.graphics.getDeltaTime(), false),
                155, 340);
    }

    private void drawPlayer(Batch batch){
        batch.draw(playerTexture, player.getPosition().x,
                player.getPosition().y, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
    }

    private void drawScore(Batch batch){
        if(String.valueOf(World.score).length() == 1){
            blackScoreFont64.draw(batch, String.valueOf(World.score), 184, 32);
            whiteScoreFont64.draw(batch, String.valueOf(World.score), 180, 30);
        } else if(String.valueOf(World.score).length() == 2){
            blackScoreFont64.draw(batch, String.valueOf(World.score), 164, 32);
            whiteScoreFont64.draw(batch, String.valueOf(World.score), 160, 30);
        } else if(String.valueOf(World.score).length() == 3){
            blackScoreFont64.draw(batch, String.valueOf(World.score), 144, 32);
            whiteScoreFont64.draw(batch, String.valueOf(World.score), 140, 30);
        } else if(String.valueOf(World.score).length() == 4){
            blackScoreFont64.draw(batch, String.valueOf(World.score), 124, 32);
            whiteScoreFont64.draw(batch, String.valueOf(World.score), 120, 30);
        }
    }

    private void drawBombCount(Batch batch){
        blackBombFont32.draw(batch, String.valueOf(World.bombCount), 114, 117);
        whiteBombFont32.draw(batch, String.valueOf(World.bombCount), 110, 115);
    }
    private void drawTime(Batch batch){
        blackTimeFont32.draw(batch, world.minutes + ":" + world.seconds, 104, 177);
        whiteTimeFont32.draw(batch, world.minutes + ":" + world.seconds, 100, 175);
    }
    private void drawBonusTime(Batch batch){
        blackGiftTime32.draw(batch, String.valueOf(Player.seconds), 589, 67);
        whiteGiftTime32.draw(batch, String.valueOf(Player.seconds), 585, 65);
    }

    private void drawCatchInRow(Batch batch){
        whiteScoreFont64.draw(batch, "" + world.getPlayer().getCatchInRow(), 5, 1200);
    }

    public void drawManOneRect(ShapeRenderer sRenderer){
        man.drawRect(sRenderer);
        oldMan.drawRect(sRenderer);
    }
    public void drawNetOneRect(ShapeRenderer sRenderer){
        man.drawNetRect(sRenderer);
        oldMan.drawNetRect(sRenderer);
        player.drawCatchBounds(sRenderer);
    }

    public void drawPath(ShapeRenderer sRenderer){
        for (Path path : paths) {
            path.drawPath(sRenderer);
        }
    }
}
