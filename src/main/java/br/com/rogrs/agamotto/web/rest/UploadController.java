package br.com.rogrs.agamotto.web.rest;

import br.com.rogrs.agamotto.domain.Arquivos;
import br.com.rogrs.agamotto.repository.ArquivosRepository;
import br.com.rogrs.agamotto.service.FileStorageService;

import br.com.rogrs.agamotto.service.ProcessarArquivosService;
import br.com.rogrs.agamotto.utils.HashCodeMD5;
import br.com.rogrs.agamotto.web.rest.dto.UploadFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
public class UploadController {

    @Autowired
    private ArquivosRepository arquivoRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProcessarArquivosService processarArquivosService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        UploadFileResponse response = fileStorageService.storeFile(file);

        try {
            Arquivos a = new Arquivos(response.getFileName());
            a.setTipo("AFD");
            a.setCaminho(response.getFileDownloadUri());

            HashCodeMD5 md5 = new HashCodeMD5(response.getFileDownloadUri());
            a.setMd5(md5.getHashMD5());
            arquivoRepository.save(a);

        } catch (Exception e) {
            log.error("Erro ao gravar dados {}", e.getMessage(), e);
        }

        processarArquivosService.execute();

        return response;
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}