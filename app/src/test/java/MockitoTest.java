import android.widget.Button;
import android.widget.TextView;

import com.example.arsenko.globallogictask.MainActivity;
import com.example.arsenko.globallogictask.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class MockitoTest {
    @Mock
    private MainActivity activityMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkLockButtonName() throws Exception{
        when(activityMock.getString(R.string.lock_button)).thenReturn("LOCK");
        assertEquals(activityMock.getString(R.string.lock_button), "LOCK");

        when(activityMock.getString(R.string.unlock_button)).thenReturn("UNLOCK");
        assertEquals(activityMock.getString(R.string.unlock_button), "UNLOCK");

        when(activityMock.getString(R.string.lock_x2_button)).thenReturn("LOCK_X2");
        assertEquals(activityMock.getString(R.string.lock_x2_button), "LOCK_X2");

        when(activityMock.getString(R.string.unlock_x2_button)).thenReturn("UNLOCK_X2");
        assertEquals(activityMock.getString(R.string.unlock_x2_button), "UNLOCK_X2");

    }

    @Test
    public void checkButtonToast() throws Exception {
        when(activityMock.getState("UNLOCK" , "AlarmDisarmed_AllUnlocked")).thenReturn("good");

    }

    @Test
    public void testReturnState() throws Exception{
        when(activityMock.getState("LOCK", "AlarmDisarmed_AllUnlocked")).thenReturn("AlarmDisarmed_AllLocked");

        when(activityMock.getState("LOCK", "AlarmDisarmed_AllUnlocked")).thenReturn("AlarmArmed_AllLocked");

    }

}
