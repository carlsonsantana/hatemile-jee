/*
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package org.hatemile.jee;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * The ResponseWrapper class help to overwrite the original response of server.
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    /**
     * The writer for response.
     */
    private final CharArrayWriter output;

    /**
     * Initializes a new object that help to overwrite the original response of
     * server.
     * @param response The response original response to the client.
     */
    public ResponseWrapper(final HttpServletResponse response) {
        super(response);
        this.output = new CharArrayWriter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return output.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(output);
    }
}
