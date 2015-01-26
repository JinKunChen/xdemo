/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('App.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.window.MessageBox'
    ],

    alias: 'controller.main',

    uses: ['App.view.MenuPanel'],

    onChangePassword: function () {
        Ext.MessageBox.prompt('修改密码', '请输入新密码:', function (btn, text) {
            if (btn != 'ok') {
                return;
            }
            Ext.Ajax.request({
                url: '/api/account/change_password',
                method: 'POST',
                jsonData: text,
                scope: this
            });
        }, this);
    },

    onLogout: function () {
        Ext.MessageBox.confirm('注销', '确定注销吗?', function (btn) {
            if (btn != 'yes') {
                return;
            }
            Ext.Ajax.request({
                url: '/api/logout',
                method: 'POST',
                scope: this,
                callback: function () {

                    window.location.href = "/admin";
                    console.dir("logout success!");
                }
            });
        }, this);
    },

    onMenuItemClick: function (selModel, record) {
        //如果是叶子节点
        if (record.get('leaf')) {
            var mainPanel = Ext.getCmp("content-panel");

            var panel = mainPanel.getComponent('panel-' + record.get('id'));

            if (panel) {
                mainPanel.setActiveTab(panel);
            } else {
                //var baseUrl = ctx + record.get('basePath');
                var text = record.get('text');
                var iconCls = record.get('iconCls');
                var clazz = record.get('clazz');
                if (clazz) {
                    panel = Ext.create(clazz, {
                        //baseUrl: baseUrl,
                        title: text,
                        iconCls: iconCls
                    });
                } else {
                    panel = {
                        title: text,
                        iconCls: iconCls,
                        closable: true,
                        html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'
                        + record.get('hrefTarget') + '"></iframe>'
                    }
                }
                panel.itemId = 'panel-' + record.get('id');  //itemId 不再支持数字开头。

                var p = mainPanel.add(panel);
                mainPanel.setActiveTab(p);
            }
        }
    }
});
