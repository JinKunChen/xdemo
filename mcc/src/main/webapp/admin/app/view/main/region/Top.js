Ext.define('App.view.main.region.Top', {
    extend: 'Ext.toolbar.Toolbar',
    alias: 'widget.main-top',
    require: 'Ext.toolbar.Spacer',
    items: [
        {
            //xtype:'button',默认就是button
            text: '管理后台',
            hidden: false,
            glyph: 0xf19c
        },
        {
            xtype: 'tbspacer',
            width: 100
        },
        '->',//右对齐面板
        '-',//一个竖直的分隔条
        {
            text: '用户登录',
            glyph: 0xf007
        }, {
            glyph: 0xf013,
            tooltip: '修改密码',
            handler: 'onChangePassword'
        }, {
            text: '注销',
            glyph: 0xf011,
            handler: 'onLogout'
        }, {
            glyph: 0xf102,
            tooltip: '隐藏顶部区域',
            disableMouseOver: true
        }
    ]
});