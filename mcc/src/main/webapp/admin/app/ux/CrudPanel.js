/**
 * 基于Ext.grid.Panel的查询面板封装
 */
Ext.define('App.ux.CrudPanel', {
    extend: 'Ext.grid.Panel',
    uses: ['App.ux.Window', 'App.ux.FormPanel','App.ux.SearchField', 'App.util.Downloader'],
    alias: 'widget.ux_crudpanel',
    autoScroll: true,
    loadMask: true,
    layout: "fit",
    autoLoadStore: true,
    closable: true,
    tbarItems: [],
    tbarModel: 2,
    entityId: "id", //默认实体ID字段名称
    //selModel: Ext.create('Ext.selection.CheckboxModel'),
    emptyText: '没有找到任何数据',
    loadMask: {
        msg: '正在加载数据,请稍等...'
    },
    viewConfig: {
        forceFit: false,
        stripeRows: true,
        enableTextSelection: true
    },
    createButton: {
        hidden: false
    },
    editButton: {
        hidden: false
    },
    deleteButton: {
        hidden: false
    },
    queryButton: {
        hidden: false
    },
    searchField: {
        hidden: false
    },
    exportButton: {
        hidden: false
    },
    importButton: {
        hidden: false
    },
    initComponent: function () {
        var me = this;
        me.tbar = me.buildTopToolbar();
        me.store = me.buildStore(me);
        // 是否预先加载数据
        if (me.autoLoadStore) {
            me.store.loadPage(1);
        }
        if (me.tbarModel >= 2) {
            var editWindow;
            // 添加按钮
            if (!me.createButton.hidden && me.window) {
                editWindow = me.buildEditWindow(me);
                var createButton = me.buildCreateButton(me, editWindow);
                me.tbar.add(createButton);
            }
            // 修改按钮
            if (!me.editButton.hidden && me.window) {
                var editWindow = editWindow ? editWindow : me
                    .buildEditWindow(me);
                var editButton = me.buildEditButton(me, editWindow);
                me.tbar.add(editButton);
            }
            // 删除按钮
            if (!me.deleteButton.hidden) {
                var deleteButton = me.buildDeleteButton(me);
                me.tbar.add(deleteButton);
            }
        }
        if (me.tbarModel >= 1) {
            // 查询按钮
            if (!me.queryButton.hidden && me.queryWindow) {
                me.queryWindow = me.buildQueryWindow(me);
                var queryButton = me.buildQueryButton(me);
                me.tbar.add(queryButton);
                me.tbar.add('-');
            }
        }
        //导入导出按钮
        if (me.tbarModel == 3) {
            me.tbar.add(' ');
            // 导入按钮
            if (!me.importButton.hidden) {
                var importButton = me.buildImportButton(me);
                me.tbar.add(importButton);
            }
            // 导出按钮
            if (!me.exportButton.hidden) {
                var exportButton = me.buildExportButton(me);
                me.tbar.add(exportButton);
            }
        }
        if (me.tbarItems.length > 0) {
            // 处理其他按钮
            Ext.Array.each(me.tbarItems, function (name, index) {
                me.tbar.add(me.tbarItems[index]);
            });
        }

        // 查询文本框
        if (!me.searchField.hidden) {
            var searchField = me.buildSearchField(me);
            me.tbar.add("->");
            me.tbar.add(searchField);
        }
        // 底部分页条
        me.dockedItems = me.buildDockedItems(me);
        me.callParent(arguments);
    },
    buildEditWindow: function (me) {
        if (!me.window) {
            alert("未配置编辑窗口！");
        }
        var formPanel = Ext.create('App.ux.FormPanel', {
            url: me.baseUrl + App.Config.Action.save,
            items: me.window.items
        });
        var window = Ext.create('App.ux.Window', {
            title: this.window.title,
            items: [formPanel],
            onSubmit: function () {
                if (!formPanel.form.isValid()) {
                    alert('请检查表单数据!');
                    return;
                }
                var formValues = formPanel.form.getValues();
                var record = formPanel.form.getRecord();
                if (record) {
                    record.set(formValues);//修改
                } else {
                    record = new DataModel();
                    record.set(formValues);
                    me.store.add(record); //添加
                    setTimeout(function () {
                        //由于ID在服务端生成，所以重新加载数据
                        me.store.loadPage(1);
                    }, 500);
                }
                this.close();
            },
            onReset: function () {
                formPanel.form.reset();
            }
        });
        return window;
    },
    buildEditButton: function (me, editWindow) {
        return {
            text: '修改',
            iconCls: "page_edit_1Icon",
            handler: function () {
                var record = me.getSelectionModel().getSelection()[0];
                if (Ext.isEmpty(record)) {
                    Ext.Msg.alert("提示", "请先选择要编辑的行!");
                    return;
                }

                editWindow.down('ux_formpanel').form.loadRecord(record);
                var comboboxs = Ext.ComponentQuery.query('ux_remotecombobox', editWindow);
                comboboxs = Array.isArray(comboboxs) ? comboboxs : new Array(comboboxs);
                Ext.Array.forEach(comboboxs, function (element, index, array) {
                    element.setValue(record.data[element.name]);
                    element.setRawValue(record.data[element.name.replace(/\.id/, '.name')]);
                });

                editWindow.show();
            }
        }
    },
    buildDeleteButton: function (me) {
        return {
            text: '删除',
            iconCls: "deleteIcon",
            handler: function () {
                var record = me.getSelectionModel().getSelection();
                if (Ext.isEmpty(record)) {
                    Ext.Msg.alert("提示", "请先选择要删除的行!");
                    return;
                }
                Ext.MessageBox.confirm("删除提示", "是否真的要删除数据？", function (ret) {
                    if (ret == "yes") {
                        me.store.remove(record[0]);
                    }
                });
            }
        }
    },
    buildImportButton: function (me) {
        var importButton = {
            text: '导入',
            iconCls: "importIcon",
            handler: function () {

            }
        };
        Ext.apply(importButton, me.importButton);
        return importButton;
    },
    buildExportButton: function (me) {
        var exportButton = {
            text: '导出',
            iconCls: "downloadIcon",
            handler: function () {
                var params = {fileName: encodeURIComponent(me.exportButton.fileName || '')};
                Ext.apply(params, me.store.proxy.extraParams);
                App.util.Downloader.get({
                    url: me.baseUrl + App.Config.Action.export,
                    params: params
                });
            }
        };
        Ext.apply(exportButton, me.exportButton);
        return exportButton;
    },
    buildSearchField: function (me) {
        var searchField = {
            xtype: 'ux_searchfield',
            text: '文本查询框',
            width: 180,
            store: me.store
        };
        Ext.apply(searchField, me.searchField);
        return searchField;
    },
    buildQueryButton: function (me) {
        if (!me.queryWindow) {
            alert("未配置窗口！");
        }
        var queryButton = {
            text: '查询',
            hidden: true,
            iconCls: "searchIcon",
            handler: function () {
                me.queryWindow.show();
            }
        };
        Ext.apply(queryButton, me.queryButton);
        return queryButton;
    },
    buildCreateButton: function (me, editWindow) {
        var createButton = {
            text: '添加',
            hidden: true,
            iconCls: "page_addIcon",
            handler: function () {
                editWindow.down('ux_formpanel').form.reset();
                editWindow.show();
            }
        }
        Ext.apply(createButton, me.createButton);
        return createButton;
    },
    buildQueryWindow: function (me) {
        var queryFormPanel = Ext.create('App.ux.FormPanel', {
            url: me.baseUrl + App.Config.Action.query,
            items: me.queryWindow.items
        });
        // 加载前，添加参数
        me.store.on('beforeload', function (store, options) {
            //兼容JPA:searchable查询
            var values = App.Config.Search.getValues(queryFormPanel.form);
            Ext.apply(store.proxy.extraParams, values);
        });
        return Ext.create('App.ux.Window', {
            title: me.queryWindow.title,
            items: [queryFormPanel],
            onSubmit: function () {
                me.store.load();
                this.close();
            },
            onReset: function () {
                queryFormPanel.form.reset();
            }
        });

    },
    buildTopToolbar: function (me) {
        return Ext.create('Ext.toolbar.Toolbar', {
            items: ['-']
        });
    },
    buildDockedItems: function (me) {
        return [
            {
                xtype: 'pagingtoolbar',
                paramNames: {start: 'startRow', limit: 'pageSize', page: 'pageNum'},
                store: me.store,
                dock: 'bottom',
                displayInfo: true,
                displayMsg: '记录 {0} - {1} of {2}',
                emptyMsg: "没有数据记录"
            }
        ]
    },
    buildStore: function (me) {
        // 定义数据模型
        //Ext.define('DataModel', {
        //    extend: 'Ext.data.Model',
        //    idProperty: 'id', // 实体主键
        //    fields: me.fields
        //});

        // 定义数据源
        var store = Ext.create('Ext.data.Store', {
            pageSize: me.pageSize ? me.pageSize : 50, // 分页大小
            remoteSort: true,
            autoLoad: true,
            autoSync: true,
            model: Ext.create('Ext.data.Model', {
                idProperty: 'id', // 实体主键
                fields: me.fields
            }),
            proxy: {
                url: me.baseUrl,
                type: 'rest', // Restful风格
                reader: {
                    rootProperty: 'content',
                    totalProperty: 'totalElements'
                },
                writer: {
                    type: 'json',
                    writeAllFields: false,
                    expandData: true //复杂嵌套类型，必须开启
                    //encode: true
                    //root: ''
                }
            },
            listeners: {
                write: function (store, operation) {
                    var record = operation.getRecords()[0],
                        name = Ext.String.capitalize(operation.action),
                        verb;
                    if (name == 'Destroy') {
                        record = operation.records[0];
                        verb = 'Destroyed';
                    } else {
                        verb = name + 'd';
                    }
                    App.common.msg(name, Ext.String.format("{0} ID: {1}", verb, record.getId()));
                }
            }
        });

        return store;
    }
});
