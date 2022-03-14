package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.core.util.Properties;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.model.Property;
import org.nuxeo.ecm.core.work.AbstractWork;
import org.nuxeo.runtime.api.Framework;

public class SampleWork extends AbstractWork {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(SampleWork.class);

    public static final String CATEGORY = "sampleWork";

    private final String FILE_CONTENT = "file:content"; 
    private final String FILE_FORMAT_TIFF = "image/tiff";
    private final String xpath;

    protected AutomationService service;

    protected Properties chainParameters = new Properties();

    public SampleWork(String repositoryName, String docId, String xpath){ 
        super(repositoryName + ':' + docId + ':' + xpath + ":sampleWork");
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

        openSystemSession();

        if (!session.exists(new IdRef(docId))){

            return;

        } else {

            DocumentModel doc = session.getDocument(new IdRef(docId));

            Property fileProp = doc.getProperty(FILE_CONTENT);

            Blob tiffBlob = (Blob) fileProp.getValue();

            if (tiffBlob != null) {

                if (tiffBlob.getMimeType().equals(FILE_FORMAT_TIFF)) {

                    AutomationService service = Framework.getService(AutomationService.class);

                    try { 
                        OperationContext ctx = new OperationContext(session);
                        ctx.setInput(doc);
                        service.run(ctx, "ConvertMainBlobToPDF");
                    } catch (OperationException oe) {
                        log.error(oe);
                    }

                }
            }
        }
    }
}
