package appclientsql.valentin.durand.net.appclientsql;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    static final private int MENU_PREFERENCES = Menu.FIRST;
    static final private int CODE_REQUETE_PREFERENCES = 1;
    static final private String TABLE_F_KEY = "Table_F";
    private String ip = "serveurPush.michel-marie.net";
    private String port = "1433";
    private String username = "manager";
    private String password = "Password1234";
    private ClientSQLmetier clientBDD;
    private int res;
    public ListView listeView;
    public ArrayList <Fournisseur> arrayF;
    public ArrayFournisseurAdapter arrayFournisseurAdapt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateAttributsFromPreferences();
        try {
            clientBDD = new ClientSQLmetier(ip, port, "Entreprise", username, password, 5);
        }
        catch (SQLException e) {
            System.err.println("Caught SQLException: " + e.getMessage());
        }
        catch (InstantiationException e) {
            System.err.println("Caught InstantiationException: " + e.getMessage());
        }
        catch (IllegalAccessException e) {
            System.err.println("Caught IllegalAccessException: " + e.getMessage());
        }
        catch (ClassNotFoundException e){
            System.err.println("Caught ClassNotFoundException: " + e.getMessage());
        }



        this.listeView = (ListView)findViewById(R.id.ListeF);
        int layoutID = R.layout.item_perso;

        if(savedInstanceState!=null){
            this.arrayF = (ArrayList<Fournisseur>) savedInstanceState.getSerializable(MainActivity.TABLE_F_KEY);
        }
        else{
            this.arrayF = new ArrayList<Fournisseur>();
        }
        this.arrayFournisseurAdapt = new ArrayFournisseurAdapter(this,layoutID,arrayF);
        this.listeView.setAdapter(this.arrayFournisseurAdapt);

        this.listeView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int NFaSuppr = Integer.parseInt(arrayF.get(position).NF);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Suppression")
                        .setMessage("Voulez vous supprimer ce fournisseur?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                removeOneFournisseur(NFaSuppr);
                            }

                        })
                        .setNegativeButton("Non", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MainActivity.TABLE_F_KEY, this.arrayF);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.menu_preferences);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case (MENU_PREFERENCES): {
                Class c = SetPreferencesFragmentActivity.class;
                Intent i = new Intent(this, c);
                startActivityForResult(i, CODE_REQUETE_PREFERENCES);
                return true;
            }
        }
        return false;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUETE_PREFERENCES)
            this.updateAttributsFromPreferences();
    }

    public void updateAttributsFromPreferences(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = prefs.getString( PreferencesFragments.PREFKEY_IPSERVEUR, "serveurPush.michel-marie.net");
        port = prefs.getString( PreferencesFragments.PREFKEY_PORTSERVEUR, "1433");
        username = prefs.getString( PreferencesFragments.PREFKEY_USERNAME, "manager");
        password = prefs.getString( PreferencesFragments.PREFKEY_PASSWORD, "Password1234");
    }

    public void clickBtnAddFourn(View v){
        EditText id = (EditText)findViewById(R.id.editTextId);
        EditText ent = (EditText)findViewById(R.id.editTextEnt);
        EditText stat = (EditText)findViewById(R.id.editTextStat);
        EditText ville = (EditText)findViewById(R.id.editTextVille);

        String idtxttemp = id.getText().toString();
        final String enttxt = ent.getText().toString();
        final String stattxt = stat.getText().toString();
        final String villetxt = ville.getText().toString();

        if(!idtxttemp.matches("") && idtxttemp.matches("\\d+") && !enttxt.matches("") && !stattxt.matches("") && !villetxt.matches("")){
            final int idtxt = Integer.parseInt(idtxttemp);

            new Thread(new Runnable() {
                public void run() {
                    try{
                        res = clientBDD.addNewFournisseur(idtxt, enttxt, stattxt, villetxt);
                    }
                    catch(Exception e) {
                        System.err.println("Exception: " + e.getMessage());
                    }
                    runOnUiThread(new Runnable(){
                        public void run(){
                            if(res == 1){
                                AlertDialog.Builder alertB = new
                                        AlertDialog.Builder(MainActivity.this);
                                alertB.setTitle("Fournisseur ajouté avec succés"); alertB.show();
                            }
                            if(res == -1){
                                AlertDialog.Builder alertB = new
                                        AlertDialog.Builder(MainActivity.this);
                                alertB.setTitle("Erreur lors de l'ajout"); alertB.show();
                            }
                        }
                    });
                }
            }).start();
        }
        else{
            AlertDialog.Builder alertB = new
                    AlertDialog.Builder(this);
            alertB.setTitle("Merci de remplir tous les champs"); alertB.show();
        }
    }

    public void clickBtnListFourn(View v){
        LoadFournisseurList();
    }

    public void LoadFournisseurList(){
        if(!this.arrayF.isEmpty()){
            this.arrayF.clear();
        }
        new Thread(new Runnable() {
            public void run() {
                try{
                    ResultSet resultat = clientBDD.getTableFournisseurs();
                    while(resultat.next()){
                        String id = resultat.getString("NF");
                        String nom = resultat.getString("nomF");
                        String stat = resultat.getString("statut");
                        String ville = resultat.getString("ville");
                        MainActivity.this.arrayF.add(new Fournisseur(id, nom, stat, ville));
                    }
                }
                catch(SQLException e) {
                    System.err.println("Exception: " + e.getMessage());
                }
                runOnUiThread(new Runnable(){
                    public void run(){
                        MainActivity.this.arrayFournisseurAdapt.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    public void removeOneFournisseur(final int NFaSuppr){
        new Thread(new Runnable() {
            public void run() {
                try{
                    res = clientBDD.deleteFournisseur(NFaSuppr);
                }
                catch(Exception e) {
                    System.err.println("Exception: " + e.getMessage());
                }
                runOnUiThread(new Runnable(){
                    public void run(){
                        if(res == 1){
                            AlertDialog.Builder alertB = new
                                    AlertDialog.Builder(MainActivity.this);
                            alertB.setTitle("Fournisseur supprimé avec succés"); alertB.show();
                            LoadFournisseurList();
                        }
                        if(res == -1){
                            AlertDialog.Builder alertB = new
                                    AlertDialog.Builder(MainActivity.this);
                            alertB.setTitle("Erreur lors de la suppression"); alertB.show();
                        }
                    }
                });
            }
        }).start();
    }
}

