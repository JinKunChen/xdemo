Ext.define('App.view.main.region.Bottom',
    {
        extend: 'Ext.toolbar.Toolbar',
        alias: 'widget.main-bottom',
        items: [
            {
                xtype: 'tbtext'
            },
            '->',
            {
                xtype: 'tbtext',
                text: '关于我们',
                glyph: 0xf1ae
            }, '-', {
                xtype: 'tbtext',
                text: '联系我们',
                glyph: 0xf003
            }, '-', {
                xtype: 'tbtext',
                text: '版权声明',
                glyph: 0xf099
            }
        ]
    }
);