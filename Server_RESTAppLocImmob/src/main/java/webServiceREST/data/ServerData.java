package webServiceREST.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import webServiceREST.models.Alerte;
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

@Configuration
public class ServerData {
	
	@Bean
	public CommandLineRunner initDatabase(
			AlerterAnnonceRepository alerterAnnonceRepository,
			AlerteRepository alerteRepository,
			AnnonceRepository annonceRepository,
			ConsulterAnnonceRepository consulterAnnonceRepository,
			FavoriRepository favoriRepository,
			MessageRepository messageRepository,
			UtilisateurProRepository utilisateurProRepository,
			UtilisateurRepository utilisateurRepository,
			ConversationRepository conversationRepository
			)
	{
		return args -> {
			
			//alerte nulle pour ceux qui en ont mis aucune
			Alerte alerteNulle = new Alerte(0, null, 0, null, null);
			alerteRepository.save(alerteNulle);
			
			//des utilisateurs pro
			UtilisateurPro utilisateurPro1 = new UtilisateurPro("Garcia", "Léa", "lea.garcia06@etu.umontpellier.fr", "jxdn", "0677199486", "Bizanet", "11200", 0, false, "");
			UtilisateurPro utilisateurPro2 = new UtilisateurPro("Desbos", "Marie-Lou", "marie-lou.desbos@etu.umontpellier.fr", "taemin", "0677199487", "Fabrègues", "34690", 0, false, "");
			utilisateurProRepository.save(utilisateurPro1);
			utilisateurProRepository.save(utilisateurPro2);
			
			//des utilisateurs particuliers
			Utilisateur utilisateur1 = new Utilisateur("Romero", "Gaétan", "gaetan.romero@etu.umontpellier.fr", "pignancapue", "0677199482", "Pignan", "34570", 0);
			Utilisateur utilisateur2 = new Utilisateur("Sanchez", "Martin", "martin", "virginie", "0677199480", "Tuchan", "11350", 0);
			Utilisateur utilisateur3 = new Utilisateur("test", "test", "test", "test", "test", "test", "test", 0);
			utilisateurRepository.save(utilisateur1);
			utilisateurRepository.save(utilisateur2);
			utilisateurRepository.save(utilisateur3);
			
		
			Annonce annonce1 = new Annonce("Villa des romarins", "Hyères", "Maison", "Saisonnière", 300, 1000, 15, 4, 0, true, true, false, true, true, "Belle villa pas loin de la mer Méditerranée", false, utilisateurPro1);;
			Annonce annonce2 = new Annonce("Villa des oliviers", "Saint-Tropez", "Maison", "Saisonnière", 200, 1500, 10, 3, 0, true, false, true, false, true, "Magnifique villa pas loin de la mer Méditerranée", false, utilisateurPro1);;
			Annonce annonce3 = new Annonce("Villa des cyprès", "Nice", "Maison", "Saisonnière", 200, 1200, 15, 3, 0, true, false, true, true, true, "Jolie villa pas loin de la mer Méditerranée", false, utilisateur3);
			annonceRepository.save(annonce1);
			annonceRepository.save(annonce2);
			annonceRepository.save(annonce3);
			
			Favori metenfavori1 = new Favori(annonce1, utilisateur1);
			Favori metenfavori2 = new Favori(annonce2, utilisateur1);
			Favori metenfavori4 = new Favori(annonce1, utilisateur2);
			Favori metenfavori5 = new Favori(annonce2, utilisateur2);
			Favori metenfavori6 = new Favori(annonce1, utilisateur3);
			Favori metenfavori7 = new Favori(annonce2, utilisateur3);
			Favori metenfavori8 = new Favori(annonce3, utilisateur3);
			favoriRepository.save(metenfavori1);
			favoriRepository.save(metenfavori2);
			favoriRepository.save(metenfavori4);
			favoriRepository.save(metenfavori5);
			favoriRepository.save(metenfavori6);
			favoriRepository.save(metenfavori7);
			favoriRepository.save(metenfavori8);
			
			/*
			Message message1 = new Message(utilisateur2, utilisateurPro1, annonce1, "coucou de martin vers lea", true);
			Message message2 = new Message(utilisateurPro1, utilisateur2, annonce1, "coucou de lea vers martin", true);
			messageRepository.save(message1);
			messageRepository.save(message2);
			List<Message> listeConversation = new ArrayList<>();
			listeConversation.add(message1);
			listeConversation.add(message2);
			Conversation conversation = new Conversation(utilisateurPro1, utilisateur2, annonce1, listeConversation);
			conversationRepository.save(conversation);
			
			Message message3 = new Message(utilisateurPro2, utilisateur2, annonce1, "coucou de marie-lou vers martin", true);
			Message message4 = new Message(utilisateur2, utilisateurPro2, annonce1, "coucou de martin vers marie-lou", false);
			messageRepository.save(message3);
			messageRepository.save(message4);
			List<Message> listeConversation2 = new ArrayList<>();
			listeConversation2.add(message3);
			listeConversation2.add(message4);
			Conversation conversation2 = new Conversation(utilisateurPro2, utilisateur2, annonce1, listeConversation2);
			conversationRepository.save(conversation2);*/
			
			
		
		};
	}

}
