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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The AccessibleFilter class is official implementation of Java EE Filter to
 * convert HTML code of web application in a HTML code more accessible.
 */
public class AccessibleFilter implements Filter {

    /**
     * The filter-parameter of configuration path of HaTeMiLe for Java.
     */
    public static final String CONFIGURATION_PATH = "configuration";

    /**
     * The setted parameters of HaTeMiLe for JEE.
     */
    private Map<String, Boolean> parameters;

    /**
     * The configuration path of HaTeMiLe for Java.
     */
    private String configurationPath;

    /**
     * Fill the value of parameter.
     * @param filterConfig The filter configuration.
     * @param parameter The parameter of HaTeMiLe for JEE.
     * @throws ServletException Throw an exception when the filter-parameter is
     * setted, but contains a invalid value.
     */
    private void setParameterBooleanValue(final FilterConfig filterConfig,
            final String parameter) throws ServletException {
        String value = filterConfig.getInitParameter(parameter);
        if ((value == null) || ("true".equals(value))) {
            parameters.put(parameter, Boolean.TRUE);
        } else if ("false".equals(value)) {
            parameters.put(parameter, Boolean.FALSE);
        } else {
            throw new ServletException(new IllegalArgumentException("Invalid "
                    + "filter-parameter value, use \"true\" or \"false\""
                    + " only."));
        }
    }

    /**
     * Initializes the parameters values of HaTeMiLe for JEE.
     * @param filterConfig The filter configuration.
     * @throws ServletException Throw an exception when the filter-parameter is
     * setted, but contains a invalid value.
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        parameters = new HashMap<String, Boolean>();
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.ASSOCIATE_DATA_CELLS);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.ASSOCIATE_LABELS);

        setParameterBooleanValue(filterConfig,
                AccessibleConverter.PROVIDE_SPEAK_PROPERTIES);

        setParameterBooleanValue(filterConfig,
                AccessibleConverter.DISPLAY_ALTERNATIVE_TEXT);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.DISPLAY_CELLS_HEADER);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.DISPLAY_DRAGS_DROPS);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.DISPLAY_LANGUAGES);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.DISPLAY_LINK_ATTRIBUTES);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.DISPLAY_ROLES);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.DISPLAY_TITLES);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.DISPLAY_SHORTCUTS);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.DISPLAY_WAI_ARIA);

        setParameterBooleanValue(filterConfig,
                AccessibleConverter.MAKE_ACCESSIBLE_CLICK);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.MAKE_ACCESSIBLE_DRAG_DROP);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.MAKE_ACCESSIBLE_HOVER);

        setParameterBooleanValue(filterConfig,
                AccessibleConverter.MARK_AUTOCOMPLETE_FIELD);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.MARK_RANGE_FIELD);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.MARK_REQUIRED_FIELD);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.MARK_INVALID_FIELD);

        setParameterBooleanValue(filterConfig,
                AccessibleConverter.NAVIGATE_TO_LONG_DESCRIPTION);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.NAVIGATE_TO_HEADING);
        setParameterBooleanValue(filterConfig,
                AccessibleConverter.NAVIGATE_TO_SKIPPER);

        setParameterBooleanValue(filterConfig,
                AccessibleConverter.HIDE_CHANGES);

        configurationPath = filterConfig.getInitParameter(CONFIGURATION_PATH);
    }

    /**
     * Convert the HTML code of response in a HTML code more accessible.
     * @param request The client request.
     * @param response The response to the client.
     * @param chain The view into the invocation chain of a filtered request.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {
        ResponseWrapper htmlResponseWrapper =
                new ResponseWrapper((HttpServletResponse) response);

        chain.doFilter(request, htmlResponseWrapper);

        if ((request instanceof HttpServletRequest)
                && (response.getContentType() != null)
                && (response.getContentType().contains("text/html"))) {
            HttpServletRequest httpServletRequest =
                    (HttpServletRequest) request;
            String htmlCode = htmlResponseWrapper.toString();
            String currentURL = httpServletRequest.getRequestURL().toString();
            String userAgent = httpServletRequest.getHeader("User-Agent");

            AccessibleConverter accessibleConverter =
                    new AccessibleConverter(htmlCode, configurationPath,
                        parameters, request.getLocale(), currentURL, userAgent);

            response.getWriter().write(accessibleConverter.convert());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
    }
}
