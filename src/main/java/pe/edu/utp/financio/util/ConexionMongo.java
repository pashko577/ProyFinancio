/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author User
 */
public class ConexionMongo {
        private static final String URI = "mongodb+srv://tonnyghp577:root@cluster0.mogadiy.mongodb.net/";

    public static MongoDatabase getDatabase(String dbName) {
        MongoClient client = MongoClients.create(URI);
        return client.getDatabase(dbName);
    }
}
