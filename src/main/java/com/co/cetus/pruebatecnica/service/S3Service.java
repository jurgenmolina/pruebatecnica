package com.co.cetus.pruebatecnica.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * Uploads a list of files to a specified folder in the S3 bucket.
     * @param files List of MultipartFile to be uploaded.
     * @param folderName The name of the folder in the S3 bucket.
     * @return List of URLs of the uploaded files.
     */
    public List<String> uploadFiles(List<MultipartFile> files, String folderName) {
        Objects.requireNonNull(files, "File list must not be null");
        Objects.requireNonNull(folderName, "Folder name must not be null or empty");

        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            File convertedFile = convertMultiPartToFile(file);
            String fileName = folderName + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
            if (!convertedFile.delete()) {
                // Log warning if file was not deleted
            }
            fileUrls.add(amazonS3.getUrl(bucketName, fileName).toString());
        }
        return fileUrls;
    }

    /**
     * Downloads a file from the S3 bucket.
     * @param fileName The name of the file to be downloaded.
     * @return S3Object representing the downloaded file.
     */
    public S3Object downloadFile(String fileName) {
        Objects.requireNonNull(fileName, "File name must not be null or empty");
        return amazonS3.getObject(new GetObjectRequest(bucketName, fileName));
    }

    /**
     * Deletes a file from the S3 bucket.
     * @param fileName The name of the file to be deleted.
     */
    public void deleteFile(String fileName) {
        Objects.requireNonNull(fileName, "File name must not be null or empty");
        amazonS3.deleteObject(bucketName, fileName);
    }

    /**
     * Converts a MultipartFile to a File.
     * @param file MultipartFile to be converted.
     * @return Converted File object.
     */
    private File convertMultiPartToFile(MultipartFile file) {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error converting multipart file to file", e);
        }
        return convFile;
    }
}
