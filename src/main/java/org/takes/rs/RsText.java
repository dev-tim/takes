/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.takes.rs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import lombok.EqualsAndHashCode;
import org.takes.Response;

/**
 * Plain text response decorator.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 0.1
 */
@EqualsAndHashCode(of = "origin")
public final class RsText implements Response {

    /**
     * Original response.
     */
    private final transient Response origin;

    /**
     * Ctor.
     * @param body Plain text body
     */
    public RsText(final String body) {
        this(body.getBytes());
    }

    /**
     * Ctor.
     * @param body Plain text body
     */
    public RsText(final byte[] body) {
        this(new ByteArrayInputStream(body));
    }

    /**
     * Ctor.
     * @param body Plain text body
     */
    public RsText(final InputStream body) {
        this(new RsEmpty(), body);
    }

    /**
     * Ctor.
     * @param res Original response
     * @param body HTML body
     */
    public RsText(final Response res, final String body) {
        this(res, body.getBytes());
    }

    /**
     * Ctor.
     * @param res Original response
     * @param body HTML body
     */
    public RsText(final Response res, final byte[] body) {
        this(res, new ByteArrayInputStream(body));
    }

    /**
     * Ctor.
     * @param res Original response
     * @param body HTML body
     */
    public RsText(final Response res, final InputStream body) {
        this.origin = new RsWithBody(
            new RsWithHeader(
                new RsWithStatus(res, HttpURLConnection.HTTP_OK),
                "Content-Type", "text/plain"
            ),
            body
        );
    }

    @Override
    public List<String> head() {
        return this.origin.head();
    }

    @Override
    public InputStream body() {
        return this.origin.body();
    }
}