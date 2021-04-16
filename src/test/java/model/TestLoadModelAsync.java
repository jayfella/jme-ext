package model;

import com.jayfella.jme.extension.SingletonJmeApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;

/**
 * Loads a single model asynchronously.
 */
public class TestLoadModelAsync extends SingletonJmeApplication {

    public static void main(String... args) {

        AppSettings appSettings = new AppSettings(true);
        appSettings.setResolution(1280, 720);

        TestLoadModelAsync testJmeApplication = new TestLoadModelAsync();
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
        text.setText("Loading Model...");
        text.setLocalTranslation(cam.getWidth() * 0.5f, cam.getHeight() * 0.5f, 0);

        guiNode.attachChild(text);

        getAsyncAssetManager().loadModel("Models/Porsche_911/scene.gltf", result -> {
            rootNode.attachChild(result);
            text.removeFromParent();
        });
    }

    @Override
    public void updateApp(float tpf) {

    }

}
