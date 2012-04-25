package com.neosavvy.utils;

import org.clapper.util.misc.MIMETypeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;


/**
 * This class is a derivative work of Fineline via Tommy Odom.
 * The code herein cannot be distributed without first establishing prior written consent
 * from tommy.odom@gmail.com
 */
public class FileUtils {
    public static final String CONTENT_TYPE_STL = "model/stl";
    public static final String CONTENT_TYPE_EMAIL = "message/rfc822";
    public static final String STL_EXTENSION = ".stl";
    public static final String EMAIL_EXTENSION = ".eml";

    private static MimetypesFileTypeMap mimeTypes = new MimetypesFileTypeMap();

    public static String getExtension(File file) {
        return FileUtils.getExtension(file.getName());
    }

    public static String getExtension(String fileName) {
        String extension = "";

        if (fileName != null) {
            int index = fileName.lastIndexOf('.');

            if (index != -1) {
                extension = fileName.substring(index);
            }
        }

        return extension;
    }

    public static String getExtensionFromContentType(String contentType) {
        return MIMETypeUtil.fileExtensionForMIMEType(contentType);
    }

    public static String getBaseName(File file) {
        return FileUtils.getBaseName(file.getName());
    }

    public static String getBaseName(String fileName) {
        String baseName = "";

        if (fileName != null) {
            int index = fileName.lastIndexOf('.');

            if (index != -1) {
                baseName = fileName.substring(0, index);
            }
        }

        return baseName;
    }

    public static String getContentType(File file) {
        String extension = getExtension(file).toLowerCase();
        if (extension.endsWith(STL_EXTENSION)) {
            return CONTENT_TYPE_STL;
        }
        else if (extension.endsWith(EMAIL_EXTENSION)) {
            return CONTENT_TYPE_EMAIL;
        }

        return FileUtils.mimeTypes.getContentType(file);
    }

    public static String getContentType(String filename) {
        return FileUtils.getContentType(new File(filename));
    }

    public static void copyFile(File source, File destination) throws FileNotFoundException, IOException {
        FileInputStream input = null;
        FileOutputStream output = null;

        try {
            input = new FileInputStream(source);
            writeFile(input, destination);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void writeFile(InputStream input, File destination) throws IOException {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(destination);
            byte[] buffer = new byte[32768];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
