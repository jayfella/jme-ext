import com.jayfella.jme.extension.SingletonJmeApplication;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.system.AppSettings;

/**
 * Executes a runnable on the next frame.
 *
 * One of the main reasons for needing this is when adding new AppStates to the AppStateManager and
 * having to wait for it to be initialized. This example demonstrates that behavior.
 *
 */
public class TestEnqueueNextFrame extends SingletonJmeApplication {

    public static void main(String... args) {

        AppSettings appSettings = new AppSettings(true);
        appSettings.setResolution(1280, 720);

        TestEnqueueNextFrame testJmeApplication = new TestEnqueueNextFrame();
        testJmeApplication.setSettings(appSettings);
        testJmeApplication.setShowSettings(false);
        testJmeApplication.start();
    }

    private TestEnqueueNextFrame() {
        super(new TestAppState());
    }

    @Override
    public void initializeApp() {

        // testAppState is not yet initialized.
        System.out.println("String value: " + getStateManager().getState(TestAppState.class).getExampleString());

        // testAppState should be initialized in the next frame.
        enqueueNextFrame(() -> System.out.println("String value: " + getStateManager().getState(TestAppState.class).getExampleString()));

    }

    @Override
    public void updateApp(float tpf) {

    }

    private static class TestAppState extends BaseAppState {

        private String exampleString = "Before initialization.";

        @Override
        protected void initialize(Application app) {
            exampleString = "After initialization.";

        }

        @Override protected void cleanup(Application app) { }
        @Override protected void onEnable() { }
        @Override protected void onDisable() { }

        public String getExampleString() {
            return exampleString;
        }

    }

}
