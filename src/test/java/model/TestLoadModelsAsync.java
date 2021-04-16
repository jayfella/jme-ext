package model;

import com.jayfella.jme.extension.SingletonJmeApplication;
import com.jayfella.jme.extension.threading.callback.IndexedAsyncCallback;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

/**
 * Loads multiple models asynchronously, with the callback returning each model as it is loaded.
 */
public class TestLoadModelsAsync extends SingletonJmeApplication {

    public static void main(String... args) {

        AppSettings appSettings = new AppSettings(true);
        appSettings.setResolution(1280, 720);

        TestLoadModelsAsync testJmeApplication = new TestLoadModelsAsync();
        testJmeApplication.setSettings(appSettings);
        testJmeApplication.setShowSettings(false);
        testJmeApplication.start();
    }

    @Override
    public void initializeApp() {

        viewPort.setBackgroundColor(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));

        rootNode.addLight(new DirectionalLight(new Vector3f(-1,-1,-1).normalizeLocal()));

        String[] models = {
                "Models/Porsche_911/scene.gltf",
                "Models/Old_Rusty/scene.gltf"
        };

        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText text = new BitmapText(font, false);
        text.setText("Loading Models: 0 / 2");
        text.setLocalTranslation(cam.getWidth() * 0.5f, cam.getHeight() * 0.5f, 0);

        guiNode.attachChild(text);

        getAsyncAssetManager().loadModels(new IndexedAsyncCallback<Spatial>() {
            @Override
            public void complete(Spatial result, int index, int count, int total) {

                result.setLocalTranslation(index * 4, 0, 0);

                // the "old_rusty" model needs to be scaled.
                // alternatively we could use result.getKey().getName() for comparison.
                if (index == 1) {
                    result.setLocalScale(0.01f);
                }

                rootNode.attachChild(result);

                text.setText("Loading Models: " + count + " / " + total);

                if (count == total) {
                    text.removeFromParent();
                }

            }
        }, models);

    }

    @Override
    public void updateApp(float tpf) {


    }

}
