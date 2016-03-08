package de.pardertec.medipad.fahrtenbuch.input;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;

import de.pardertec.medipad.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Thiemo on 05.03.2016.
 */
@SmallTest
public class ZieleingabeActivityTest {
    @Rule
    public ActivityTestRule<ZieleingabeActivity> fahrtenbuchActivityTestRule =
            new ActivityTestRule<>(ZieleingabeActivity.class);

    @Test
    public void performZieleingabe_showsFahrtenbuch() throws Exception {
        //Zieleingabe
        onView(withId(R.id.kilometerstand_edit)).perform(typeText("111"));
        onView(withId(R.id.adresse_edit)).perform(typeText("Name\nStraße Nummer\nPLZ Ort"));
        onView(withId(R.id.btn_add_fahrt)).perform(click());

        // Check if the fahrtenbuch layout is displayed
        onView(withId(R.id.fahrtenbuch_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void missingValues_showsZieleingabe() throws Exception {
        //Clear text edit fields
        onView(withId(R.id.kilometerstand_edit)).perform(clearText());
        onView(withId(R.id.adresse_edit)).perform(clearText());
        onView(withId(R.id.btn_add_fahrt)).perform(click());

        // Check if the zieleingabe layout is still displayed
        onView(withId(R.id.zieleingabe_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void abortZieleingabe_showsFahrtenbuch() throws Exception {
        //Zieleingabe
        onView(withId(R.id.btn_abort)).perform(click());

        // Check if the fahrtenbuch layout is displayed
        onView(withId(R.id.fahrtenbuch_layout)).check(matches(isDisplayed()));
    }
}
