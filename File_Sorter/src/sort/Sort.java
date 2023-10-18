package sort;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
public class Sort {
	//Goal: create a file sorter that puts all files in a designated folder based on their file type
	// if the folder for said file type does not exist, create a new folder
	//Moving files achieved by copying files to new folder and deleting the old copy
	private static ArrayList<File> files = new ArrayList<File>();
	private static File folder;
	private static int missedFiles = 0;
	public static void main(String args[]) {
		JFileChooser select = new JFileChooser();
		select.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		select.showOpenDialog(null);
		System.out.println("test");
		System.out.println("select folder to sort");
		
		//gets folder with unsorted files
		folder = select.getSelectedFile();
		
		
		//open and put contents into an array
		File[] temp = folder.listFiles();
		makeList(temp);
		
		makeFolder();
		temp = folder.listFiles();
		makeList(temp);
		for(File f: files) {
			relocate(f);
		}
		System.out.println("Sorting complete. Number of missed files: " + missedFiles);
		
	}
	public static void makeList(File[] temp) {
		for(File f: temp) {
			if(!files.contains(f))
			files.add(f);
			System.out.println(f.getName());
			if(f.getName().equals(".DS_Store")) {
				files.remove(f);
			}
		}
	}
	public static void makeFolder() {
		for (File f:files) {
			if (f.isDirectory()) {
				//do nothing
			}
			else {
				String name = f.getName();
				name = name.substring(name.indexOf(".") + 1) + "Folder";
				while(name.contains(".")) {
					name = name.substring(name.indexOf(".") + 1);
				}
				if(check(name) == false) {
					File newFolder = new File(folder.getAbsolutePath(), name);
					newFolder.mkdir();
					System.out.println(newFolder.getName() + " created");
				}
				
			}
		}
	}
	
	//checks for folder
	private static boolean check(String name) {
		for(File f: files) {
			if (f.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	//gets Folder
	private static File getF(String name) {
		for(File f: files) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}
	public static void relocate(File f) {
		String name = f.getName();
		if(f.isDirectory()) {
			return;
		}
		name = name.substring(name.indexOf(".") + 1) + "Folder";
		while(name.contains(".")) {
			name = name.substring(name.indexOf(".") + 1);
		}
		
		Path source = Paths.get(f.getAbsolutePath());
		
		try {
			Path destination = Paths.get(getF(name).getAbsolutePath() + "/" + f.getName());
			Files.move(source, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException j) {
			missedFiles ++;
			return;
		}
		
		
		
	}
	

}
