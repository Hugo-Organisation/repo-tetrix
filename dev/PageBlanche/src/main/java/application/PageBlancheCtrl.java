package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import neige.Flocon;
import neige.Neige;
import neige.SensDuVent;

public class PageBlancheCtrl implements javafx.fxml.Initializable  {
	/*=============================================
	 * Section de code générée par SceneBuilder.
	 * Si la scéne décrite est enrichie, reporter ici les
	 * nouveaux attributs qui apparaîtraient dans le code
	 * généré par SceneBuilder.
	 */
	/**
	 * container root de la scène dans lequel il faut placer
	 * l'arborescence de tous les autres widgets.
	 */
	@FXML
	private HBox hboxRoot;


	/*=============================================
	 * à partir d'ici les attributs ajoutés à main.
	 */
	
	/**
	 * Canvas dans laquelle on va dessiner toute la page.
	 */
	private Canvas canvas;

	/**
	 * Contexte graphique placé dans le canvas.
	 * Il va servire à dessiner l'image de la scene.
	 */
	private GraphicsContext  gc;

	/**
	 * Référence sur la classe du modèle.
	 */
	private Neige neige;

	/*=============================================
	 * ci dessous la méthode initialize qui est automatiquement appelée à la fin
	 * du chargement de la vue de SceneBuiler dans la classe d'application (Main).
	 * Tous les widgets décrits dans la vue (pour cet exemple, il n'y a que
	 * le HBox hboxRoot) sont construits et utilisables.
	 * Il faut placer dans cette méthode tout ce qui doit être fait pour que
	 * le contrôleur fonctionne:
	 *  -> dessin de ce qui doit être vu
	 *  -> mise en place des callbacks pour les events gérés par le contrôleur.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// initialisation de la référence sur la classe du modèle.
		neige = new Neige();
	
		// mise en place du dessin de l'interface
		initCanvas();
		
		// mise en place des callbacks
		initMousse();  // gestion de la souris
		initClavier(); // gestion du clavier
		initAnimation(); // gestion des animations automatiques régulières. 
		

	}

	/**
	 * mise en place du canvas pour dessiner l'image.
	 */
	private void initCanvas() {
		canvas = new Canvas(ConfigVue.LARGEUR, ConfigVue.HAUTEUR);
		// pour que les events claviers soient visibles par le canvas
		canvas.setFocusTraversable(true);
		gc = canvas.getGraphicsContext2D();
		hboxRoot.getChildren().add(canvas);
		dessinerNeige();
	}

	/**
	 * mise en place du callback pour gérer la souris.
	 */
	public void initMousse() {
		// Création de EventHandler par une classe anonyme.
		EventHandler<MouseEvent> mouseHandler = new EventHandler<>() {
			@Override
			public void handle(MouseEvent event) {
				onClic(event); // méthode écrite plus loins de le code.

			}
		};

		// l'équivalent des lignes ci dessus avec une expression lambda (1 seule ligne de code):
		// EventHandler<MouseEvent> mouseHandler = (event) -> onClic(event); 

		// mise en place du callback pour les events de clic de la souris:
		canvas.setOnMouseClicked(mouseHandler);
	}
	
	/**
	 * mise en place du callback pour gérer le clavier.
	 */
	public void initClavier() {
		// Création de EventHandler par une classe anonyme.
		EventHandler<KeyEvent> keyboardHandler = new EventHandler<>() {

			@Override
			public void handle(KeyEvent event) {
				onKey(event);
				
			}
			
		};

		// l'équivalent des lignes ci dessus avec une expression lambda (une seule ligne de code)
		// EventHandler<KeyEvent> keyboaedHandler = (event) -> onClic(event); 

		// mise en place du callback pour les events les touches du clavier
		canvas.setOnKeyPressed(keyboardHandler);
	}

	/**
	 * mise en place d'un AnimationTimer pour provoquer des évolutions
	 * régulières (la méthode handle est appellée 60 fois par secondes
	 * si la charge le permet).
	 */
	private void initAnimation() {
		// usage d'une classe anonyme pour la création de l'AnimationTimer
		new AnimationTimer() {
			private long lastTimer;
			// le paramètre de handle est en nanoseconde.
			// il y a un millard de nanoseconde dans une seconde.
			private static final long MILLIARD = 1000000000L;
			
			// nombres de TIC par seconde pour la descente des flocons
			private static final int NBTICS = 20; 

			// méthode qui va être appelée à chaque tic (60 fois par seconde
			// si la charge de la machine le permet).
			// le paramètre "now" est un timestamp exprimé en nanoseconde.
			@Override
			public void handle(long now) {
				long interval = now - lastTimer;
				if (interval >= MILLIARD/NBTICS) { // une action tous les 20ieme (cf valeur de NBTICS) de secondes
					                              // donc 20 actions par seconde si la charge le permet.
					neige.tomberFlocons();
					dessinerNeige();
					lastTimer = now;
				}

			}   

		}.start();
	}

	/**
	 * redessine toute la scene.
	 */
	public void dessinerNeige() {
		
		// 1: dessiner l'image de fond.

		// réccupération de l'URL du fichier image et transformation en chaine de caractères 
		// (premièr argument attentu par le constructeur de la classe Image 
		String urlFichierImageFond = getClass().getResource(ConfigVue.REPERTOIREIMAGE+"/"+ConfigVue.IMAGEFOND).toString();
		// image retaillée aux dimenssions du canvas
		Image imageFond = new Image(urlFichierImageFond, ConfigVue.LARGEUR, ConfigVue.HAUTEUR, false, false);
		gc.drawImage(imageFond, 0, 0);	

		
		// 2: dessiner tous les flocons.
		List<Flocon> flocons = neige.getFlocons();

		String urlFichierImageFlocon = getClass().getResource(ConfigVue.REPERTOIREIMAGE+"/"+ConfigVue.IMAGEFLOCON).toString();

		for(Flocon flocon: flocons) {
			int taille = flocon.getTaille();
			int posX = flocon.getPosition().getCoordX();
			int posY = flocon.getPosition().getCoordY();
			Image imageFlocon = new Image(urlFichierImageFlocon, taille, taille, false, false);
			gc.drawImage(imageFlocon, posX, posY);
		}
	}





	/**
	 * Callback appelé lors d'un clic de souris;
	 * @param event
	 *           event provoqué par le clic de souris.
	 */
	public void onClic(MouseEvent event) {
		// ajout d'un nouveau flocon sur le paysage là où
		// le clic a eu lieu.
		neige.add((int)event.getX(), (int)event.getY());
		dessinerNeige();
	}
	
	/**
	 * Callback appelé lorsqu'une touche du clavier est appuyée.
	 * @param event
	 *          event provoqué par une touche du clavier.
	 */
	public void onKey(KeyEvent event) {
		KeyCode code = event.getCode();
		
		// on accèlère le vent dans le sens de la touche appuyée
		// sur le clavier/
		switch(code) {
	    case LEFT:  
	    	neige.changerVitesseDuVent(SensDuVent.VERS_LA_GAUCHE);
	    	break;
	    case RIGHT:
	    	neige.changerVitesseDuVent(SensDuVent.VERS_LA_DROITE);
	    	break;
	    default:
	    }
	}

}