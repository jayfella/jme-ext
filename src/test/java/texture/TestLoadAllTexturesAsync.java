package texture;

import com.jayfella.jme.extension.SingletonJmeApplication;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class TestLoadAllTexturesAsync extends SingletonJmeApplication {

    public static void main(String... args) {

        AppSettings appSettings = new AppSettings(true);
        appSettings.setResolution(1280, 720);

        TestLoadAllTexturesAsync testJmeApplication = new TestLoadAllTexturesAsync();
        testJmeApplication.setSettings(appSettings);
        testJmeApplication.setShowSettings(false);
        testJmeApplication.start();
    }


    @Override
    public void initializeApp() {

        Geometry geometry1 = new Geometry("Test Geometry 1", new Box(1,1,1));
        geometry1.setMaterial(new Material(assetManager, Materials.UNSHADED));

        Geometry geometry2 = new Geometry("Test Geometry 2", new Box(1,1,1));
        geometry2.setLocalTranslation(2.5f, 0, 0);
        geometry2.setMaterial(new Material(assetManager, Materials.UNSHADED));

        getAsyncAssetManager().loadAllTextures(result -> {
            geometry1.getMaterial().setTexture("ColorMap", result[0]);
            geometry2.getMaterial().setTexture("ColorMap", result[1]);
        }, "Textures/flowers_4096.jpg", "Textures/brick_1024.jpg");

        rootNode.attachChild(geometry1);
        rootNode.attachChild(geometry2);
    }

    @Override
    public void updateApp(float tpf) {

    }

}
