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

    uses: ['App.view.main.region.Top',
        'App.view.main.region.Bottom',
        'App.view.main.region.Left',
        'App.view.main.region.Center'],

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
            xtype: 'main-left',
            region: 'west',
            width: 250,
            split: true,
            collapsible: true,
            listeners: {
                itemclick: 'onMenuItemClick'
            }
        }, {
            region: 'center',
            id: 'content-panel',
            xtype: 'main-center'

        }]
});


