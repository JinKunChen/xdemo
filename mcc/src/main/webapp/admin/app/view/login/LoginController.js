/**
 * 登录控制器
 *
 * Created by Chen on 15/1/18.
 */
Ext.define('App.view.login.LoginController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.window.MessageBox'
    ],

    alias: 'controller.login',

    //用户登录按钮事件处理
    onLoginBtnClick: function () {
        var form = this.lookupReference('form');
        if (form.isValid()) {
            this.login({
                data: form.getValues(),
                scope: this,
                success: 'onLoginSuccess',
                failure: 'onLoginFailure'
            })
        }
    },

    onLoginFailure: function () {
        // Do something
        Ext.getBody().unmask();
    },

    onLoginSuccess: function (loginName) {
        console.log('登录成功，用户名： ' + loginName);
        this.fireViewEvent('login', loginName);
        //var org = this.lookupReference('organization').getSelectedRecord();
        // this.fireViewEvent('login', this.getView(), user, org, this.loginManager);
    },

    login: function (options) {
        Ext.Ajax.request({
            url: '/api/authentication',
            method: 'POST',
            params: options.data,
            scope: this,
            callback: this.onLoginReturn,
            original: options
        });
    },
    /**
     applyModel: function(model) {
        return model && Ext.data.schema.Schema.lookupEntity(model);
    },
     */
    onLoginReturn: function (options, success, response) {
        options = options.original;
        if (success) {
            console.log('login success');
            Ext.callback(options.success, options.scope, [options.data.j_username]);
        } else {
            Ext.callback(options.failure, options.scope, [response]);
        }
    }
});