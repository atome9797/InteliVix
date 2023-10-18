package com.intellivix.service;

import com.intellivix.model.newsFile;
import com.intellivix.repository.newsFileRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.security.SecureRandom;
import java.util.*;

@Service
@RequiredArgsConstructor
public class newsFileService {

    private final newsFileRepository newsFileRepository;

    public boolean uploadFile(MultipartHttpServletRequest multipartHttpServletRequest, Long NewsNo) throws Exception {
        if (NewsNo == null) throw new NullPointerException("EMPTY NEWS NO");

        Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();
        Iterator<Map.Entry<String, MultipartFile >> itr = files.entrySet().iterator();

        MultipartFile mFile;
        String saveFilePath = "", randomFileName = "";

        Calendar cal = Calendar.getInstance();
        List<Long> resultList = new ArrayList<Long>();
        while (itr.hasNext()) {
            Map.Entry<String, MultipartFile> entry = itr.next();
            mFile = entry.getValue();
            int fileSize = (int)mFile.getSize();

            if (fileSize > 0) {
                if (findImage(NewsNo) != null) {
                    deleteImage(NewsNo);
                }
                String filePath = "/intellivix/tomcat/resource/";
                //filePath = filePath + File.separator + String.valueOf(cal.get(Calendar.YEAR)) + File.separator + String.valueOf(cal.get(Calendar.MONTH) + 1);
                randomFileName = "FILE_" + RandomStringUtils.random(8,0,0,false,true,null,new SecureRandom());

                String realFileName = mFile.getOriginalFilename();
                String fileExt = Objects.requireNonNull(realFileName).substring(realFileName.lastIndexOf(".") + 1);
                String saveFileName = randomFileName + "." + fileExt;
                saveFilePath = filePath + File.separator + saveFileName;

                File filePyhFolder = new File(filePath);


                if (!filePyhFolder.exists()) {
                    if (!filePyhFolder.mkdirs()) {
                        throw new Exception("File.mkdir(): Fail. ::::  "+ filePyhFolder.getAbsolutePath());
                    }
                }

                File saveFile = new File(saveFilePath);

                if (saveFile.isFile()) {
                    boolean _exist = true;

                    int idx = 0;

                    while (_exist) {
                        idx ++;
                        saveFileName = randomFileName + "(" + idx + ")." + fileExt;
                        String dictFile = filePath + File.separator + saveFileName;
                        _exist = new File(dictFile).isFile();

                        if (!_exist) {
                            saveFilePath = dictFile;
                        }
                    }
                    mFile.transferTo(new File(saveFilePath));
                } else {
                    mFile.transferTo(saveFile);
                }
                newsFile newsFiles = com.intellivix.model.newsFile.builder()
                        .newsNo(NewsNo)
                        .origFileName(realFileName)
                        .saveFileName(saveFileName)
                        .fileSize(fileSize)
                        .fileExt(fileExt)
                        .filePath(filePath)
                        .deleteYn("N")
                        .build();
                resultList.add(newsFileRepository.save(newsFiles).getNo());
            }
        }
        return files.size() == resultList.size();
    }

    public newsFile findImage(Long newsNo) {
        return newsFileRepository.findByNewsNo(newsNo);
    }

    public void deleteImage(Long newsNo) {
        newsFile newsFile = newsFileRepository.findByNewsNo(newsNo);
        File chkFile = new File(newsFile.getFilePath()+newsFile.getSaveFileName());

        if (chkFile.exists()) {
            chkFile.delete();
            newsFileRepository.deleteByNewsNo(newsNo);
        }
    }
}
