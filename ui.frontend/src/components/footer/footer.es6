/*
    footer jquery plugin
    Author:
*/

(function ($, window, document, undefined) {
    'use strict';

    // Create the defaults
    const pluginName = 'footer';
    const noop = function () { };
    const defaults = {
        color: 'red'
    };

    // The actual plugin constructor
    const Plugin = function (element, options) {
        this.element = element;
        this.$el = $(element);

        // override options with data options, if they exist.
        // For AEM integration, always prefer loading
        // options via data attrs.

        this.settings = $.extend({}, defaults, options);

        this._defaults = defaults;
        this._name = pluginName;

        this.init();
    };

    const methods = {
        init() {
            this.addListeners();
        },

        addListeners() {}
    };

    // Avoid Plugin.prototype conflicts
    $.extend(Plugin.prototype, methods);

    $.fn[pluginName] = function (options) {
        options = options || {}; // for default initialization

        return this.each(function () {
            // preventing against multiple instantiations
            if (!$.data(this, 'plugin_' + pluginName)) {
                $.data(this, 'plugin_' + pluginName, new Plugin(this, options));
            }
            $(this).data('internals', methods);
        });
    };
})($, window, document);