package webServiceREST.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import webServiceREST.models.Annonce;
import webServiceREST.models.Conversation;
import webServiceREST.models.Favori;
import webServiceREST.models.Message;
import webServiceREST.models.Utilisateur;
import webServiceREST.models.UtilisateurPro;
import webServiceREST.repositories.AlerteRepository;
import webServiceREST.repositories.AlerterAnnonceRepository;
import webServiceREST.repositories.AnnonceRepository;
import webServiceREST.repositories.ConsulterAnnonceRepository;
import webServiceREST.repositories.ConversationRepository;
import webServiceREST.repositories.FavoriRepository;
import webServiceREST.repositories.MessageRepository;
import webServiceREST.repositories.UtilisateurProRepository;
import webServiceREST.repositories.UtilisateurRepository;

@RestController
public class ServerController {

	@Autowired
	private AlerterAnnonceRepository alerterAnnonceRepository;
	@Autowired
	private AlerteRepository alerteRepository;
	@Autowired
	private AnnonceRepository annonceRepository;
	@Autowired
	private ConsulterAnnonceRepository consulterAnnonceRepository;
	@Autowired
	private FavoriRepository favoriRepository;
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UtilisateurProRepository utilisateurProRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private ConversationRepository conversationRepository;

	@GetMapping("/dernieresannonces")
	public @ResponseBody String getDernieresAnnonces() {
		System.out.println("\nDemande d'envoi des dernières annonces\n");
		String json = new Gson().toJson(annonceRepository.findAll());
		System.out.println(json);
		return json;
	}

	@GetMapping("/recherche/{motCle}")
	public @ResponseBody ArrayList<Annonce> rechercheRapide(@PathVariable String motCle) {
		System.out.println("\nRecherche rapide : " + motCle);
		ArrayList<Annonce> listeAnnonces = annonceRepository.findAll();
		ArrayList<Annonce> listeResultat = new ArrayList<>();
		for (int i = 0; i < listeAnnonces.size(); i++) {
			if (listeAnnonces.get(i).getTitre().toLowerCase().contains(motCle)
					|| listeAnnonces.get(i).getDescription().toLowerCase().contains(motCle)) {
				listeResultat.add(listeAnnonces.get(i));
			}
		}
		System.out.println("Annonces correspondant (titre ou description contient le mot clé) : " + listeResultat);
		return listeResultat;
	}

	@GetMapping("/rechercher/annonces/{ville}:{typeLogement}:{typeLocation}:{nbPiecesMin}:{surfaceMin}:{nbChambresMin}:{prixMax}")
	public @ResponseBody ArrayList<Annonce> rechercherAnnonce(@PathVariable String ville, @PathVariable String typeLogement,
			@PathVariable String typeLocation, @PathVariable int nbPiecesMin, @PathVariable int surfaceMin,
			@PathVariable int nbChambresMin, @PathVariable int prixMax) {
		System.out.println("\nRecherche d'une annonce!");
		ArrayList<Annonce> listeRetour = new ArrayList<>();
		// Je récupère chaque annonce si elle correspond aux critères demandés dans la
		// requête
		for (Annonce annonce : annonceRepository.findAll()) {
			if ((ville.equalsIgnoreCase(annonce.getVille()))
					&& (typeLogement.equalsIgnoreCase(annonce.getTypeLogement()))
					&& (typeLocation.equalsIgnoreCase(annonce.getTypeLocation()))
					&& (annonce.getNbPieces() >= nbPiecesMin) && (annonce.getSurface() >= surfaceMin)
					&& (annonce.getNbChambres() >= nbChambresMin) && (annonce.getPrix() <= prixMax)) {
				listeRetour.add(annonce);
			}
		}
		System.out.println("Annonces correspondant à la recherche : " + listeRetour);
		return listeRetour;

	}

	@RequestMapping(value = "/ajoutannonce", method = RequestMethod.POST)
	public @ResponseBody String ajoutannonce(@RequestBody Map<String, String> map) {
		System.out.println("\nAjout d'une annonce!");
		System.out.println(map);

		Utilisateur utilisateur = getUtilisateurByEmailAndMdp(map.get("email"), map.get("mdp"));

		Annonce annonce = new Annonce(map.get("titre"), map.get("ville"), map.get("typelog"), map.get("typeloc"),
				Integer.parseInt(map.get("surface")), Integer.parseInt(map.get("prix")),
				Integer.parseInt(map.get("nbpieces")), Integer.parseInt(map.get("nbchambres")),
				Integer.parseInt(map.get("etage")), map.get("ascenseur").equals("true"),
				map.get("caveBox").equals("true"), map.get("balconTerasse").equals("true"),
				map.get("baignoire").equals("true"), map.get("meuble").equals("true"), map.get("description"), false,
				utilisateur);

		annonceRepository.save(annonce);

		System.out.println("Résultat de l'ajout d'une annonce : réussie");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("retour", "1");
		String json = new Gson().toJson(map2);
		return json;
	}

	@RequestMapping(value = "/inscriptionparticulier", method = RequestMethod.POST)
	public @ResponseBody String inscriptionparticulier(@RequestBody Map<String, String> map) {
		System.out.println("\nDemande d'inscription d'un particulier!");
		System.out.println(map);

		Utilisateur ut = new Utilisateur(map.get("nom"), map.get("prenom"), map.get("email"), map.get("mdp"),
				map.get("tel"), map.get("ville"), map.get("code"), 0);
		utilisateurRepository.save(ut);

		System.out.println("Résultat inscription : réussie");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("retour", "1");
		String json = new Gson().toJson(map2);
		return json;
	}

	@RequestMapping(value = "/inscriptionprofessionnel", method = RequestMethod.POST)
	public @ResponseBody boolean inscriptionpro(@RequestBody Map<String, String> map) {
		System.out.println("\nDemande d'inscription d'un professionnel!");
		System.out.println(map);

		UtilisateurPro ut = new UtilisateurPro(map.get("nom"), map.get("prenom"), map.get("email"), map.get("mdp"),
				map.get("tel"), map.get("ville"), map.get("code"), 0, map.get("abonnement") == "1",
				map.get("typeAbonnement"));
		utilisateurRepository.save(ut);

		System.out.println("Résultat inscription : réussie");
		return true;
	}
	
	@RequestMapping(value = "/connexion", method = RequestMethod.POST)
	public @ResponseBody String connexion(@RequestBody Map<String, String> map) {
		System.out.println("\nDemande de connexion!");
		System.out.println(map);

		ArrayList<Utilisateur> utRecu = utilisateurRepository.findByEmail(map.get("email"));
		if (utRecu.size() != 0) {
			for (int i = 0; i < utRecu.size(); i++) {
				if (utRecu.get(i).getMdp().equals(map.get("mdp"))) {
					if (utRecu.get(i) instanceof UtilisateurPro) {
						Map<String, String> map2 = new HashMap<String, String>();
						map2.put("type", "2");
						map2.put("idUtilisateurConnecte", String.valueOf(utRecu.get(i).getIdUtilisateur()));
						String json = new Gson().toJson(map2);
						System.out.println("Résultat connexion : 2 = professionnel et : " + json);
						return json;
					} else {
						Map<String, String> map2 = new HashMap<String, String>();
						map2.put("type", "1");
						map2.put("idUtilisateurConnecte", String.valueOf(utRecu.get(i).getIdUtilisateur()));
						String json = new Gson().toJson(map2);
						System.out.println("Résultat connexion : 1 = particulier et : " + json);
						return json;
					}
				}
			}
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("type", "0");
			map2.put("idUtilisateurConnecte", "");
			String json = new Gson().toJson(map2);
			System.out.println("Résultat connexion : 0 = non trouvé !");
			return json;
		} else {
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("type", "0");
			map2.put("idUtilisateurConnecte", "");
			String json = new Gson().toJson(map2);
			System.out.println("Résultat connexion : 0 = bdd vide et : " + json);
			return json;
		}
	}

	@RequestMapping(value = "/recuperationdonnees", method = RequestMethod.POST)
	public @ResponseBody Utilisateur getUtilisateurByEmailAndMdpRequete(@RequestBody Map<String, String> map) {
		System.out.println("\nDemande de récupération de données");
		System.out.println("Données fournies : " + map);

		ArrayList<Utilisateur> utRecu = utilisateurRepository.findByEmail(map.get("email"));

		for (int i = 0; i <= utRecu.size() - 1; i++) {
			if (utRecu.get(i).getMdp().equals(map.get("mdp"))) {
				System.out.println("Résultat récupération, j'envoie : " + utRecu.get(i));
				return utRecu.get(i);
			}
		}
		return null;
	}

	@RequestMapping(value = "/modificationdonnees", method = RequestMethod.POST)
	public @ResponseBody String modificationdonnees(@RequestBody Map<String, String> map) {
		System.out.println("\nDemande de modification de données");
		System.out.println(map);

		Utilisateur utilisateur = getUtilisateurByEmailAndMdp(map.get("email"), map.get("mdp"));
		utilisateur.setMdp(map.get("mdp"));
		utilisateur.setNom(map.get("nom"));
		utilisateur.setPrenom(map.get("prenom"));
		utilisateur.setVille(map.get("ville"));
		utilisateur.setCodePostal(map.get("code"));
		utilisateur.setTelephone(map.get("tel"));
		utilisateurRepository.flush();

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("retour", "1");
		String json = new Gson().toJson(map2);
		return json;

	}
	
	@RequestMapping(value = "/modificationannonce", method = RequestMethod.POST)
	public @ResponseBody String modificationannonce(@RequestBody Map<String, String> map) {
		System.out.println("\nDemande de modification d'une annonce");
		System.out.println(map);
		
	
		Annonce annonce = annonceRepository.findAnnonceById(Integer.parseInt(map.get("id")));
		System.out.println("Id de l'annonce : " + annonce.getIdAnnonce());
		annonce.setTitre(map.get("titre"));
		annonce.setVille(map.get("lieu"));
		annonce.setTypeLogement(map.get("typelog"));
		annonce.setTypeLocation(map.get("typeloc"));
		annonce.setNbPieces(Integer.parseInt(map.get("nbpieces")));
		annonce.setSurface(Integer.parseInt(map.get("surface")));
		annonce.setNbChambres(Integer.parseInt(map.get("nbchambres")));
		annonce.setPrix(Integer.parseInt(map.get("prix")));
		annonce.setNumeroEtage(Integer.parseInt(map.get("etage")));
		annonce.setDescription(map.get("description"));
		annonce.setAscenseur(Boolean.valueOf(map.get("ascenseur")).booleanValue());
		annonce.setCaveBox(Boolean.valueOf(map.get("caveBox")).booleanValue());
		annonce.setBalconTerasse(Boolean.valueOf(map.get("balconTerasse")).booleanValue());
		annonce.setBaignoire(Boolean.valueOf(map.get("baignoire")).booleanValue());
		annonce.setMeuble(Boolean.valueOf(map.get("meuble")).booleanValue());
		
		
		annonceRepository.flush();

		//A quoi servent ces lignes ?
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("retour", "1");
		String json = new Gson().toJson(map2);
		return json;

	}

	@GetMapping("/nbfavoris/{idAnnonce}")
	public @ResponseBody int getNbFavoris(@RequestBody int idAnnonce) {
		System.out.println("\nDemande d'envoi du nombre de favoris pour une annnonce en particulier");

		int nb = favoriRepository.findNbFavoris(idAnnonce);
		System.out.println("Nombre de favoris pour l'annonce" + idAnnonce + " :" + nb);

		return nb;
	}

	@GetMapping("/favoris/{idUtilisateur}")
	public @ResponseBody ArrayList<Annonce> getFavorisByUtilisateurId(@PathVariable int idUtilisateur) {
		System.out.println("\nDemande d'envoi des annonces mises en favoris par un certain utilisateur");

		ArrayList<Annonce> listeFavoris = favoriRepository.FindFavorisByUtilisateurId(idUtilisateur);
		System.out.println("Annonces mises en favoris par l'utilisateur " + idUtilisateur + " :" + listeFavoris);

		return listeFavoris;
	}

	@RequestMapping(value = "/ajouterfavoris", method = RequestMethod.POST)
	public @ResponseBody boolean ajoutFavoris(@RequestBody Map<String, String> map) {
		System.out.println("\nAjout d'un favori");

		Utilisateur utilisateur = utilisateurRepository.getById(Integer.parseInt(map.get("idUtilisateur")));
		Annonce annonce = annonceRepository.getById(Integer.parseInt(map.get("idAnnonce")));

		Favori favori = new Favori(annonce, utilisateur);

		// On vérifie que cet utilisateur n'a pas déjà mis cette annonce en favoris
		Favori existeDeja = favoriRepository.findFavorisByTwoId(Integer.parseInt(map.get("idUtilisateur")),
				Integer.parseInt(map.get("idAnnonce")));
		if (existeDeja == null) {
			favoriRepository.save(favori);
			System.out.println("Résultat de l'ajout d'un favori : réussi");
			return true;
		} else {
			System.out.println("Résultat de l'ajout d'un favori : échec, existe déjà");
			return true;
		}
	}

	@RequestMapping(value = "/supprimerannonce", method = RequestMethod.POST)
	public @ResponseBody boolean SuppAnnonce(@RequestBody Map<String, String> map) {
		System.out.println("\nDemande de suppression d'une annonce d'un certain utilisateur");

		// Je n'ai pas réussi à gérer les CASCADE

		/*
		 * ArrayList<Favori> listeFavoris =
		 * favoriRepository.FindFavorisByAnnonceId(Integer.parseInt(map.get("idAnnonce")
		 * )); for (int i = 0; i < listeFavoris.size(); i++) {
		 * System.out.println("Suppression du favoris :" +
		 * listeFavoris.get(i).getIdFavori());
		 * favoriRepository.delete(listeFavoris.get(i)); }
		 * 
		 * ArrayList<Conversation> listeConversations =
		 * conversationRepository.FindConversationByAnnonceId(Integer.parseInt(map.get(
		 * "idAnnonce"))); for (int i = 0; i < listeConversations.size(); i++) { for
		 * (int j = 0; j < listeConversations.get(i).getListeMessages().size(); j++) {
		 * //listeConversations.get(i).getListeMessages().remove(listeConversations.get(
		 * i).getListeMessages().get(i)); //conversationRepository.flush();
		 * messageRepository.delete(listeConversations.get(i).getListeMessages().get(i))
		 * ; System.out.println("Suppression du message :" +
		 * listeConversations.get(i).getListeMessages().get(i).getContenuMessage()); }
		 * conversationRepository.delete(listeConversations.get(i));
		 * System.out.println("Suppression de la conversation :" +
		 * listeConversations.get(i).getIdConversation()); }
		 * 
		 * Annonce retrouverAnnonce =
		 * annonceRepository.findAnnonceByTwoId(Integer.parseInt(map.get("idUtilisateur"
		 * )), Integer.parseInt(map.get("idAnnonce")));
		 * annonceRepository.delete(retrouverAnnonce);
		 * 
		 * System.out.println("Annonce supprimée :" + retrouverAnnonce +
		 * ". Demandé par l'utilisateur :" +
		 * Integer.parseInt(map.get("idUtilisateur")));
		 */

		return true;
	}

	@RequestMapping(value = "/supprimerfavori", method = RequestMethod.POST)
	public @ResponseBody boolean SuppFavori(@RequestBody Map<String, String> map) {
		System.out.println("\nDemande de suppression d'une annonce des favoris d'un certain utilisateur");

		Favori retrouverFavori = favoriRepository.findFavorisByTwoId(Integer.parseInt(map.get("idUtilisateur")),
				Integer.parseInt(map.get("idAnnonce")));
		favoriRepository.delete(retrouverFavori);

		System.out.println("Favori supprimé : " + retrouverFavori + ". Demandé par l'utilisateur :"
				+ Integer.parseInt(map.get("idAnnonce")));

		return true;
	}

	@GetMapping("/annonces/{idUtilisateur}")
	public @ResponseBody ArrayList<Annonce> getAnnoncesByUtilisateurId(@PathVariable int idUtilisateur) {
		System.out.println("\nDemande d'envoi des annonces mises en ligne par un certain utilisateur");

		ArrayList<Annonce> listeAnnonces = annonceRepository.FindAnnoncesByUtilisateurId(idUtilisateur);
		System.out.println("Annonces mises en ligne par l'utilisateur " + idUtilisateur + " :" + listeAnnonces);

		return listeAnnonces;
	}

	@GetMapping("/conversation/{idUtilisateur1}/{idUtilisateur2}/{idAnnonce}")
	public @ResponseBody String getConversationBetween(@PathVariable int idUtilisateur1,
			@PathVariable int idUtilisateur2, @PathVariable int idAnnonce) {
		System.out.println("\nDemande d'envoi des messages d'une conversation entre " + idUtilisateur1 + " et "
				+ idUtilisateur2 + " pour l'annonce " + idAnnonce);

		ArrayList<Message> conversation = messageRepository.findMessageByThreeIds(idUtilisateur1, idUtilisateur2,
				idAnnonce);
		String json = new Gson().toJson(conversation);
		System.out.println("J'envoie : " + json);
		return json;
	}

	@RequestMapping(value = "/envoimessage", method = RequestMethod.POST)
	public @ResponseBody boolean envoiMessage(@RequestBody Map<String, String> map) {
		System.out.println("\nUn message a été envoyé, maintenant je l'enregistre en bdd");
		System.out.println(map);

		Utilisateur ut1 = utilisateurRepository.findUtilisateurById(Integer.parseInt(map.get("envoyeur")));
		Utilisateur ut2 = utilisateurRepository.findUtilisateurById(Integer.parseInt(map.get("receveur")));
		Annonce annonce = annonceRepository.findAnnonceById(Integer.parseInt(map.get("annonce")));
		Conversation conversation = conversationRepository.findConversationByTwoIdAndAnnonce(
				Integer.parseInt(map.get("envoyeur")), Integer.parseInt(map.get("receveur")),
				Integer.parseInt(map.get("annonce")));
		System.out.println(conversation.toString());
		Message messageNouveau = new Message(ut1, ut2, annonce, map.get("message"), false);
		messageRepository.save(messageNouveau);
		conversation.getListeMessages().add(messageNouveau);
		conversationRepository.flush();

		return true;

	}

	@RequestMapping(value = "/envoimessagenouvelleconversation", method = RequestMethod.POST)
	public @ResponseBody boolean envoimessagenouvelleconversation(@RequestBody Map<String, String> map) {
		System.out.println(
				"\nUn message a été envoyé, c'est une nouvelle conversation, maintenant je l'enregistre en bdd");
		System.out.println(map);

		Utilisateur ut1 = utilisateurRepository.findUtilisateurById(Integer.parseInt(map.get("envoyeur")));
		Utilisateur ut2 = utilisateurRepository.findUtilisateurById(Integer.parseInt(map.get("receveur")));
		Annonce annonce = annonceRepository.findAnnonceById(Integer.parseInt(map.get("annonce")));
		System.out.println(ut1);
		System.out.println(ut1);
		System.out.println(annonce);

		Conversation conversation = conversationRepository.findConversationByTwoIdAndAnnonce(
				Integer.parseInt(map.get("envoyeur")), Integer.parseInt(map.get("receveur")),
				Integer.parseInt(map.get("annonce")));
		if (conversation == null) {
			Message messageNouveau = new Message(ut1, ut2, annonce, map.get("message"), false);
			messageRepository.save(messageNouveau);
			ArrayList<Message> listM = new ArrayList<>();
			listM.add(messageNouveau);
			Conversation conversation2 = new Conversation(ut1, ut2, annonce, listM);
			conversationRepository.save(conversation2);
		} else {
			Message messageNouveau = new Message(ut1, ut2, annonce, map.get("message"), false);
			messageRepository.save(messageNouveau);
			conversation.getListeMessages().add(messageNouveau);
			conversationRepository.flush();
		}

		return true;

	}

	@GetMapping("/conversationall/{idUtilisateur}")
	public @ResponseBody ArrayList<Conversation> getConversationUtilisateur(@PathVariable int idUtilisateur) {
		System.out.println("\nDemande d'envoi des conversations de l'utilisateur : " + idUtilisateur);
		ArrayList<Conversation> conversation = conversationRepository.FindConversationByUtilisateurId(idUtilisateur);
		System.out.println("Résultat : " + conversation);
		return conversation;
	}

	
	
	
	
	public Utilisateur getUtilisateurByEmailAndMdp(String email, String mdp) {
		ArrayList<Utilisateur> utRecu = utilisateurRepository.findByEmail(email);
		if (utRecu.size() != 0) {
			for (int i = 0; i < utRecu.size(); i++) {
				if (utRecu.get(i).getMdp().equals(mdp)) {
					return utRecu.get(i);
				}
			}
		}
		return null;
	}

}
