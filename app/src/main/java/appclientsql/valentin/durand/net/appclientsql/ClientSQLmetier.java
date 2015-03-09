package appclientsql.valentin.durand.net.appclientsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.util.Log;

public class ClientSQLmetier {

	private static final String TAG = "ClientSQLMetier";
	private String serveurBDD;
	private String nomBDD;
	private String userBDD;
	private String mdpBDD;
	private String portBDD;
	private String connexionStringBDD;
	private Connection conn = null;
	
	public ClientSQLmetier(String serveur, String port, String bdd, String user, String mdp, int timeout) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		this.setServeurBDD(serveur);
		this.setNomBDD(bdd);
		this.setUserBDD(user);
		this.setMdpBDD(mdp);
		String to = String.valueOf(timeout);
		setConnexionStringBDD("jdbc:jtds:sqlserver://"+getServeurBDD().toString()+":"+port.toString()+"/"+bdd+";encrypt=false;instance=SQLEXPRESS;loginTimeout="+to+";socketTimeout="+to+";");
		// Chargement du driver
		Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		DriverManager.setLoginTimeout(timeout);
	}

	public ResultSet getTableFournisseurs() throws SQLException
	{
		if( conn == null )
			conn = DriverManager.getConnection(this.connexionStringBDD,this.userBDD, this.mdpBDD);
		Log.i(TAG,"open BDD");
		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery("select * from F");
		return result;
	}
	
	public int addNewFournisseur(int nf, String nom, String statut, String ville) 
	{
		int result = -1;
		if( conn == null )
		{
			try {
				conn = DriverManager.getConnection(this.connexionStringBDD,this.userBDD, this.mdpBDD);
				} catch (SQLException e) {
					result = -1;
				}
		}
				Log.i(TAG,"open BDD");
				Statement stmt;
				try {
					stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					PreparedStatement prepstmt = conn.prepareStatement("INSERT INTO F (NF, nomF, statut, ville) VALUES (?, ?, ?, ?)");
					prepstmt.setInt(1, nf);
					prepstmt.setString(2, nom);
					prepstmt.setString(3, statut);
					prepstmt.setString(4, ville);
					result = prepstmt.executeUpdate();
					if(prepstmt != null)
						prepstmt.close();
					if(stmt != null)
						stmt.close();
				} catch (SQLException e) {
					result = -1;
				}
		return result;
	}
	
	public int deleteFournisseur(int NF)
	{
		int result = -1;
		if( conn == null )
		{
			try {
				conn = DriverManager.getConnection(this.connexionStringBDD,this.userBDD, this.mdpBDD);
				} catch (SQLException e) {
					result = -1;
				}
		}
				Log.i(TAG,"open BDD");
				Statement stmt;
				try {
					stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					PreparedStatement prepstmt = conn.prepareStatement("DELETE FROM F  WHERE NF = ?");
					prepstmt.setInt(1, NF);
					result = prepstmt.executeUpdate();
					if(prepstmt != null)
						prepstmt.close();
					if(stmt != null)
						stmt.close();
				} catch (SQLException e) {
					result = -1;
				}
		return result;
	}
	
	  public void finalize()
      {
		  if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      }
      
	public String getServeurBDD() {
		return serveurBDD;
	}

	public void setServeurBDD(String serveurBDD) {
		this.serveurBDD = serveurBDD;
	}

	public String getNomBDD() {
		return nomBDD;
	}

	public void setNomBDD(String nomBDD) {
		this.nomBDD = nomBDD;
	}

	public String getMdpBDD() {
		return mdpBDD;
	}

	public void setMdpBDD(String mdpBDD) {
		this.mdpBDD = mdpBDD;
	}

	public String getPortBDD() {
		return portBDD;
	}

	public void setPortBDD(String portBDD) {
		this.portBDD = portBDD;
	}

	@SuppressWarnings("unused")
	private String getConnexionStringBDD() {
		return connexionStringBDD;
	}

	private void setConnexionStringBDD(String connexionStringBDD) {
		this.connexionStringBDD = connexionStringBDD;
	}

	public String getUserBDD() {
		return userBDD;
	}

	public void setUserBDD(String userBDD) {
		this.userBDD = userBDD;
	}
	
	
}
