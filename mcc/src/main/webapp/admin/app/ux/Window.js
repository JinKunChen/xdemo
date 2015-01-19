/**
 * 基于Ext.window.Window的Window窗口封装
 */

Ext.define('App.ux.Window', {
    extend: 'Ext.window.Window',
    alias: 'widget.ux_window',
    modal: true,
    shadow: true,
    constrain: true,
    resizable: false,
    collapsible: false,
    maximizable: false,
    animCollapse: true,
    titleCollapse: true,
    layout: "fit",
    closeAction: "hide",
    buttonAlign: "center",
    btn1text: '提交',
    btn2text: '重置',
    btn3text: '取消',
    btn1iconCls: 'searchIcon',
    btn2iconCls: 'resetIcon',
    btn3iconCls: 'cancelIcon',
    onSubmit: function () {
        alert('onSubmit 没定义!');
    },
    onReset: function () {
        alert('onReset 没定义!');
    },
    onCancel: function () {
        this.close();
    },
    buttons: [
        {
            name: 'btn1',
            handler: function () {
                this.onSubmit();
            }
        },
        {
            name: 'btn2',
            handler: function () {
                this.onReset();
            }
        },
        {
            name: 'btn3',
            handler: function () {
                this.onCancel();
            }
        }
    ],
    initComponent: function () {
        // 处理按钮
        Ext.Array.each(this.buttons, function (name, index) {
            var button = this.buttons[index];
            // 解决按钮回调方法scope问题
            button.scope = this;
            if (button.name == 'btn1') {
                button.text = this.btn1text;
                button.iconCls = this.btn1iconCls;
            }
            if (button.name == 'btn2') {
                button.text = this.btn2text;
                button.iconCls = this.btn2iconCls;
            }
            if (button.name == 'btn3') {
                button.text = this.btn3text;
                button.iconCls = this.btn3iconCls;
            }
        }, this);

        this.callParent(arguments);
    }
});
