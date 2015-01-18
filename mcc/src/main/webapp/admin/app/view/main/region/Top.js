Ext.define('App.view.main.region.Top', {
    extend: 'Ext.toolbar.Toolbar',
    alias: 'widget.main-top',
    require: 'Ext.toolbar.Spacer',
    items: [
        {
            //xtype:'button',默认就是button
            text: '首页',
            hidden: false,
            glyph: 0xf015
        },
        {
            xtype: 'tbspacer',
            width: 100
        },
        '->',//右对齐面板
        '-',//一个竖直的分隔条
        {
            xtype: 'textfield',
            name: 'searchField',
            emptyText: '输入您的搜索关键词'
        }, {
            text: '搜索',
            glyph: 0xf00e
        }, '-',//一个竖直的分隔条
        {
            text: '用户登录',
            glyph: 0xf007
        }, {
            text: '注销',
            glyph: 0xf011
        }, {
            glyph: 0xf102,
            tooltip: '隐藏顶部区域',
            disableMouseOver: true
        }
    ]
});