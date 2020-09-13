package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.nuxeo.ecm.core.work.AbstractWork;
import org.nuxeo.runtime.api.Framework;

import org.nuxeo.ecm.core.api.IterableQueryResult;

public class SampleWork extends AbstractWork {

    private static final Log log = LogFactory.getLog(SampleWork.class);

    public static final String CATEGORY = "sampleWork";

    public SampleWork(String originatingUsername){ 
        setOriginatingUsername(originatingUsername);
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
        log.error(originatingUsername);

        openUserSession();
                
        IterableQueryResult it = session.queryAndFetch("select * from Document", org.nuxeo.ecm.core.query.sql.NXQL.NXQL); 
        log.error(it.size());
        it.close();


    }

}

