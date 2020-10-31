package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public int createFile(MultipartFile multipartFile, Integer userid) {
        try {
            // Check if the file with the filename exists
            if (isFileNameExists(multipartFile.getOriginalFilename())) {
                return -2;
            }

            File file = new File(
                    null,
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    String.valueOf(multipartFile.getSize()),
                    userid,
                    multipartFile.getBytes()
            );

            return fileMapper.insert(file);
        } catch (IOException e) {
            return -1;
        }
    }

    public boolean isFileNameExists(String filename) {
        File file = fileMapper.getFileByName(filename);

        if (file == null) {
            return false;
        } else {
            return true;
        }
    }

    public File[] getAllFiles() {
        return fileMapper.getAllFiles();
    }

    public void deleteFile(Integer fileId) {
        fileMapper.delete(fileId);
    }
}
