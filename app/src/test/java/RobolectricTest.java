import android.widget.Button;
import android.widget.TextView;

import com.example.arsenko.globallogictask.BuildConfig;
import com.example.arsenko.globallogictask.MainActivity;
import com.example.arsenko.globallogictask.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)

public class RobolectricTest {

    private MainActivity activity;

    @Before
    public void init() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void textView_shouldKeepActualCurrentState() throws Exception {
        activity.findViewById(R.id.lock_button).performClick();
        TextView text =  activity.findViewById(R.id.state_view);
        String stateText = text.getText().toString();
        String currentState = activity.mCurrentState;
        assertEquals(stateText, currentState);
    }

    @Test
    public void shouldNotBeNull() throws Exception{

        assertNotNull(activity);

        TextView textView = activity.findViewById(R.id.state_view);
        assertNotNull(textView);

        Button lockBtn = activity.findViewById(R.id.lock_button);
        assertNotNull(lockBtn);

        Button lockX2Btn = activity.findViewById(R.id.lock_x2_button);
        assertNotNull(lockX2Btn);

        Button unlockBtn = activity.findViewById(R.id.unlock_button);
        assertNotNull(unlockBtn);

        Button unlockX2Btn = activity.findViewById(R.id.unlock_x2_button);
        assertNotNull(unlockX2Btn);

    }
    @Test
    public void buttonClickShowToast() throws Exception {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .get();

        Button lock = activity.findViewById(R.id.lock_button);
        lock.performClick();
        assertEquals(ShadowToast.getTextOfLatestToast(), "LOCK");

        Button lock_x2 = activity.findViewById(R.id.lock_x2_button);
        lock_x2.performClick();
        assertEquals(ShadowToast.getTextOfLatestToast(), "LOCK_X2");

        Button unlock = activity.findViewById(R.id.unlock_button);
        unlock.performClick();
        assertEquals(ShadowToast.getTextOfLatestToast(), "UNLOCK");

        Button unlock_x2 = activity.findViewById(R.id.unlock_x2_button);
        unlock_x2.performClick();
        assertEquals(ShadowToast.getTextOfLatestToast(), "UNLOCK_X2");
    }


}
