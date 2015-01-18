Ext.define('App.controller.Root',
    {
        extend: 'Ext.app.Controller',
        uses: ['App.view.login.Login', 'App.view.main.Main'],

        /**
         * 初始化事件
         */
        onLaunch: function () {
            if (Ext.isIE8) {
                Ext.Msg.alert('亲，本例子不支持IE8哟');
                return;
            }
            var session = this.session = new Ext.data.Session();
            this.login = new App.view.login.Login({
                session: session,
                listeners: {
                    scope: this,
                    login: 'onLogin'
                }
            });
        },

        /**
         * loginController 的 "login" 事件回调.
         * @param user
         * @param loginManager
         */
        onLogin: function (username, loginController) {
            this.login.destroy();
            this.user = username;
            console.log('username: ' + username);
            this.showUI();
        },

        showUI: function () {
            console.log('show ui started');
            //显示主界面  
            this.viewport = new App.view.main.Main(
                {   //用户信息传入视图           
                    viewModel: {
                        data: {
                            currentUser: this.user
                        }
                    }
                }
            );
        }
    });  