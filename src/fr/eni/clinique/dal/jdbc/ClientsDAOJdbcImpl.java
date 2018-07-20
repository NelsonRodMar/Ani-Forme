package fr.eni.clinique.dal.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni.clinique.bo.Clients;
import fr.eni.clinique.bo.Personnels;
import fr.eni.clinique.dal.ClientsDAO;
import fr.eni.clinique.dal.DALException;

public class ClientsDAOJDBCImpl implements ClientsDAO{
	
	/*Constantes*/
	private static String SQL_GETALLCLIENTS_CLIENTS = "Select * from Clients where Archive = 0;";
	private static final String INSERT_CLIENTS = "INSERT INTO Clients (NomClient, PrenomClient, Adresse1, CodePostal, Ville) values(?,?,?,?,?);";
	
	@Override
	public ArrayList<Clients> allClients() throws DALException {

		ArrayList<Clients> resultat = new ArrayList<Clients>();
		Connection cnx = null;
		boolean reponse = false;
		try {
			cnx = JdbcTools.getConnection();			
		}catch(SQLException e1){
			e1.printStackTrace();
		}
		Statement commande = null;
		PreparedStatement commandeParemetree = null;
		CallableStatement appelProcedureStockee = null;
		
		try{
			commande = cnx.createStatement();
			commandeParemetree = cnx.prepareStatement(SQL_GETALLCLIENTS_CLIENTS, Statement.RETURN_GENERATED_KEYS);
		}catch(SQLException sqle){
			System.err.println("Impossible d'�xecuter la requ�te");
			sqle.printStackTrace();
		}
		ResultSet resultatDeLaRequete = null;
		try{
			resultatDeLaRequete = commandeParemetree.executeQuery();
		}catch(SQLException e){
			System.err.println("Impossible d'�xecuter la requ�te");
			e.printStackTrace();
		}
		try {
			while(resultatDeLaRequete.next()){
				Clients pers = new Clients(
						resultatDeLaRequete.getInt("CodeClient"),
						resultatDeLaRequete.getString("NomClient"),
						resultatDeLaRequete.getString("PrenomClient"),
						resultatDeLaRequete.getString("Adresse1"),
						resultatDeLaRequete.getString("Adresse2"),
						resultatDeLaRequete.getString("CodePostal"),
						resultatDeLaRequete.getString("Ville"),
						resultatDeLaRequete.getString("NumTel"),
						resultatDeLaRequete.getString("Assurance"),
						resultatDeLaRequete.getString("Email"),
						resultatDeLaRequete.getString("Remarque"),
						resultatDeLaRequete.getBoolean("Archive"));
				resultat.add(pers);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			if(cnx != null){
				cnx.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return resultat;
	}
	
	
	
	//M�thode d'insert d'un client 
	public void insertClient (Clients cli) throws DALException {
				/*Connection � la base de donn�es*/
				Connection cnx = null;
				
				try { 
					cnx = JdbcTools.getConnection();
				}catch (SQLException e1){
					e1.printStackTrace();
				}
				Statement stmt = null;
				PreparedStatement prestmt = null;
				
				try{
					//Ma requete pr�par�
					stmt = cnx.createStatement();
					/*retourne les cl�s autog�n�r� par le statement*/
					prestmt = cnx.prepareStatement(INSERT_CLIENTS, Statement.RETURN_GENERATED_KEYS);
					prestmt.setString(1, cli.getNomClient());
					prestmt.setString(2, cli.getPrenomClient());
					prestmt.setString(3, cli.getAdresse1());
					prestmt.setString(4, cli.getCodePostal());
					prestmt.setString(5, cli.getVille());
				}catch(SQLException sqle){
					System.err.println("Impossible de pr�parer la requ�te d'insertion d'un client");
					sqle.printStackTrace();
				}
			
				//on execute la requ�te
				try{
					prestmt.executeUpdate();
				}catch(SQLException sqle){
					System.err.println("Impossible d'executer la requ�te d'insertion d'un client");
					sqle.printStackTrace();
				}
				
				//On g�n�re une cl� que l'on met dans un resultset voir ==>Statement.RETURN_GENERATED_KEYS
				//C'est pour le CodeClient qui est en AI
				try{
					ResultSet genKey = prestmt.getGeneratedKeys();
					if(genKey.next()){
						cli.setCodeClient(genKey.getInt(1));
					}
				}catch (SQLException e){
					System.err.println("Impossible de r�cup�rer la cl� autog�n�r�");
					e.printStackTrace();
				}
				
				try{
					if(cnx != null){
						cnx.close();
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
				
					
				
		}
		
		}

