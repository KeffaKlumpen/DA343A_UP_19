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
    private static ContactFileManager instance;

    public static ContactFileManager getInstance() {
        if(instance == null)
            instance = new ContactFileManager();
        return instance;
    }

    // TODO: Make this actually create the file and directory if it doesn't exist..
    public void writeContactLists(Hashtable<User, User[]> contactLists){
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("data/contactLists.dat")))){
            oos.writeObject(contactLists);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // TODO: Only readObject if the file exists.
    public Hashtable<User, User[]> readContactLists(){
        Hashtable<User, User[]> contactList = new Hashtable<>();
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("data/contactLists.dat")))){
            try {
                contactList = (Hashtable<User, User[]>) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contactList;
    }
}
