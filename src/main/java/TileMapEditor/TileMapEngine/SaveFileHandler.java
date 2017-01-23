package TileMapEditor.TileMapEngine;

import TileMapEditor.Tiles.TileMap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveFileHandler {

    private static SaveFileHandler instance;

    private String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Tilemap";

    public static SaveFileHandler getInstance() {
        if(instance == null) {
            instance = new SaveFileHandler();
        }
        return instance;
    }

    /**
     * Create the save directory if it doesn't exist.
     */
    private SaveFileHandler() {
        File f = new File(path);
        if(!f.isDirectory()) {
            f.mkdir();
        }
    }

    /**
     * Returns a list of all files in the save folder
     * @return
     */
    public List<String> getFiles() {
        List<String> l = new ArrayList<>();
        File[] files = new File(path).listFiles();

        for(File f : files) {
            l.add(f.getName());
        }

        return l;
    }

    /**
     * Returns the file with the given name.
     * Throws exception if file does not exist in the save directory.
     * @param name of the save file
     * @return save file
     * @throws FileNotFoundException
     */
    public File getFile(String name) throws FileNotFoundException {
        File f = new File(path+File.separator+name);

        if(!f.exists()) {
            throw new FileNotFoundException();
        }

        return f;
    }

    /**
     * Write a save file to the save directory. Will overwrite if file exists!
     *
     * Lager en tekst ut av tillemappen for å lagre slik:
     * xpos,ypos,tileImageId,solid
     * med mellomrom som skilletegn mellom hver tile som skal lagres
     * @param name of the new save
     */
    public void writeFile(String name, TileMap tileMap) {
        File file = new File(path+File.separator+name);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(tileMap.toString());
            bw.newLine();
            bw.write(tileMap.decString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get path to the save folder
     * @return path
     */
    public String getPath() {
        return path;
    }

}
