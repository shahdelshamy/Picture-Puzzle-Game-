/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buzzlegame;

/**
 *
 * @author sayed
 */

public class LevelRequirments {
	
	private String []defaultSortedIconsArray;
	private int levelNumber;
	private String defaultIconPath;
	
	public LevelRequirments() {
		super();
	}
	
	
	public LevelRequirments(String[] defaultSortedIconsArray, int levelNumber, String defaultIconPath) {
		super();
		this.defaultSortedIconsArray = defaultSortedIconsArray;
		this.levelNumber = levelNumber;
		this.defaultIconPath = defaultIconPath;
	}


	public String[] getDefaultSortedIconsArray() {
		return defaultSortedIconsArray;
	}
	public void setDefaultSortedIconsArray(String[] defaultSortedIconsArray) {
		this.defaultSortedIconsArray = defaultSortedIconsArray;
	}
	public int getLevelNumber() {
		return levelNumber;
	}
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	public String getDefaultIconPath() {
		return defaultIconPath;
	}
	public void setDefaultIconPath(String defaultIconPath) {
		this.defaultIconPath = defaultIconPath;
	}
	

	
	
	
}
