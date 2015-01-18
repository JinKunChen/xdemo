/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */



Ext.define('App.view.main.Main', {
    extend: 'Ext.container.Container',
    requires: [
        'App.view.main.MainController',
        'App.view.main.MainModel'
    ],

    uses: ['App.view.main.region.Top', 'App.view.main.region.Bottom'],

    xtype: 'app-main',

    controller: 'main',

    viewModel: {
        type: 'main'
    },

    layout: {
        type: 'border'
    },

    initComponent: function () {
        Ext.setGlyphFontFamily('FontAwesome'); //设置图标字体文件，以使用glyph属性
        this.callParent();
    },

    items: [
        {
            xtype: 'main-top',
            region: 'north'
        },
        {
            xtype: 'main-bottom',
            region: 'south',
            bind: '你好，{currentUser}'
        }, {
            xtype: 'panel',
            bind: {
                title: '{name}'
            },
            region: 'west',
            html: '<ul><li>This area is commonly used for navigation, for example, using a "tree" component.</li></ul>',
            width: 250,
            split: true,
            tbar: [{
                text: 'Button',
                handler: 'onClickButton'
            }]
        }, {
            region: 'center',
            xtype: 'tabpanel',
            items: [{
                title: '首页',
                glyph: 0xf015,
                html: '<h2>Content appropriate for the current navigation.</h2>'
            }]
        }]
});


