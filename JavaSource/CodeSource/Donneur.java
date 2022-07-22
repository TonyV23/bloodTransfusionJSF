package CodeSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import DataBaseConnect.dbConnection;

public class Donneur {
	private int id;
	
	private String nom_donneur;
	private String prenom_donneur;
	private String sexe_donneur;
	private String groupe_sanguin_donneur;
	
	private String info;

	// le constructeur de la classe donneur
	public Donneur(int id, String nom_donneur, String prenom_donneur,
			String sexe_donneur, String groupe_sanguin_donneur) {
		this.id = id;
		this.nom_donneur = nom_donneur;
		this.prenom_donneur = prenom_donneur;
		this.sexe_donneur = sexe_donneur;
		this.groupe_sanguin_donneur = groupe_sanguin_donneur;
	}
	
	// le constructeur de la classe donneur sans id
	public Donneur(String nom_donneur, String prenom_donneur,
			String sexe_donneur, String groupe_sanguin_donneur) {
		this.nom_donneur = nom_donneur;
		this.prenom_donneur = prenom_donneur;
		this.sexe_donneur = sexe_donneur;
		this.groupe_sanguin_donneur = groupe_sanguin_donneur;
	}
	
	// le constructeur vide par default
	public Donneur (){}
	
	private List <Donneur> liste_des_donneurs_dans_la_base_de_donnees;

	//les setters et les getters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom_donneur() {
		return nom_donneur;
	}

	public void setNom_donneur(String nom_donneur) {
		this.nom_donneur = nom_donneur;
	}

	public String getPrenom_donneur() {
		return prenom_donneur;
	}

	public void setPrenom_donneur(String prenom_donneur) {
		this.prenom_donneur = prenom_donneur;
	}

	public String getSexe_donneur() {
		return sexe_donneur;
	}

	public void setSexe_donneur(String sexe_donneur) {
		this.sexe_donneur = sexe_donneur;
	}

	public String getGroupe_sanguin_donneur() {
		return groupe_sanguin_donneur;
	}

	public void setGroupe_sanguin_donneur(String groupe_sanguin_donneur) {
		this.groupe_sanguin_donneur = groupe_sanguin_donneur;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setListe_des_donneurs_dans_la_base_de_donnees(
			List<Donneur> liste_des_donneurs_dans_la_base_de_donnees) {
		this.liste_des_donneurs_dans_la_base_de_donnees = liste_des_donneurs_dans_la_base_de_donnees;
	}
	
	// la methode qui permet d'afficher la liste de tous les donneurs depuis la base de donnée
	public List<Donneur> getListe_des_donneurs_dans_la_base_de_donnees() {
		ResultSet result = dbConnection.selectFromDataBase("select * from donneur;");
		if(liste_des_donneurs_dans_la_base_de_donnees == null)
			liste_des_donneurs_dans_la_base_de_donnees = new ArrayList<Donneur>();
		else
			liste_des_donneurs_dans_la_base_de_donnees.clear();
		if (result != null){
			try{
				while(result.next()){
					liste_des_donneurs_dans_la_base_de_donnees.add(
						new Donneur(
							result.getInt("id"),
							result.getString("nom_donneur"),
							result.getString("prenom_donneur"),
							result.getString("sexe_donneur"),
							result.getString("groupe_sanguin_donneur")
					));
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return liste_des_donneurs_dans_la_base_de_donnees;
	}
	
	// la methode qui enregistre un nouveau donneur dans la base de donnée
	public void EnregistrerDonneur(){
		int info = dbConnection.updateDataBase(
			"insert into donneur(nom_donneur,prenom_donneur,sexe_donneur,groupe_sanguin_donneur)" +
			"values('"+this.nom_donneur+"','"+this.prenom_donneur+"','"+this.sexe_donneur+"','"+this.groupe_sanguin_donneur+"')");
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
	
	public void SupprimerDonneur(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String id_in_string = getIdParametter(facesContext);
		this.id = Integer.parseInt(id_in_string);
		
		int info = dbConnection.updateDataBase("delete from donneur where id="+id+";");
		if (info > 0)
			this.setInfo("Donneur supprimé avec succes");
		else
			this.setInfo("Erreur de suppression !!");
	}
	
	private boolean modify = false;
	private Donneur select_donneur = null;
	
	// les setters et les getters
	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		this.modify = modify;
	}

	public Donneur getSelect_donneur() {
		return select_donneur;
	}

	public void setSelect_donneur(Donneur select_donneur) {
		this.select_donneur = select_donneur;
	}

	// la methode qui modifie les donneurs du donneur
	public void ModifierDonneur(){
		if (this.id <= 0){ this.info = "Rien à modifier !"; return ;}
		if (this.nom_donneur == null || this.nom_donneur.trim().equalsIgnoreCase("")){this.info = "Tapez le nom du donneur !!"; return ;}
		if (this.prenom_donneur == null || this.prenom_donneur.trim().equalsIgnoreCase("")){this.info = "Tapez le prenom du donneur !!"; return ;}
		if (this.sexe_donneur == null || this.sexe_donneur.trim().equalsIgnoreCase("")){this.info = "Tapez le genre du donneur !!"; return ;}
		if (this.groupe_sanguin_donneur == null || this.groupe_sanguin_donneur.trim().equalsIgnoreCase("")){this.info = "Tapez le groupe sanguin du donneur !!"; return ;}
		
		if(dbConnection.updateDataBase("update donneur set nom_donneur='"+this.nom_donneur.trim()+"'," +
				"prenom_donneur='"+this.prenom_donneur.trim()+"',sexe_donneur='"+
				this.sexe_donneur.trim()+"',groupe_sanguin_donneur='"+this.groupe_sanguin_donneur.trim()+"' where id="+this.id)>0){
			this.modify = true;
			this.setInfo("Modification Reussie avec Succes");
		}else 
			this.setInfo("Erreur de modification !!");
	}
}
