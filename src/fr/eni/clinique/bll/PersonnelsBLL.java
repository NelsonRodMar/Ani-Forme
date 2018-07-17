package fr.eni.clinique.bll;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Personnels;
import fr.eni.clinique.dal.DALException;
import fr.eni.clinique.dal.DAOFactory;
import fr.eni.clinique.dal.PersonnelsDAO;

public class PersonnelsBLL {
	// Cr�ation d'une variable d'instance
	// pour le Singleton
	private static PersonnelsBLL INSTANCE = null;

	public PersonnelsBLL() throws BLLException {
		// Obtention du DAO Personnels

	}

	// Singleton
	public static synchronized PersonnelsBLL getInstance() throws BLLException {
		if (INSTANCE == null) {
			INSTANCE = new PersonnelsBLL();
		}
		return INSTANCE;
	}

	public boolean validerConnection(Personnels p) throws BLLException, DALException {
		boolean valide = false;
		StringBuffer sb = new StringBuffer();

		if (null == p) {
			throw new BLLException("Personnel null");
		}
		// Les attributs du personnels sont obligatoires
		if (null == p.getMdp() || p.getMdp().trim().length() == 0) {
			sb.append("Le mdp  est obligatoire.\n");
			return valide;
		}
		if (p.getNom() == null || p.getNom().trim().length() == 0) {
			sb.append("Le nom  est obligatoire.\n");
			return valide;
		}

		PersonnelsDAO personnelsDAO = DAOFactory.getPersonnelsDAO();

		valide = personnelsDAO.connection(p.getNom(), p.getMdp());

		return valide;

	}

	public boolean validerPersonnel(Personnels p) throws BLLException, DALException {
		boolean valide = false;
		StringBuffer sb = new StringBuffer();

		if (p == null) {

		}
		// if (p.getMdp() == null || )
		return valide;
	}
}
