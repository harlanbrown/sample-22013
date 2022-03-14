package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import org.nuxeo.ecm.core.work.api.WorkManager;
import org.nuxeo.runtime.api.Framework;

public class SampleListener implements EventListener {

    private static final Log log = LogFactory.getLog(SampleListener.class);

    @Override
    public void handleEvent(Event event) {
        EventContext ctx = event.getContext();
        if(event.getName().equalsIgnoreCase("documentCreated")){
            log.debug("HI FROM SAMPLELISTENER");
            DocumentEventContext docCtx = (DocumentEventContext) ctx;
            DocumentModel doc = docCtx.getSourceDocument();
            log.debug(doc.toString());
            log.debug(doc.getRepositoryName());
            log.debug(doc.getId());
            WorkManager workManager = Framework.getService(WorkManager.class);
            if (workManager == null) {
                throw new RuntimeException("No WorkManager available");
            }
            SampleWork work = new SampleWork(doc.getRepositoryName(), doc.getId(), "file:content");
            workManager.schedule(work);
        }
    }
}
