package appclientsql.valentin.durand.net.appclientsql;

import java.io.Serializable;

public class Fournisseur implements Serializable{
    public String NF;
    public String nomF;
    public String statut;
    public String ville;
    private static final long serialVersionUID = 12L;

    public Fournisseur(String NF,String nomF,String statut,String ville) {
        this.NF = NF;
        this.nomF = nomF;
        this.statut = statut;
        this.ville =ville;
    }
    @Override
    public String toString() {
        return (this.NF+ "\t : "+this.nomF+ "\t : "+this.statut+ "\t : "+this.ville);
    }
}