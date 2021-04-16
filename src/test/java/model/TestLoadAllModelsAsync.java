package model;

import com.jayfella.jme.extension.SingletonJmeApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;

/**
 * Loads multiple models asynchronously, with the callback returning all models when they have all loaded.
 */
public class TestLoadAllModelsAsync extends SingletonJmeApplication {

    public static void main(String... args) {

        AppSettings appSettings = new AppSettings(true);
        appSettings.setResolution(1280, 720);

        TestLoadAllModelsAsync testJmeApplication = new TestLoadAllModelsAsync();
        testJmeApplication.setSettings(appSettings);
        testJmeApplication.setShowSettings(false);
        testJmeApplication.start();
    }

    @Override
    public void initializeApp() {

        viewPort.setBackgroundColor(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));

        rootNode.addLight(new DirectionalLight(new Vector3f(-1,-1,-1).normalizeLocal()));

        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText text = new BitmapText(font, false);
        text.setText("Loading Models...");
        text.setLocalTranslation(cam.getWidth() * 0.5f, cam.getHeight() * 0.5f, 0);

        guiNode.attachChild(text);

        getAsyncAssetManager().loadAllModels(result -> {

            rootNode.attachChild(result[0]);

            result[1].setLocalTranslation(4, 0, 0);
            result[1].setLocalScale(0.01f);
            rootNode.attachChild(result[1]);

            text.removeFromParent();
        }, "Models/Porsche_911/scene.gltf", "Models/Old_Rusty/scene.gltf");
    }

    @Override
    public void updateApp(float tpf) {

    }

}
