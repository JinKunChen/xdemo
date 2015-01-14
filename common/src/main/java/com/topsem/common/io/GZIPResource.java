package com.topsem.common.io;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Created by Chen on 14-7-3.
 */
public class GZIPResource extends InputStreamResource implements Resource {
    public GZIPResource(Resource delegate) throws IOException {
        super(new GZIPInputStream(delegate.getInputStream()));
    }
}




