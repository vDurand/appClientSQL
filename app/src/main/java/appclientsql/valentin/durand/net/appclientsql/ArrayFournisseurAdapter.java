package appclientsql.valentin.durand.net.appclientsql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Calvin on 03/03/15.
 */
public class ArrayFournisseurAdapter extends ArrayAdapter<Fournisseur> {

    // Déclaration d'une liste d'items
    private ArrayList<Fournisseur> objets;
    private int item_id;

    //Surcharge du constructeur ArrayAdapteur
    public ArrayFournisseurAdapter(Context context, int textViewResourceId, ArrayList<Fournisseur> objects)
    {
        super(context, textViewResourceId, objects);
        this.objets = objects;
        this.item_id = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Vue à mettre à jour
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(this.item_id, null);
        }
        Fournisseur fcourant = objets.get(position);
        if (fcourant != null) {
            TextView tv_nf = (TextView) v.findViewById(R.id.nf);
            TextView tv_nomF = (TextView) v.findViewById(R.id.nomF);
            TextView tv_statut = (TextView) v.findViewById(R.id.statut);
            TextView tv_ville = (TextView) v.findViewById(R.id.ville);
            ImageView icone = (ImageView) v.findViewById(R.id.imgUsine);
            if (tv_nf != null) tv_nf.setText(fcourant.NF);
            if (tv_nomF != null) tv_nomF.setText(fcourant.nomF);
            if (tv_statut != null) tv_statut.setText(fcourant.statut);
            if (tv_ville != null) tv_ville.setText(fcourant.ville);
            if (icone != null) icone.setImageResource(R.drawable.usine);
        }
        return v;
    }
}