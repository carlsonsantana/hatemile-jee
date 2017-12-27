# HaTeMiLe for JEE

HaTeMiLe for JEE is a library that help developers to convert a HTML code in a code more accessible for Java EE web applications.

## Accessibility solutions

* [Associate HTML elements](https://github.com/carlsonsantana/HaTeMiLe-for-Java/wiki/Associate-HTML-elements);
* [Provide a polyfill to CSS Speech and CSS Aural properties](https://github.com/carlsonsantana/HaTeMiLe-for-Java/wiki/Provide-a-polyfill-to-CSS-Speech-and-CSS-Aural-properties);
* [Display inacessible informations of page](https://github.com/carlsonsantana/HaTeMiLe-for-Java/wiki/Display-inacessible-informations-of-page);
* [Enable all functionality of page available from a keyboard](https://github.com/carlsonsantana/HaTeMiLe-for-Java/wiki/Enable-all-functionality-of-page-available-from-a-keyboard);
* [Improve the acessibility of forms](https://github.com/carlsonsantana/HaTeMiLe-for-Java/wiki/Improve-the-acessibility-of-forms);
* [Provide accessibility resources to navigate](https://github.com/carlsonsantana/HaTeMiLe-for-Java/wiki/Provide-accessibility-resources-to-navigate).

## Documentation

To generate the full API documentation of HaTeMiLe for JEE:

1. [Install Maven](https://maven.apache.org/install.html);
2. Execute the command `mvn site` in HaTeMiLe for JEE directory.

## Usage

Configure the filter in the `web.xml` file in the web application /WEB-INF directory.

You will need the following entries in the <web-app> element:

```xml
<filter>
    <filter-name>AccessibleFilter</filter-name>
    <filter-class>org.hatemile.jee.AccessibleFilter</filter-class>
    <init-param>
        <!-- HaTeMiLe for JEE paramters -->
        <param-name>provide-all-speak-properties</param-name>
        <param-value>false</param-value> 
    </init-param>
</filter>

<filter-mapping>
    <filter-name>AccessibleFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

### Filter parameters

#### `associate-all-data-cells-with-header-cells`

Associate all data cells with header cells of all tables.

**Value pattern**: [true | false]

#### `associate-all-labels-with-fields`

Associate all labels with fields.

**Value pattern**: [true | false]

#### `provide-all-speak-properties`

Provide the CSS features of speaking and speech properties in all elements.

**Value pattern**: [true | false]

#### `display-all-alternative-text-images`

Display the alternative text of all images.

**Value pattern**: [true | false]

#### `display-all-cell-headers`

Display the headers of each data cell of all tables.

**Value pattern**: [true | false]

#### `display-all-drags-and-drops`

Display that an elements have drag-and-drop events.

**Value pattern**: [true | false]

#### `display-all-languages`

Display the language of all elements.

**Value pattern**: [true | false]

#### `display-all-links-attributes`

Display the attributes of all links.

**Value pattern**: [true | false]

#### `display-all-roles`

Display the WAI-ARIA roles of all elements.

**Value pattern**: [true | false]

#### `display-all-titles`

Display the titles of all elements.

**Value pattern**: [true | false]

#### `display-all-shortcuts`

Display all shortcuts.

**Value pattern**: [true | false]

#### `display-all-wai-aria-states`

Display the WAI-ARIA attributes of all elements.

**Value pattern**: [true | false]

#### `make-accessible-all-click-events`

Make all click events available from a keyboard.

**Value pattern**: [true | false]

#### `make-accessible-all-drag-and-drop-events`

Make all Drag-and-Drop events available from a keyboard.

**Value pattern**: [true | false]

#### `make-accessible-all-hover-events`

Make all hover events available from a keyboard.

**Value pattern**: [true | false]

#### `mark-all-autocomplete-fields`

Mark that the fields have autocomplete.

**Value pattern**: [true | false]

#### `mark-all-range-fields`

Mark that the fields have range.

**Value pattern**: [true | false]

#### `mark-all-required-fields`

Mark that the fields is required.

**Value pattern**: [true | false]

#### `mark-all-invalid-fields`

Mark a solution to display that a fields are invalid.

**Value pattern**: [true | false]

#### `provide-navigation-to-all-long-descriptions`

Provide an alternative way to access the longs descriptions of all elements.

**Value pattern**: [true | false]

#### `provide-navigation-by-all-headings`

Provide navigation by headings.

**Value pattern**: [true | false]

#### `provide-navigation-by-all-skippers`

Provide navigation by content skippers.

**Value pattern**: [true | false]

#### `hide-hatemile-changes`

Hide visual changes of HaTeMiLe.

**Value pattern**: [true | false]

#### `configuration`

The configuration path of HaTeMiLe for Java, without properties extension.

## Contributing

If you want contribute with HaTeMiLe for JEE, read [contributing guidelines](CONTRIBUTING.md).

## See also
* [HaTeMiLe for Java](https://github.com/carlsonsantana/HaTeMiLe-for-Java)
* [HaTeMiLe for JavaScript](https://github.com/carlsonsantana/HaTeMiLe-for-JavaScript)
