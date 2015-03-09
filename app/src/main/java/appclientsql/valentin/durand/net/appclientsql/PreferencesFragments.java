package appclientsql.valentin.durand.net.appclientsql;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Calvin on 27/02/15.
 */
public class PreferencesFragments extends PreferenceFragment {
    public static final String PREFKEY_IPSERVEUR ="PREFKEY_IPSERVEUR";
    public static final String PREFKEY_PORTSERVEUR="PREFKEY_PORTSERVEUR";
    public static final String PREFKEY_USERNAME ="PREFKEY_USERNAME";
    public static final String PREFKEY_PASSWORD ="PREFKEY_PASSWORD";

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        addPreferencesFromResource(R.xml.userpreferences);
    }
}
