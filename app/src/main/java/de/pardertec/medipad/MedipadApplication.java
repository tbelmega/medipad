package de.pardertec.medipad;

import android.annotation.TargetApi;
import android.app.Application;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.print.PrintHelper;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import de.pardertec.medipad.fahrtenbuch.model.Fahrt;
import de.pardertec.medipad.fahrtenbuch.model.Fahrtenbuch;
import de.pardertec.medipad.fahrtenbuch.model.Fahrtenzettel;

/**
 * Created by Thiemo on 02.03.2016.
 */
public class MedipadApplication extends Application {

    private static final MedipadApplication instance = new MedipadApplication();

    public static final String TAG = "MediPad.Fahrtenbuch";
    public static final String EXTRA_UUID = "uuid";
    public static final String EXTRA_KILOMETERSTAND = "kilometerstand";
    public static final String EXTRA_ADRESSE = "adresse";
    public static final String EXTRA_ABFAHRT = "abfahrtszeit";
    public static final String EXTRA_ANKUNFT = "ankunftszeit";

    public static final String EXTRA_KENNZEICHEN = "kennzeichen";
    public static final String EXTRA_FAHRER = "fahrer";
    public static final String EXTRA_SANI = "sani";

    public static final String EXTRA_INTENTION = "intention";

    public static final DateFormat GERMAN_STD_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.GERMANY);

    private Fahrtenbuch theFahrtenbuch = new Fahrtenbuch();


    public MedipadApplication() {
        //TODO: Remove dummy data, load actual data
        this.theFahrtenbuch.addFahrtenzettel(getTestFahrtenzettel());
    }

    public static Fahrtenbuch getFahrtenbuch() {
        return getInstance().theFahrtenbuch;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static MedipadApplication getInstance(){
        return instance;
    }

    public static Fahrtenzettel getCurrentFahrtenzettel(){
        return getInstance().theFahrtenbuch.getLastFahrtenzettel();
    }

    public static void startNewFahrtenZettel() {
        getInstance().theFahrtenbuch.startFahrtenzettel();
    }

    ///////////////////////////////////////////
    //// DUMMY DATA
    //////////////////////////////////////////

    public static Fahrtenzettel getTestFahrtenzettel() {
        Fahrt ersteFahrt = new Fahrt();
        ersteFahrt.setAbfahrtszeit(System.currentTimeMillis() - 1000000);
        ersteFahrt.setAnkunftszeit(System.currentTimeMillis());
        ersteFahrt.setZiel("Wichtelwerkstatt\n96930 Polarkreis\nFinnland");
        ersteFahrt.setSonderrechte(true);
        ersteFahrt.setKilometerBeginn(42);

        Fahrt zweiteFahrt = new Fahrt();
        zweiteFahrt.setAbfahrtszeit(System.currentTimeMillis());
        zweiteFahrt.setAnkunftszeit(System.currentTimeMillis() + 1000000);
        zweiteFahrt.setZiel("Dr. K. Ruprecht\nDraußen vom Walde\n10459 Neubrandenburg");
        zweiteFahrt.setKilometerBeginn(297);
        zweiteFahrt.setInf(true);

        Fahrt dritteFahrt = new Fahrt();
        dritteFahrt.setAbfahrtszeit(System.currentTimeMillis() + 1000000);
        dritteFahrt.setZiel("Nach Hause");
        dritteFahrt.setKilometerBeginn(1001);

        Fahrtenzettel testFahrtenzettel = new Fahrtenzettel();
        testFahrtenzettel.setFahrer("Rudolf Rentier");
        testFahrtenzettel.setSani("Santa Claus");
        testFahrtenzettel.setKennzeichen("M-ERRY XMAS");

        testFahrtenzettel.addFahrt(ersteFahrt);
        testFahrtenzettel.addFahrt(dritteFahrt);
        testFahrtenzettel.addFahrt(zweiteFahrt);

        return testFahrtenzettel;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void storeCurrentFahrtenzettelAsPdf(View fahrtenbuchLayout) {
        PrintAttributes.Builder b = new PrintAttributes.Builder();
        b.setMediaSize(PrintAttributes.MediaSize.ISO_A2.asLandscape());
        b.setMinMargins(new PrintAttributes.Margins(20, 20, 20, 20));
        b.setColorMode(PrintAttributes.COLOR_MODE_COLOR);
        PrintAttributes attr = b.build();

        // open a new document
        PrintedPdfDocument document = new PrintedPdfDocument(fahrtenbuchLayout.getContext(),
                attr);

        // start a page
        PdfDocument.Page page = document.startPage(0);

        // draw something on the page
        fahrtenbuchLayout.draw(page.getCanvas());

        // finish the page
        document.finishPage(page);

        // write the document content
        File f = new File(fahrtenbuchLayout.getContext().getExternalFilesDir(null),"fahrtenzettel" + System.currentTimeMillis() + ".pdf");
        try {
            f.createNewFile();
            document.writeTo(new FileOutputStream(f));
            Toast.makeText(fahrtenbuchLayout.getContext(),"Saved file " + f.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //close the document
        document.close();
    }
}
