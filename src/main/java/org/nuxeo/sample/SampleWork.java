package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.nuxeo.ecm.core.work.AbstractWork;
import org.nuxeo.runtime.api.Framework;

public class SampleWork extends AbstractWork {

    private static final Log log = LogFactory.getLog(SampleWork.class);

    public static final String CATEGORY = "sampleWork";

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
        log.info("HI from sample worker");
    }

}

