/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buzzlegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;
import static javax.swing.TransferHandler.COPY;
import static javax.swing.TransferHandler.MOVE;
import javax.swing.border.LineBorder;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class BuzzelStructure extends JFrame {

    String username;
    String strength;
    private static final long serialVersionUID = 1L;
    TransferHandlerAction transferHandler;
    ImageIcon defaultIcon = new ImageIcon("pic/default.jpg");
    ImageIcon greetWinnerIcon = new ImageIcon("pic/winner.gif");

    JLabel[] sortedLabelsArray;
    JLabel[] unsortedLabelsArray;

    JLabel collectedPoints;
    JLabel gretWinner;
    JLabel finalImageShape;
    JLabel referenceIconTitle;

    String[] defaultSortedIconsArray;
    JLabel level;

    int levelId=0;
    LevelInformation levelStructure;
    private int correctTriesCounter = 0;
    private String finalImagePath;
    private String[] strength_Level = {"easy", "medium", "hard"};
    private int strength_Level_current_index = 0;

    public BuzzelStructure(String userName, String strengh) {

        this.username = userName;
        this.strength = strengh;
        transferHandler = new TransferHandlerAction();
        levelStructure = new LevelInformation(strengh);
        initalizeTheNextLevel();
    }

    public void initalizeTheNextLevel() {
    	this.correctTriesCounter=0;
        constructArrayBasedOnStrenghType(this.strength);
        this.getLevelInformation(this.levelId);
        initUI();
        this.setLevelDateOnUI();
    }

   
    private void initUI() {
		this.flush();
        int unsorted_x = 10;
        int unsorted_y = 10;
        int width = 100;
        int height = 100;

        int sorted_x = 10;
        int sorted_y = 300;

        int sorted_x_counter = 1;
        int sorted_y_counter = 1;

        int numberOfImagesPerLine = 0;

        if (this.strength.equals("easy")) {
            numberOfImagesPerLine = 2;
            unsorted_x=300;
        } else if (this.strength.equals("medium")) {
            numberOfImagesPerLine = 3;
        }else if(this.strength.equals("hard")){
            numberOfImagesPerLine = 4;
        }

        DragMouseAdapter listener = new DragMouseAdapter();
        for (int i = 0; i < sortedLabelsArray.length; i++) {
            unsortedLabelsArray[i] = new JLabel();
            unsortedLabelsArray[i].addMouseListener(listener);
            unsortedLabelsArray[i].setBounds(unsorted_x, unsorted_y, width, height);
            transferHandler.setTransferAction(unsortedLabelsArray[i], defaultIcon);

            sortedLabelsArray[i] = new JLabel();
            sortedLabelsArray[i].addMouseListener(listener);
            sortedLabelsArray[i].setBounds(sorted_x, sorted_y, width, height);
            transferHandler.setTransferAction(sortedLabelsArray[i], defaultIcon);
            sortedLabelsArray[i].setIcon(defaultIcon);
            add(unsortedLabelsArray[i]);
            add(sortedLabelsArray[i]);

            unsorted_x += 100;
            if (sorted_y_counter >= numberOfImagesPerLine) {
                sorted_y += 100;
                sorted_y_counter = 0;
            }
            if (sorted_x_counter < numberOfImagesPerLine) {
                sorted_x += 100;
            } else {
                sorted_x = 10;
                sorted_x_counter = 0;
            }
            sorted_y_counter++;
            sorted_x_counter++;

        }

        createAdditionalComponentLayout();

    }

    private void createAdditionalComponentLayout() {
        int shifXLength = 0;
        if (this.strength.equals("easy") || this.strength.equals("medium")) {
            shifXLength = 0;
        } else if (this.strength.equals("hard")) {
            shifXLength = 300;
        }
        finalImageShape = new JLabel();
        finalImageShape.setBounds((700 + shifXLength), 320, 200, 200);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setBounds(10, 140, 200, 40);
        usernameLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        usernameLabel.setText("Hello , " + this.username);
        
        JLabel strength_level = new JLabel();
        strength_level.setBounds(10, 170, 200, 40);
        strength_level.setFont(new Font("Verdana", Font.PLAIN, 20));
        strength_level.setText("Strength : " + this.strength);
        
         level = new JLabel();
         level.setBounds(10, 200, 200, 40);
         level.setFont(new Font("Verdana", Font.PLAIN, 20));
         level.setText("Level : " + (this.levelId+1));

        referenceIconTitle = new JLabel();
        
        gretWinner = new JLabel();
        
        gretWinner.setBounds(200, 200, 100, 100);

        collectedPoints = new JLabel();
        collectedPoints.setBounds((730 + shifXLength), 450, 200, 200);
        collectedPoints.setFont(new Font("Verdana", Font.PLAIN, 20));
        collectedPoints.setText("Score : " + correctTriesCounter + " / " + sortedLabelsArray.length);

        referenceIconTitle.setBounds((750 + shifXLength), 200, 200, 200);
        referenceIconTitle.setText("Reference Image");
        referenceIconTitle.setFont(new Font("Serif", Font.BOLD, 15));
        referenceIconTitle.setForeground(Color.red);

        JButton reset = new JButton();
        reset.setBounds((760 + shifXLength), 200, 100, 30);
        reset.setText("RESET");
        reset.setBorder(new LineBorder(Color.BLACK));
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setLevelDateOnUI();
            }
        });
        add(reset);
        add(usernameLabel);
        add(strength_level);
        add(level);
        add(referenceIconTitle);
        add(finalImageShape);
        add(collectedPoints);
        add(gretWinner);



    }

    private void collectSuccessTries() {
        correctTriesCounter = 0;
        for (int i = 0; i < sortedLabelsArray.length; i++) {
            if (((ImageIcon) sortedLabelsArray[i].getIcon()).getDescription().equals(defaultSortedIconsArray[i])) {
                correctTriesCounter++;
            }
        }
        collectedPoints.setText("Score : " + correctTriesCounter + " / " + sortedLabelsArray.length);
        
        
        if (correctTriesCounter == defaultSortedIconsArray.length) {
            
            gretWinner.setIcon(greetWinnerIcon);
            soundPlayer("win.wav");
            int selectedOption = JOptionPane.showConfirmDialog(null, "Congratulations, Do You Want To Move To Next Level");
            if (selectedOption == 0) {       	
                int lastLevel = levelStructure.levelComponentsList.size() - 1;
                if (this.levelId == lastLevel) {
                    int option = JOptionPane.showConfirmDialog(null, "Now You Win All levels , Waiting the next more strength click ok.");
                    if (option == 0) {
                        if (this.strength_Level_current_index == this.strength_Level.length-1) {
                            this.strength_Level_current_index = 0;
                        } else {
                            this.strength_Level_current_index++;
                        }
                        this.levelId = 0;
                        this.strength=this.strength_Level[this.strength_Level_current_index];
                        levelStructure = new LevelInformation(this.strength); 
                        this.initalizeTheNextLevel(); 
                    }
                    if (option == 1) {
                        System.exit(0);
                    }

                } else {
                	collectedPoints.setText("Score : 0  / " + sortedLabelsArray.length);
                    this.levelId = this.levelId + 1;
                    this.getLevelInformation(levelId);
                    this.setLevelDateOnUI();
                    level.setText("Level : " + (this.levelId+1));

                }

            
            }

        
        }
        
        
 
    }

    private class DragMouseAdapter extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            JComponent c = (JComponent) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            if (((JLabel) c).getIcon() != null && !((JLabel) c).getIcon().equals(defaultIcon)) {
               handler.exportAsDrag(c, e, TransferHandler.COPY);

            }

        }

    }

    private class TransferHandlerAction {

        public void setTransferAction(JLabel label, ImageIcon icon) {
            label.setTransferHandler(new TransferHandler("icon") {
                @Override
                protected void exportDone(JComponent source, Transferable data, int action) {
                    if (action == MOVE) {
                        ((JLabel) source).setIcon(icon);
                        collectSuccessTries();
                        soundPlayer("drapImage.wav");


                    }
                }

                @Override
                public int getSourceActions(JComponent c) {
                    return COPY | MOVE;
                }
            });

        }

    }

    public static String[] Randomize(String[] arr) {
        String[] randomizedArray = new String[arr.length];
        System.arraycopy(arr, 0, randomizedArray, 0, arr.length);
        Random rgen = new Random();

        for (int i = 0; i < randomizedArray.length; i++) {
            int randPos = rgen.nextInt(randomizedArray.length);
            String tmp = randomizedArray[i];
            randomizedArray[i] = randomizedArray[randPos];
            randomizedArray[randPos] = tmp;
        }

        return randomizedArray;
    }

    private void getLevelInformation(int levelNumber) {

        LevelRequirments level = levelStructure.getLevelById(levelNumber);
        this.defaultSortedIconsArray = level.getDefaultSortedIconsArray();
        this.levelId = level.getLevelNumber();
        this.finalImagePath = level.getDefaultIconPath();
    }

    private void setLevelDateOnUI() {
        String[] arr = Randomize(this.defaultSortedIconsArray);
        for (int i = 0; i < unsortedLabelsArray.length; i++) {
            unsortedLabelsArray[i].setIcon(new ImageIcon(arr[i]));
        }
        for (int i = 0; i < sortedLabelsArray.length; i++) {
            sortedLabelsArray[i].setIcon(defaultIcon);
        }
        this.finalImageShape.setIcon(new ImageIcon(this.finalImagePath));
        this.gretWinner.setIcon(null);

    }

    private void constructArrayBasedOnStrenghType(String type) {
        if (type != null && type.equals("easy")) {
            sortedLabelsArray = new JLabel[4];
            unsortedLabelsArray = new JLabel[4];
            this.strength_Level_current_index=0;
        } else if (type != null && type.equals("medium")) {
            sortedLabelsArray = new JLabel[9];
            unsortedLabelsArray = new JLabel[9];
            this.strength_Level_current_index=1;
        } else if (type != null && type.equals("hard")) {
            sortedLabelsArray = new JLabel[12];
            unsortedLabelsArray = new JLabel[12];
            this.strength_Level_current_index=2;

        }

    }
    
	private void flush() {
		getContentPane().removeAll();
		repaint();
		setLocationRelativeTo(null);
		if (this.strength != null && this.strength.equals("easy")) {
			setSize(920, 700);

		} else if (this.strength != null && this.strength.equals("medium")) {
			setSize(920, 700);

		} else if (this.strength != null && this.strength.equals("hard")) {
			setSize(1220, 700);

		}
	}
        
        
        
     private void soundPlayer(String soundFile){
         InputStream in ;
        try {
            String projectPath=System.getProperty("user.dir");
            in = new FileInputStream(new File(System.getProperty("user.dir")+"/sound/"+soundFile));
            AudioStream ad = new AudioStream(in);
            AudioPlayer.player.start(ad);
        } catch (IOException e) {
            
        }
     }
}
