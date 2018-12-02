package springboot.mongo;

import java.io.File;
import java.io.IOException;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

public class MongodbAppHbaseTest {  
    public static void main(String[] args) throws IOException{  
    	Mongo mongo = new Mongo();
        DB db = mongo.getDB("images2017-3-18");
//    	DB db = mongo.getDB("wan");
//        GridFS myFS = new GridFS(db,"wan");
        GridFS myFS = new GridFS(db,"pei");
        //保存文件  
//        GridFSFile file = myFS.createFile(new File("F://1111/image1.jpg"));  
//        file.save();
          
        //输出文件  
        DBCursor dbCursor = myFS.getFileList();
		while (dbCursor.hasNext()) {
			String filename = dbCursor.next().get("filename").toString();
			GridFSDBFile file =myFS.findOne(filename); 
			file.writeTo(new File("F://1111/"+filename));
		}
        
          
         //删除文件  
        /*GridFSDBFile file =myFS.findOne("image1.jpg"); 
        myFS.remove((ObjectId) file2.getId());*/  
    }  
} 