/**
 * 登录视图
 *
 * Created by Chen on 15/1/18.
 */
Ext.define('App.view.login.Login', {
    requires: ['App.view.login.LoginController'],
    extend: 'Ext.window.Window',
    controller: 'login',
    closable: false,
    resizable: false,
    modal: true,
    //draggable: false,
    autoShow: true,
    title: '用户登录---MCC运营后台',
    glyph: 'xf015@FontAwesome',
    items: [{
        xtype: 'form',//父窗体
        reference: 'form',
        bodyPadding: 20,
        items: [{
            xtype: 'textfield',
            name: 'j_username',
            labelWidth: 50,
            fieldLabel: '用户名',
            allowBlank: false,
            value:'admin',
            emptyText: '用户名或邮箱地址'
        }, {
            xtype: 'textfield',
            name: 'j_password',
            labelWidth: 50,
            inputType: 'password',
            fieldLabel: '密  码',
            allowBlank: false,
            value:'admin',
            emptyText: '请输入您的密码'
        },{
            xtype: 'checkboxfield',
            name: '_spring_security_remember_me',
            labelWidth: 50,
            fieldLabel: '记住我'
        }]
    }],

    buttons: [{
        name: 'registbutton',
        text: '用户注册',
        glyph: 'xf118@FontAwesome'
    }, {
        name: 'loginbutton',
        text: '用户登录',
        glyph: 'xf110@FontAwesome',
        region: 'center',
        listeners: {
            click: 'onLoginBtnClick'//单击事件 调用LoginConroller.js中的onLoginbtnClick函数
        }
    }]
});