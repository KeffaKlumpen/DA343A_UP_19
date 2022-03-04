/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Server.Controller;

import Model.User;

import java.io.*;
import java.util.Hashtable;

public class ContactFileManager {
    public static void writeContactLists(Hashtable<User, User[]> contactLists){
        File directory = new File("data/");
        File dataFile = new File(directory, "contactLists.dat");
        try {
            if(!directory.exists()){
                if(!directory.mkdir())
                    throw new IOException("Couldn't create directory.");
            }

            if(!dataFile.exists()){
                if(!dataFile.createNewFile())
                    throw new IOException("Couldn't create dataFile.");
            }

            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));
            oos.writeObject(contactLists);
            oos.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Hashtable<User, User[]> readContactLists(){
        Hashtable<User, User[]> contactList = new Hashtable<>();

        File f = new File("data/contactLists.dat");
        if(f.exists()){
            try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("data/contactLists.dat")))){
                try {
                    contactList = (Hashtable<User, User[]>) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.err.println(f.getPath() + " -- File not found!");
        }

        return contactList;
    }
}
