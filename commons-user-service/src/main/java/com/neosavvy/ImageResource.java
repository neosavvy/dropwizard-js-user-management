package com.neosavvy;

import com.neosavvy.model.ResourceNotFoundException;
import com.neosavvy.model.StorageBucket;
import com.neosavvy.model.StorageBucketType;
import com.neosavvy.utils.FileUtils;
import com.neosavvy.utils.HttpUtils;
import com.neosavvy.utils.StringUtils;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: waparrish
 * Date: 4/24/12
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/image")
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {

    private List<StorageBucket> buckets;

    public List<StorageBucket> getBuckets() {
        
        if( buckets == null )
        {
            buckets = new ArrayList<StorageBucket>();
            buckets.add(new StorageBucket(
                    "images"
                    , StorageBucketType.STANDARD
                    , new File("/tmp/images/")
            ));
            buckets.add(new StorageBucket(
                    "temp"
                    , StorageBucketType.TEMPORARY
                    , new File("/tmp/tempFiles")
            ));
        }
        
        return buckets;
    }

    @POST
    @Path(value="/file/{bucket:.*?}/{key:.*}")
    @Encoded
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    public void uploadFile(@Context Request request, @PathParam("bucket")String bucket, @PathParam("key")String key) throws Exception
    {

        String owner = null;
        String fileNameOverride = null;
        FileItemIterator iter = new ServletFileUpload().getItemIterator(HttpUtils.getHttpRequest());

        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            if (item.isFormField() && item.getFieldName().equals("owner")) {
                owner = Streams.asString(item.openStream());
            }
            else if (item.isFormField() && item.getFieldName().equals("fileNameOverride")) {
                fileNameOverride = Streams.asString(item.openStream());
            }
            else if (!item.isFormField()){
                saveFile(bucket, key, (!StringUtils.isEmpty(fileNameOverride) ? fileNameOverride : item.getName()),
                        parseContentType(item.getContentType()), item.openStream(), owner);
            }
        }

    }


    public void saveFile(String bucket, String key, String fileName, String contentType, InputStream data, String owner)
            throws IOException, ResourceNotFoundException {

        File bucketDir = lookupBucketDirectory(bucket);


        File parentDir = bucketDir;
        File file = new File(parentDir, key);

        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        FileUtils.writeFile(data, file);
    }

    private File lookupBucketDirectory(String bucketName) throws ResourceNotFoundException {
        StorageBucket bucket = getBucket(bucketName);

        if (bucket == null) {
            throw new ResourceNotFoundException("The bucket " + bucketName + " is not a known bucket.");
        }

        if (!bucket.getDirectory().exists()) {
            throw new ResourceNotFoundException("The directory for bucket " + bucketName + " does not exist");
        }

        return bucket.getDirectory();
    }

    public StorageBucket getBucket(String name) {
        for (StorageBucket bucket : getBuckets()) {
            if (bucket.getName().equals(name)) {
                return bucket;
            }
        }
        return null;
    }

    private String parseContentType(String contentType) {
        if (!StringUtils.isEmpty(contentType)) {
            int delimiter = contentType.indexOf(';');

            if (delimiter != -1) {
                return contentType.substring(0, delimiter);
            }

        }

        return contentType;
    }


}
