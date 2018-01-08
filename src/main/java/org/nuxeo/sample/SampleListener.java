package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.InvalidChainException;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.core.api.RecoverableClientException;
import org.nuxeo.ecm.core.api.blobholder.BlobHolderAdapterService;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import org.nuxeo.ecm.platform.web.common.exceptionhandling.ExceptionHelper;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.transaction.TransactionHelper;

import org.nuxeo.sample.SampleOperation;

public class SampleListener implements EventListener {
  
    private static final Log log = LogFactory.getLog(SampleListener.class);

    @Override
    public void handleEvent(Event event) {
        EventContext eventContext = event.getContext();
        if (!(eventContext instanceof DocumentEventContext)) {
          return;
        }

        DocumentEventContext docCtx = (DocumentEventContext) eventContext;
        DocumentModel doc = docCtx.getSourceDocument();

        log.debug("Hi from sample listener");

        try (OperationContext ctx = new OperationContext(eventContext.getCoreSession())) {           
            AutomationService os = Framework.getService(AutomationService.class);                    

            if (eventContext instanceof DocumentEventContext) {                                      
                BlobHolderAdapterService blobService = Framework.getService(BlobHolderAdapterService.class);
                Blob blob = blobService.getBlobHolderAdapter(doc).getBlob();

                ctx.setInput(blob);

                try {                               
                    os.run(ctx, SampleOperation.ID);             
                } catch (InvalidChainException e) { 
                }                                   

            }                                   
        } catch (OperationException t) {        
            log.error(t, t);                    
        }                 
    }
}
