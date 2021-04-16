package texture;

import com.jayfella.jme.extension.SingletonJmeApplication;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class TestLoadTextureAsync extends SingletonJmeApplication {

    public static void main(String... args) {

        AppSettings appSettings = new AppSettings(true);
        appSettings.setResolution(1280, 720);

        TestLoadTextureAsync testJmeApplication = new TestLoadTextureAsync();
        testJmeApplication.setSettings(appSettings);
        testJmeApplication.setShowSettings(false);
        testJmeApplication.start();
    }


    @Override
    public void initializeApp() {

        Geometry geometry = new Geometry("Test Geometry", new Box(1,1,1));
        geometry.setMaterial(new Material(assetManager, Materials.UNSHADED));

        getAsyncAssetManager().loadTexture("Textures/flowers_4096.jpg", result -> {
            geometry.getMaterial().setTexture("ColorMap", result);
        });

        rootNode.attachChild(geometry);
    }

    @Override
    public void updateApp(float tpf) {

    }

}
