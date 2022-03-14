package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.nuxeo.ecm.core.work.AbstractWork;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.model.Property;

import org.nuxeo.runtime.transaction.TransactionHelper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleWork extends AbstractWork {

    private static final Log log = LogFactory.getLog(SampleWork.class);

    private static final long serialVersionUID = 1L;

    public static final String CATEGORY = "sampleWork";
    private final String FILE_CONTENT = "file:content"; 
    private final String FILE_FORMAT_TIFF = "image/tiff"; 

    protected final String xpath;

    public SampleWork(String repositoryName, String docId, String xpath){ 
        super(repositoryName + ':' + docId + ':' + xpath + ":sampleWork");
        setOriginatingUsername(originatingUsername);
        setDocument(repositoryName, docId);
        this.xpath = xpath;
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    @Override 
    public String getTitle() {
        return "Sample work";
    }

    @Override
    public void work() {
        this.setProgress(Progress.PROGRESS_INDETERMINATE);
        this.setStatus("Converting TIFF to PDF");

        List<Blob> tempFiles = new ArrayList<>();

        openSystemSession();

        if (TransactionHelper.isTransactionActiveOrMarkedRollback()) {
            TransactionHelper.commitOrRollbackTransaction();
        }

        TransactionHelper.startTransaction();

        try {
            DocumentModel doc = session.getDocument(new IdRef(id));
//            Property fileProp = doc.getProperty(FILE_CONTENT);
//            Blob tifBlob = (Blob) fileProp.getValue();
//            if (tifBlob != null) {
//                if (tifBlob.getMimeType().equals(FILE_FORMAT_TIFF)) {
//                    log.debug("FILE:CONTENT is TIFF, Converting to PDF");
//                }
//            }
        } catch (Exception ex) {
            log.error("An issue occurred while converting file to PDF", ex);
//        } finally {
//            cleanupTempFiles(tempFiles);
        }

    }

    protected void cleanupTempFiles(List<Blob> tempFiles) {
        for (Blob blob : tempFiles) {
            if (blob == null) {
                continue;
            }
            File file = blob.getFile();
            if (file == null || !file.exists()) {
                continue;
            }
            File directory = file.getParentFile();
            try {
                Files.delete(file.toPath());
            } catch (IOException | SecurityException e) {
                log.error(String.format("Cannot delete temporary file %s", file.getAbsolutePath()), e);
            }

            if (directory == null || !directory.exists()) {
                continue;
            }
            try {
                Files.delete(directory.toPath());
            } catch (IOException | SecurityException e) {
                log.error(String.format("Cannot delete temporary directory %s", directory.getAbsolutePath()), e);
            }
        }
    }
}
