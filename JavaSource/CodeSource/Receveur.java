package CodeSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import DataBaseConnect.dbConnection;

public class Receveur {
	private int id;
	
	private String nom_receveur;
	private String prenom_receveur;
	private String sexe_receveur;
	private String groupe_sanguin_receveur;
	
	private String info;

	// le constructeur de la classe 
	public Receveur(int id, String nom_receveur, String prenom_receveur,
			String sexe_receveur, String groupe_sanguin_receveur) {
		this.id = id;
		this.nom_receveur = nom_receveur;
		this.prenom_receveur = prenom_receveur;
		this.sexe_receveur = sexe_receveur;
		this.groupe_sanguin_receveur = groupe_sanguin_receveur;
	}
	
	// le constructeur de la classe sans id
	public Receveur(String nom_receveur, String prenom_receveur,
			String sexe_receveur, String groupe_sanguin_receveur) {
		this.nom_receveur = nom_receveur;
		this.prenom_receveur = prenom_receveur;
		this.sexe_receveur = sexe_receveur;
		this.groupe_sanguin_receveur = groupe_sanguin_receveur;
	}
	
	// le constructeur de la classe par defaut vide
	public Receveur (){}
	
	
	public List <Receveur> liste_des_receveurs_de_la_base_de_donnees;

	// les settes et les getters 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom_receveur() {
		return nom_receveur;
	}

	public void setNom_receveur(String nom_receveur) {
		this.nom_receveur = nom_receveur;
	}

	public String getPrenom_receveur() {
		return prenom_receveur;
	}

	public void setPrenom_receveur(String prenom_receveur) {
		this.prenom_receveur = prenom_receveur;
	}

	public String getSexe_receveur() {
		return sexe_receveur;
	}

	public void setSexe_receveur(String sexe_receveur) {
		this.sexe_receveur = sexe_receveur;
	}

	public String getGroupe_sanguin_receveur() {
		return groupe_sanguin_receveur;
	}

	public void setGroupe_sanguin_receveur(String groupe_sanguin_receveur) {
		this.groupe_sanguin_receveur = groupe_sanguin_receveur;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public void setListe_des_receveurs_de_la_base_de_donnees(
			List<Receveur> liste_des_receveurs_de_la_base_de_donnees) {
		this.liste_des_receveurs_de_la_base_de_donnees = liste_des_receveurs_de_la_base_de_donnees;
	}
	
	
	// la methode qui affiche tous les receveurs depuis la base de donnees 
	public List<Receveur> getListe_des_receveurs_de_la_base_de_donnees() {
		ResultSet result = dbConnection.selectFromDataBase("select * from receveur;");
		if (liste_des_receveurs_de_la_base_de_donnees == null)
			liste_des_receveurs_de_la_base_de_donnees = new ArrayList<Receveur>();
		else
			liste_des_receveurs_de_la_base_de_donnees.clear();
		if (result != null){
			try{
				while(result.next()){
					liste_des_receveurs_de_la_base_de_donnees.add(
							new Receveur(
								result.getInt("id"),
								result.getString("nom_receveur"),
								result.getString("prenom_receveur"),
								result.getString("sexe_receveur"),
								result.getString("groupe_sanguin_receveeur")
							)
					);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return liste_des_receveurs_de_la_base_de_donnees;
	}

	// la methode qui enregistre un nouveau donneur dans la base de donnée
	public void EnregistrerReceveur(){
		int info = dbConnection.updateDataBase(
			"insert into receveur(nom_receveur,prenom_receveur,sexe_receveur,groupe_sanguin_receveur)" +
			"values('"+this.nom_receveur+"','"+this.prenom_receveur+"','"+this.sexe_receveur+"','"+this.groupe_sanguin_receveur+"')");
		if (info > 0)
			this.setInfo("Enregistrement Reussi");
		else
			this.setInfo("Erreur d'enregistrement !!");		
	}
	
	// la methode qui efface un donneur dans la base de données
	public String getIdParametter(FacesContext fc){
		Map <String, String> idParametter = fc.getExternalContext().getRequestParameterMap();
		
		return idParametter.get("id");
	}
	
	public void SupprimerReceveur(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String id_in_string = getIdParametter(facesContext);
		this.id = Integer.parseInt(id_in_string);
		
		int info = dbConnection.updateDataBase("delete from receveur where id="+id+";");
		if (info > 0)
			this.setInfo("Donneur supprimé avec succes");
		else
			this.setInfo("Erreur de suppression !!");
	}
	
	private boolean modify = false;
	private Receveur select_receveur = null;
	
	// les setters et les getters
	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		this.modify = modify;
	}

	public Receveur getSelect_receveur() {
		return select_receveur;
	}

	public void setSelect_receveur(Receveur select_receveur) {
		this.select_receveur = select_receveur;
	}

	// la methode qui modifie les donneurs du donneur
	public void ModifierReceveur(){
		if (this.id <= 0){ this.info = "Rien à modifier !"; return ;}
		if (this.nom_receveur == null || this.nom_receveur.trim().equalsIgnoreCase("")){this.info = "Tapez le nom du receveur !!"; return ;}
		if (this.prenom_receveur == null || this.prenom_receveur.trim().equalsIgnoreCase("")){this.info = "Tapez le prenom du receveur !!"; return ;}
		if (this.sexe_receveur == null || this.sexe_receveur.trim().equalsIgnoreCase("")){this.info = "Tapez le genre du receveur !!"; return ;}
		if (this.groupe_sanguin_receveur == null || this.groupe_sanguin_receveur.trim().equalsIgnoreCase("")){this.info = "Tapez le groupe sanguin du receveur !!"; return ;}
		
		if(dbConnection.updateDataBase("update receveur set nom_receveur='"+this.nom_receveur.trim()+"'," +
				"prenom_receveur='"+this.prenom_receveur.trim()+"',sexe_receveur='"+
				this.sexe_receveur.trim()+"',groupe_sanguin_receveur='"+this.groupe_sanguin_receveur.trim()+"' where id="+this.id)>0){
			this.modify = true;
			this.setInfo("Modification Reussie avec Succes");
		}else 
			this.setInfo("Erreur de modification !!");
	}
	

}
