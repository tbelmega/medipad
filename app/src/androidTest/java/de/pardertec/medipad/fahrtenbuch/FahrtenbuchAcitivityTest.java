package de.pardertec.medipad.fahrtenbuch;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.pardertec.medipad.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Thiemo on 05.03.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class FahrtenbuchAcitivityTest {

    public static final String NEUER_KILOMETRSTAND = "11111";


    @Rule
    public ActivityTestRule<FahrtenbuchActivity> fahrtenbuchActivityTestRule =
            new ActivityTestRule<>(FahrtenbuchActivity.class);

    @Test
    public void clickNeuesBlattButton_showsFahrtenbuch() throws Exception {
        // Click on the button "Neues Blatt"
        onView(withId(R.id.neues_blatt_button)).perform(click());

        // Check if the fahrtenbuch layout is displayed
        onView(withId(R.id.fahrtenbuch_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.neue_fahrt_button)).check(matches(isEnabled()));

        onView(withText("42")).check(doesNotExist());
    }

    @Test
    public void clickBearbeitenButton_opensBearbeitenView() throws Exception {
        // Click on the button "Bearbeiten"
        onView(withId(R.id.bearbeiten_button)).perform(click());

        // Check if the fahrzeugdaten layout is displayed
        onView(withId(R.id.fahrzeugdaten_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void clickNeuFahrtButton_opensZieleingabeView() throws Exception {
        // Click on the buttons "Neues Blatt" and then "Neue Fahrt"
        onView(withId(R.id.neues_blatt_button)).perform(click());
        onView(withId(R.id.neue_fahrt_button)).perform(click());

        // Check if the zieleingabe root is displayed
        onView(withId(R.id.zieleingabe_layout)).check(matches(isDisplayed()));
    }


    @Test
    public void addFahrtCycle() throws Exception {
        // Click on the buttons "Neues Blatt" and then "Neue Fahrt"
        onView(withId(R.id.neues_blatt_button)).perform(click());
        onView(withId(R.id.neue_fahrt_button)).perform(click());

        //Zieleingabe
        onView(withId(R.id.kilometerstand_edit)).perform(typeText(NEUER_KILOMETRSTAND));
        onView(withId(R.id.adresse_edit)).perform(typeText("Name\nStraße Nummer\nPLZ Ort"));
        onView(withId(R.id.btn_add_fahrt)).perform(click());

        onView(withText(NEUER_KILOMETRSTAND)).check(matches(isDisplayed()));

        // Check if the abfahrt button is enabled and click it
        onView(withId(R.id.abfahrt_button)).check(matches(isEnabled()));
        onView(withId(R.id.abfahrt_button)).perform(click());

        // Check if the ankunft button is enabled and click it
        onView(withId(R.id.ankunft_button)).check(matches(isEnabled()));
        onView(withId(R.id.ankunft_button)).perform(click());

        // Check if the neue fahrt button is enabled
        onView(withId(R.id.neue_fahrt_button)).check(matches(isEnabled()));
    }

}
