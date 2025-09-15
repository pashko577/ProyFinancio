/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.dao.impl;


import com.mongodb.client.*;
import org.bson.Document;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.financio.dao1.MetaDAO;
import pe.edu.utp.financio.modelo.Meta;
import pe.edu.utp.financio.util.ConexionMongo;

public class MetaDAOMongo implements MetaDAO{
  private final MongoCollection<Document> collection;

    public MetaDAOMongo() {
        MongoDatabase db = ConexionMongo.getDatabase("financio");
        this.collection = db.getCollection("metas");
    }

    @Override
    public int registrar(Meta meta) throws SQLException {
        Document doc = new Document("idUsuario", meta.getIdUsuario())
                .append("nombre", meta.getNombre())
                .append("montoObjetivo", meta.getMontoObjetivo())
                .append("acumulado", 0.0)
                .append("porcentaje", meta.getPorcentaje())
                .append("fechaLimite", meta.getFechaLimite().toString())
                .append("activa", true);
        collection.insertOne(doc);
        return 1; // En Mongo no hay ID autoincremental clásico
    }

    @Override
    public List<Meta> listarActivasPorUsuario(int idUsuario) throws SQLException {
        List<Meta> metas = new ArrayList<>();
        FindIterable<Document> docs = collection.find(new Document("idUsuario", idUsuario)
                .append("activa", true));
        for (Document d : docs) {
            metas.add(new Meta(
                d.getInteger("idMeta", 0),
                d.getInteger("idUsuario"),
                d.getString("nombre"),
                d.getDouble("montoObjetivo"),
                d.getDouble("acumulado"),
                d.getDouble("porcentaje"),
                java.time.LocalDate.parse(d.getString("fechaLimite")),
                d.getBoolean("activa")
            ));
        }
        return metas;
  
    }

    @Override
    public void actualizarAcumulado(int idMeta, double monto) throws SQLException {
        collection.updateOne(new Document("idMeta", idMeta),
                new Document("$inc", new Document("acumulado", monto)));
    }

    @Override
    public void desactivarSiCumplida(int idMeta) throws SQLException {
        Document meta = collection.find(new Document("idMeta", idMeta)).first();
        if (meta != null && meta.getDouble("acumulado") >= meta.getDouble("montoObjetivo")) {
            collection.updateOne(new Document("idMeta", idMeta),
                    new Document("$set", new Document("activa", false)));
        }
    }
}