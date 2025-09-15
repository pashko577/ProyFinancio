package pe.edu.utp.financio.test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class PruebaMongoDB {
        public static void main(String[] args) {
        try (MongoClient client = MongoClients.create("mongodb+srv://tonnyghp577:root@cluster0.mogadiy.mongodb.net/")) {
            MongoDatabase db = client.getDatabase("financio");
            MongoCollection<Document> metas = db.getCollection("metas");

            System.out.println("✅ Conectado a MongoDB");

       Document meta = new Document("idMeta", 1)
                    .append("idUsuario", 76319763)
                    .append("nombre", "Comprar Casa")
                    .append("montoObjetivo", 50000)
                    .append("acumulado", 0)
                    .append("porcentaje", 20)
                    .append("fechaLimite", "2026-12-31")
                    .append("activa", true);

            metas.insertOne(meta);
            System.out.println("✅ Meta insertada en MongoDB correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error en MongoDB: " + e.getMessage());
        }
    }

}
