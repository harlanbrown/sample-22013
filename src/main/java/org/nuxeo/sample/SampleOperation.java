package org.nuxeo.sample;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.PathRef;

/**
 *
 */
@Operation(id=SampleOperation.ID, category=Constants.CAT_DOCUMENT, label="SampleOperation", description="Describe here what your operation does.")
public class SampleOperation {

    public static final String ID = "Document.SampleOperation";
    private static final Log log = LogFactory.getLog(SampleOperation.class);

    @Context
    protected CoreSession session;

    @OperationMethod
    public void run(Blob blob) {
        log.debug("Hi from sample operation");
        log.debug(blob.getFilename());
        String query = "SELECT * FROM Document";
        DocumentModelList response = session.query(query);

    }
}
