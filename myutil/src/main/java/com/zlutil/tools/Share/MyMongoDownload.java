package com.zlutil.tools.Share;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

@Service
public class MyMongoDownload {

    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    GridFsTemplate gridFsTemplate;

    public void uploadWithMongo(MultipartFile file) throws IOException {
        ObjectId id = gridFsTemplate.store(file.getInputStream(), "name");
    }

    public void downloadWithMongo(String id) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));

        if (Objects.nonNull(gridFSFile)) {
            try {
                Path path = Files.createTempFile(Paths.get(System.getProperty("user.dir")), "tmp", "jpg");
                OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.WRITE);
                gridFSBucket.downloadToStream(gridFSFile.getObjectId(), outputStream);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
