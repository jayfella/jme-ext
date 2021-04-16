package texture;

import com.jayfella.jme.extension.SingletonJmeApplication;
import com.jayfella.jme.extension.threading.callback.IndexedAsyncCallback;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;

public class TestLoadTexturesAsync extends SingletonJmeApplication {

    public static void main(String... args) {

        AppSettings appSettings = new AppSettings(true);
        appSettings.setResolution(1280, 720);

        TestLoadTexturesAsync testJmeApplication = new TestLoadTexturesAsync();
        testJmeApplication.setSettings(appSettings);
        testJmeApplication.setShowSettings(false);
        testJmeApplication.start();
    }


    @Override
    public void initializeApp() {

        Geometry[] geometries = {
                new Geometry("Test Geometry 1", new Box(1,1,1)),
                new Geometry("Test Geometry 2", new Box(1,1,1)),
        };

        geometries[0].setMaterial(new Material(assetManager, Materials.UNSHADED));
        geometries[1].setMaterial(new Material(assetManager, Materials.UNSHADED));

        // move the second geometry out of the way of the first
        geometries[1].move(2.5f, 0, 0);

        getAsyncAssetManager().loadTextures(new IndexedAsyncCallback<Texture>() {
            @Override
            public void complete(Texture result, int index, int count, int total) {
                geometries[index].getMaterial().setTexture("ColorMap", result);
            }
        }, "Textures/flowers_4096.jpg", "Textures/brick_1024.jpg");

        rootNode.attachChild(geometries[0]);
        rootNode.attachChild(geometries[1]);
    }

    @Override
    public void updateApp(float tpf) {

    }

}
