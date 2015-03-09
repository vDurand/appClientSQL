package appclientsql.valentin.durand.net.appclientsql;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by Calvin on 27/02/15.
 */
public class SetPreferencesFragmentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(android.R.id.content,new PreferencesFragments()).commit();
    }
}
