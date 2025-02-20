package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Animation;
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

public class PageBlancheCtrl2 implements javafx.fxml.Initializable  {
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
	 * Contexte graphique fourni par le canvas.
	 * Il va servir à dessiner l'image de la scène.
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
		canvas.setOnMouseClicked((event) -> onClic(event)); // la souris
		canvas.setOnKeyPressed((event) -> onKey(event)); // le clavier
		
		// mise en place de l'animation (avec une classe anonyme)
		new AnimationTimer() {	 // animation de l'image
			// la classe abstraite AnimationTimer demande le code
			// de la méthode handle() pour être complètée.
			@Override
			public void handle(long now) {
				// tout le code de l'animation est placé dans une 
				// méthode écrite plus loin dans ce contrôleur
				doAnimation(now);
			}
		}.start(); // on démarre le thread de l'animation.
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
	 * redessine toute la scene.
	 */
	public void dessinerNeige() {

		// 1: dessiner l'image de fond.

		// réccupération de l'URL du fichier image et transformation en chaine de caractères 
		// (premièr argument attentu par le constructeur de la classe Image).
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
		// sur le clavier.
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
	
	/**
	 * attribut de classe pour mémoriser le moment 
	 * de la dernière action réalisées.
	 */
	private long lastTimer;

	/**
	 * methode qui appelée par le handle() de l'AnimationTimer.
	 * Elle réalise tout ce qui doit être fait à chaque tic
	 * de l'animation.
	 * @param now
	 *           horodatage de l'appel de la méthode (en nanoseconde).
	 */
	public void doAnimation(long now) {
		// le paramètre de animation est en nanoseconde.
		// il y a un millard de nanosecondes dans une seconde.
		final long MILLIARD = 1000000000L;

		// nombres de TICs par seconde pour la descente des flocons
		final int NBTICS = 20; 

		long interval = now - lastTimer;
		if (interval >= MILLIARD/NBTICS) { // une action tous les 20ieme (cf valeur de NBTICS) de secondes
			// donc 20 actions par seconde si la charge le permet.
			neige.tomberFlocons();
			dessinerNeige();
			lastTimer = now;
		}

	}


}