package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.work.api.WorkManager;
import org.nuxeo.runtime.api.Framework;

public class SampleListener implements EventListener {
  
    private static final Log log = LogFactory.getLog(SampleListener.class);

    @Override
    public void handleEvent(Event event) {

        if(event.getName().equalsIgnoreCase("sampleListener")){
            log.info("Hi from sample listener");
        } 

        WorkManager workManager = Framework.getService(WorkManager.class);

        if (workManager == null) {
            throw new RuntimeException("No WorkManager available");
        }

        SampleWork work = new SampleWork();
        workManager.schedule(work);

    }
}
