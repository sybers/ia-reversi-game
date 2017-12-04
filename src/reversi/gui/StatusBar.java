package reversi.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;

/**
 * Barre de status placée en bas de la fenêtre de l'application
 * @author stanyslasbres
 */
public class StatusBar extends JLabel {
    private static final long serialVersionUID = -8230774255203043055L;

    private static Color InfoColor = new Color(2, 117, 216);
    private static Color ErrorColor = new Color(217, 83, 79);
    private static Color WarningColor = new Color(240, 173, 78);
    private static Color SuccessColor = new Color(92, 184, 92);

    /**
     * Constructor
     */
    public StatusBar() {
        super(" ");
        init();
    }

    /**
     * Constructor
     * @param text default text
     */
    public StatusBar(String text) {
        super(text);
        init();
    }

    /**
     * Initialise les styles du composant
     */
    private void init() {
        setForeground(StatusBar.InfoColor);
        setBackground(Color.WHITE);
        setBorder(
            new CompoundBorder(
                BorderFactory.createEmptyBorder(5, 0, 0, 0),
                BorderFactory.createLoweredBevelBorder()
            )
        );
    }

    /**
     * Définit un message d'info à afficher
     * @param text texte à afficher
     */
    public void setInfoMessage(String text) {
        setForeground(StatusBar.InfoColor);
        setText(text);
    }

    /**
     * Définit un message d'erreur à afficher
     * @param text texte à afficher
     */
    public void setErrorMessage(String text) {
        setForeground(StatusBar.ErrorColor);
        setText(text);
    }

    /**
     * Définit un message d'erreur à afficher
     * @param text texte à afficher
     */
    public void setWarningMessage(String text) {
        setForeground(StatusBar.WarningColor);
        setText(text);
    }

    /**
        * Définit un message de succès à afficher
        * @param text texte à afficher
     */
    public void setSuccessMessage(String text) {
        setForeground(StatusBar.SuccessColor);
        setText(text);
    }
}
