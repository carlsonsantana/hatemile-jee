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

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hatemile.AccessibleAssociation;
import org.hatemile.AccessibleCSS;
import org.hatemile.AccessibleDisplay;
import org.hatemile.AccessibleEvent;
import org.hatemile.AccessibleForm;
import org.hatemile.AccessibleNavigation;
import org.hatemile.implementation.AccessibleAssociationImplementation;
import org.hatemile.implementation.AccessibleCSSImplementation;
import org.hatemile.implementation.AccessibleDisplayScreenReaderImplementation;
import org.hatemile.implementation.AccessibleEventImplementation;
import org.hatemile.implementation.AccessibleFormImplementation;
import org.hatemile.implementation.AccessibleNavigationImplementation;
import org.hatemile.util.Configure;
import org.hatemile.util.css.StyleSheetParser;
import org.hatemile.util.css.phcss.PHCSSParser;
import org.hatemile.util.html.HTMLDOMElement;
import org.hatemile.util.html.HTMLDOMParser;
import org.hatemile.util.html.jsoup.JsoupHTMLDOMParser;

/**
 * The AccessibleConverter class help to convert the HTML code in a code more
 * accessible.
 */
public class AccessibleConverter {

    /**
     * The filter-parameter to associate data cells with header cells.
     */
    public static final String ASSOCIATE_DATA_CELLS =
            "associate-all-data-cells-with-header-cells";

    /**
     * The filter-parameter to associate labels with fields.
     */
    public static final String ASSOCIATE_LABELS =
            "associate-all-labels-with-fields";

    /**
     * The filter-parameter to provide a polyfill to speak and speak-as
     * properties.
     */
    public static final String PROVIDE_SPEAK_PROPERTIES =
            "provide-all-speak-properties";

    /**
     * The filter-parameter to display the alternative text of images.
     */
    public static final String DISPLAY_ALTERNATIVE_TEXT =
            "display-all-alternative-text-images";

    /**
     * The filter-parameter to display the content of cells headers in data
     * cells.
     */
    public static final String DISPLAY_CELLS_HEADER =
            "display-all-cell-headers";

    /**
     * The filter-parameter to display when a element has drag or drop events.
     */
    public static final String DISPLAY_DRAGS_DROPS =
            "display-all-drags-and-drops";

    /**
     * The filter-parameter to display the language of elements.
     */
    public static final String DISPLAY_LANGUAGES = "display-all-languages";

    /**
     * The filter-parameter to display the attributes of links.
     */
    public static final String DISPLAY_LINK_ATTRIBUTES =
            "display-all-links-attributes";

    /**
     * The filter-parameter to display the roles of elements.
     */
    public static final String DISPLAY_ROLES = "display-all-roles";

    /**
     * The filter-parameter to display the titles of elements.
     */
    public static final String DISPLAY_TITLES = "display-all-titles";

    /**
     * The filter-parameter to display the shortcuts of elements.
     */
    public static final String DISPLAY_SHORTCUTS = "display-all-shortcuts";

    /**
     * The filter-parameter to display the WAI-ARIA states of elements.
     */
    public static final String DISPLAY_WAI_ARIA = "display-all-wai-aria-states";

    /**
     * The filter-parameter to make click events accessible by keyboard.
     */
    public static final String MAKE_ACCESSIBLE_CLICK =
            "make-accessible-all-click-events";

    /**
     * The filter-parameter to make drag and drop events accessible by keyboard.
     */
    public static final String MAKE_ACCESSIBLE_DRAG_DROP =
            "make-accessible-all-drag-and-drop-events";

    /**
     * The filter-parameter to make hover events accessible by keyboard.
     */
    public static final String MAKE_ACCESSIBLE_HOVER =
            "make-accessible-all-hover-events";

    /**
     * The filter-parameter to mark autocomplete fields.
     */
    public static final String MARK_AUTOCOMPLETE_FIELD =
            "mark-all-autocomplete-fields";

    /**
     * The filter-parameter to mark range fields.
     */
    public static final String MARK_RANGE_FIELD = "mark-all-range-fields";

    /**
     * The filter-parameter to mark required fields.
     */
    public static final String MARK_REQUIRED_FIELD = "mark-all-required-fields";

    /**
     * The filter-parameter to mark invalid fields.
     */
    public static final String MARK_INVALID_FIELD = "mark-all-invalid-fields";

    /**
     * The filter-parameter to provide links to navigate for long description
     * of images.
     */
    public static final String NAVIGATE_TO_LONG_DESCRIPTION =
            "provide-navigation-to-all-long-descriptions";

    /**
     * The filter-parameter to provide links to navigate to headings.
     */
    public static final String NAVIGATE_TO_HEADING =
            "provide-navigation-by-all-headings";

    /**
     * The filter-parameter to provide links to skip parts.
     */
    public static final String NAVIGATE_TO_SKIPPER =
            "provide-navigation-by-all-skippers";

    /**
     * The filter-parameter to hide visual changes of HaTeMiLe for Java.
     */
    public static final String HIDE_CHANGES = "hide-hatemile-changes";

    /**
     * The HTML code of page.
     */
    private final String htmlCode;

    /**
     * The HTML parser of page.
     */
    private final HTMLDOMParser htmlParser;

    /**
     * The configuration of HaTeMiLe for Java.
     */
    private final Configure configure;

    /**
     * The setted parameters of converter.
     */
    private final Map<String, Boolean> parameters;

    /**
     * The current URL of page.
     */
    private final String currentURL;

    /**
     * The user-agent of client.
     */
    private final String userAgent;

    /**
     * Initializes the converter.
     * @param pageCode The HTML code of page.
     * @param configurationPath The full path of configuration file.
     * @param filterParameters The setted parameters of converter.
     * @param locale The locale of client.
     * @param currentURLPage The current URL of page.
     * @param userAgentClient The user-agent of client.
     */
    public AccessibleConverter(final String pageCode,
            final String configurationPath,
            final Map<String, Boolean> filterParameters, final Locale locale,
            final String currentURLPage, final String userAgentClient) {
        htmlCode = pageCode;
        htmlParser = new JsoupHTMLDOMParser(pageCode);
        parameters = filterParameters;
        currentURL = currentURLPage;
        userAgent = userAgentClient;
        if (configurationPath == null) {
            configure = new Configure(locale);
        } else {
            configure = new Configure(configurationPath, locale);
        }
    }

    /**
     * Returns the content of file.
     * @param file The name of file.
     * @return The content of file.
     */
    private String getContentFromFile(final String file) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(getClass().getResourceAsStream(file));
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }

        return stringBuilder.toString();
    }

    /**
     * Include the stylesheet to hide the change of HaTeMiLe for Java.
     */
    private void increaseHideChanges() {
        if (parameters.get(HIDE_CHANGES)) {
            HTMLDOMElement head = htmlParser.find("head").firstResult();
            if (head == null) {
                head = htmlParser.createElement("head");
                htmlParser.find("html").firstResult().prependElement(head);
            }

            HTMLDOMElement styleSheet = htmlParser.createElement("style");
            styleSheet.setAttribute("type", "text/css");
            styleSheet.appendText(getContentFromFile("/css/hide_changes.css"));
            head.prependElement(styleSheet);
        }
    }

    /**
     * Execute the accessible associations solutions.
     */
    private void executeAssociation() {
        AccessibleAssociation accessibleAssociation =
                new AccessibleAssociationImplementation(htmlParser,
                    configure);
        if (parameters.get(ASSOCIATE_DATA_CELLS)) {
            accessibleAssociation.associateAllDataCellsWithHeaderCells();
        }
        if (parameters.get(ASSOCIATE_LABELS)) {
            accessibleAssociation.associateAllLabelsWithFields();
        }
    }

    /**
     * Execute the accessible CSS solutions.
     */
    private void executeCSS() {
        if (parameters.get(PROVIDE_SPEAK_PROPERTIES)) {
            StyleSheetParser cssParser = new PHCSSParser(htmlParser,
                    currentURL);
            AccessibleCSS accessibleCSS =
                    new AccessibleCSSImplementation(htmlParser, cssParser,
                        configure);
            accessibleCSS.provideAllSpeakProperties();
        }
    }

    /**
     * Execute the accessible display solutions.
     */
    private void executeDisplay() {
        AccessibleDisplay accessibleDisplay =
                new AccessibleDisplayScreenReaderImplementation(htmlParser,
                    configure, userAgent);
        if (parameters.get(DISPLAY_ALTERNATIVE_TEXT)) {
            accessibleDisplay.displayAllAlternativeTextImages();
        }
        if (parameters.get(DISPLAY_CELLS_HEADER)) {
            accessibleDisplay.displayAllCellHeaders();
        }
        if (parameters.get(DISPLAY_DRAGS_DROPS)) {
            accessibleDisplay.displayAllDragsAndDrops();
        }
        if (parameters.get(DISPLAY_LANGUAGES)) {
            accessibleDisplay.displayAllLanguages();
        }
        if (parameters.get(DISPLAY_LINK_ATTRIBUTES)) {
            accessibleDisplay.displayAllLinksAttributes();
        }
        if (parameters.get(DISPLAY_ROLES)) {
            accessibleDisplay.displayAllRoles();
        }
        if (parameters.get(DISPLAY_TITLES)) {
            accessibleDisplay.displayAllTitles();
        }
        if (parameters.get(DISPLAY_SHORTCUTS)) {
            accessibleDisplay.displayAllShortcuts();
        }
        if (parameters.get(DISPLAY_WAI_ARIA)) {
            accessibleDisplay.displayAllWAIARIAStates();
        }
    }

    /**
     * Execute the accessible events solutions.
     */
    private void executeEvent() {
        AccessibleEvent accessibleEvent =
                new AccessibleEventImplementation(htmlParser, configure);
        if (parameters.get(MAKE_ACCESSIBLE_CLICK)) {
            accessibleEvent.makeAccessibleAllClickEvents();
        }
        if (parameters.get(MAKE_ACCESSIBLE_DRAG_DROP)) {
            accessibleEvent.makeAccessibleAllDragandDropEvents();
        }
        if (parameters.get(MAKE_ACCESSIBLE_HOVER)) {
            accessibleEvent.makeAccessibleAllHoverEvents();
        }
    }

    /**
     * Execute the accessible form solutions.
     */
    private void executeForm() {
        AccessibleForm accessibleForm =
                new AccessibleFormImplementation(htmlParser, configure);
        if (parameters.get(MARK_AUTOCOMPLETE_FIELD)) {
            accessibleForm.markAllAutoCompleteFields();
        }
        if (parameters.get(MARK_RANGE_FIELD)) {
            accessibleForm.markAllRangeFields();
        }
        if (parameters.get(MARK_REQUIRED_FIELD)) {
            accessibleForm.markAllRequiredFields();
        }
        if (parameters.get(MARK_INVALID_FIELD)) {
            accessibleForm.markAllInvalidFields();
        }
    }

    /**
     * Execute the accessible navigation solutions.
     */
    private void executeNavigation() {
        AccessibleNavigation accessibleNavigation =
                new AccessibleNavigationImplementation(htmlParser, configure);
        if (parameters.get(NAVIGATE_TO_LONG_DESCRIPTION)) {
            accessibleNavigation.provideNavigationToAllLongDescriptions();
        }
        if (parameters.get(NAVIGATE_TO_HEADING)) {
            accessibleNavigation.provideNavigationByAllHeadings();
        }
        if (parameters.get(NAVIGATE_TO_SKIPPER)) {
            accessibleNavigation.provideNavigationByAllSkippers();
        }
    }

    /**
     * Convert HTML code in a HTML code more accessible.
     * @return The HTML code more accessible.
     */
    public String convert() {
        try {
            executeAssociation();
            executeCSS();
            executeDisplay();
            executeEvent();
            executeForm();
            executeNavigation();
            increaseHideChanges();

            return htmlParser.getHTML();
        } catch (Exception exception) {
            Logger.getLogger(AccessibleConverter.class.getName())
                    .log(Level.WARNING, null, exception);
            return htmlCode;
        }
    }
}
