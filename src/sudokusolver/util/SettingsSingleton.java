package sudokusolver.util;

public class SettingsSingleton {
	private static SettingsSingleton s;
	private int gridSize;
	
	private SettingsSingleton() {
		gridSize = -1;
	}
	
	public static SettingsSingleton getInstance() {
		if (s == null)
			s = new SettingsSingleton();
		return s;
	}
	
	public void setGridSize(int size) {
		gridSize = size;
	}
	
	public int getGridSize() {
		return gridSize;
	}

}
