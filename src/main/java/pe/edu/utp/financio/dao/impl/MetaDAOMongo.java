/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.dao.impl;

import com.mongodb.client.*;
import java.math.BigDecimal;
import org.bson.Document;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import pe.edu.utp.financio.dao1.MetaDAO;
import pe.edu.utp.financio.modelo.Aporte;
import pe.edu.utp.financio.modelo.Meta;
import pe.edu.utp.financio.util.ConexionMongo;

public class MetaDAOMongo implements MetaDAO {

    private final MongoCollection<Document> collection;

    public MetaDAOMongo() {
        MongoDatabase db = ConexionMongo.getDatabase("financio");
        this.collection = db.getCollection("metas");
    }

    @Override
    public int registrar(Meta meta) throws SQLException {

        // Convertir LocalDate a java.util.Date para Mongo
        java.util.Date fecha = java.util.Date.from(
                meta.getFechaLimite().atStartOfDay(ZoneId.systemDefault()).toInstant()
        );
        ObjectId objectId=new ObjectId();

        Document doc = new Document()
                .append("_id", new ObjectId())
                .append("idUsuario",meta.getIdUsuario())
                .append("nombre", meta.getNombre())
                .append("montoObjetivo", meta.getMontoObjetivo())
                .append("acumulado", meta.getAcumulado())
                .append("porcentaje", meta.getPorcentaje())
                .append("fechaLimite", fecha)
                .append("activa", meta.isActiva());
        collection.insertOne(doc);
        return 1;
    }

    @Override
    public List<Meta> listarActivasPorUsuario(int idUsuario) throws SQLException {
        List<Meta> metas = new ArrayList<>();
        FindIterable<Document> docs = collection.find(new Document("idUsuario", idUsuario)
                .append("activa", true));

        for (Document d : docs) {
            java.util.Date fecha = d.getDate("fechaLimite"); // Mongo Date
            LocalDate fechaLocal = fecha.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            Meta meta = new Meta();
            meta.setIdMeta(d.getObjectId("_id").toHexString()); // o podrías mapear con _id si quieres
            meta.setNombre(d.getString("nombre"));
            meta.setMontoObjetivo(d.getDouble("montoObjetivo"));
            meta.setAcumulado(d.getDouble("acumulado"));
            meta.setPorcentaje(d.getDouble("porcentaje"));
            meta.setFechaLimite(fechaLocal);
            meta.setActiva(d.getBoolean("activa"));

            metas.add(meta);
        }
        return metas;
    }

    @Override
    public void actualizarAcumulado(String idMeta, BigDecimal monto) throws SQLException {
      Document filtro = new Document("_id", new ObjectId(idMeta));

    Document actualizacion = new Document("$inc",
        new Document("acumulado", monto.doubleValue())
    );

    collection.updateOne(filtro, actualizacion);
    };

    @Override
    public void desactivarSiCumplida(String idMeta) throws SQLException {
        Document doc = collection.find(new Document("_id", new ObjectId(idMeta))).first();

        if (doc != null) {
            double objetivo = doc.getDouble("montoObjetivo");
            double acumulado = doc.getDouble("acumulado");

            if (acumulado >= objetivo) {
                collection.updateOne(
                        new Document("_id", new ObjectId(idMeta)),
                        new Document("$set", new Document("activa", false))
                );
            }
        }
    }
}
