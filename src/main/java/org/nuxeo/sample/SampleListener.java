package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.event.DocumentEventTypes;
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
        DocumentEventContext docCtx = (DocumentEventContext) ctx;
        DocumentModel doc = docCtx.getSourceDocument();
        log.debug(doc);

        if (DocumentEventTypes.DOCUMENT_CREATED.equals(event.getName())){
            WorkManager workManager = Framework.getService(WorkManager.class);
            if (workManager == null) {
                throw new RuntimeException("No WorkManager available");
            }
            SampleWork work = new SampleWork(doc.getRepositoryName(), doc.getRef().toString(), "file:content");
            workManager.schedule(work);
        }
    }
}
