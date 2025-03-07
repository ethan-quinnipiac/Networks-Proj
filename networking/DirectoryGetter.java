package networking;

import java.io.File;
import java.util.ArrayList;

/**
 * Written by Kyle Macdonald
 * Class takes a directory path provided as a string and has a method that can be used to return all the files
 * and directories of the specified path.
 */


public class DirectoryGetter{

    public String startingFolder;

    public DirectoryGetter(String startingFolder){
        this.startingFolder = startingFolder;
    }

    public void setDirectory(String dir){
        this.startingFolder = dir;
    }
    public String getDirectory(){
        return this.startingFolder;
    }

    public ArrayList<String> getDirectories(){
        ArrayList<String> toReturn = new ArrayList<>();
        File directory = new File(this.startingFolder);
        
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (file.isDirectory()) {
                        toReturn.add("Directory: " + file.getName());
                    } else if (file.isFile()) {
                        toReturn.add("File: " + file.getName());
                    }
                }
            }else{ //ERRORS in case directory isn't found/no files in directory
                System.out.println("ERROR: No files in directory provided: " + this.startingFolder);
            }
        }else{
            System.out.println("ERROR: Directory not found: " + this.startingFolder);
        }


        return toReturn;
    }
}