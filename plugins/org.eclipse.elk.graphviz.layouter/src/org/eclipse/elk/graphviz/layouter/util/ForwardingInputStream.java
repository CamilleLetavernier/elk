/*******************************************************************************
 * Copyright (c) 2009, 2015 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiel University - initial API and implementation
 *******************************************************************************/
package org.eclipse.elk.graphviz.layouter.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An input stream that forwards all read data to an output stream.
 * 
 * @kieler.design 2014-04-17 reviewed by cds, chsch, tit, uru
 * @kieler.rating 2009-12-11 proposed yellow msp
 * @author msp
 */
public class ForwardingInputStream extends InputStream {

    /** the input stream that is wrapped by this instance. */
    private final InputStream inputStream;
    /** the output stream to which all read data is forwarded. */
    private final OutputStream outputStream;
    
    /**
     * Creates a forwarding input stream with the given input and output streams.
     * 
     * @param theinputStream the input stream from which data is read
     * @param theoutputStream the output stream to which data is written
     */
    public ForwardingInputStream(final InputStream theinputStream,
            final OutputStream theoutputStream) {
        this.inputStream = theinputStream;
        this.outputStream = theoutputStream;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int read() throws IOException {
        int data = inputStream.read();
        if (data >= 0) {
            outputStream.write(data);
        }
        return data;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int read(final byte[] b) throws IOException {
        int count = inputStream.read(b);
        if (count > 0) {
            outputStream.write(b, 0, count);
        }
        return count;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        int count = inputStream.read(b, off, len);
        if (count > 0) {
            outputStream.write(b, off, count);
        }
        return count;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long skip(final long n) throws IOException {
        return inputStream.skip(n);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int available() throws IOException {
        return inputStream.available();
    }
    
    /**
     * Closes this input stream and releases any system resources associated
     * with the stream. This method does <em>not</em> close the output stream.
     * 
     * @throws IOException if an I/O error occurs.
     */
    public void close() throws IOException {
        inputStream.close();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void mark(final int readlimit) {
        inputStream.mark(readlimit);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void reset() throws IOException {
        inputStream.reset();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean markSupported() {
        return inputStream.markSupported();
    }

}
