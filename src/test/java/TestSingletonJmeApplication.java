import com.jayfella.jme.extension.SingletonJmeApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

/**
 * Extends SingletonJmeApplication instead of SimpleApplication
 */
public class TestSingletonJmeApplication extends SingletonJmeApplication {

    public static void main(String... args) {

        TestSingletonJmeApplication app = new TestSingletonJmeApplication();
        app.start();

    }

    @Override
    public void initializeApp() {

        viewPort.setBackgroundColor(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));

        // just an example of how to access the singleton application.
        Spatial model = SingletonJmeApplication.getInstance()
                .getAssetManager().loadModel("Models/Porsche_911/scene.gltf");

        rootNode.attachChild(model);

    }

    @Override
    public void updateApp(float tpf) {

    }

}
