package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.work.AbstractWork;
import org.nuxeo.runtime.api.Framework;

public class SampleWork extends AbstractWork {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(SampleWork.class);

    public static final String CATEGORY = "sampleWork";

    private final String xpath;

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
            log.error("doc ref no good"); 
        } else {
          log.debug("HI FROM WORKER"); 
          DocumentModel theDoc = session.getDocument(new IdRef(docId));
        }

    }

}
