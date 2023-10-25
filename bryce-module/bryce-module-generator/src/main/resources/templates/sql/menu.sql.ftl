<#assign now = "now()">
<#if dbType=="SQLServer">
  <#assign now = "getDate()">
</#if>
<#assign nextId = "(select max(id)+1 from brc_sys_menu)">
<#if dbType=="MySQL">
  <#assign nextId = "(select maxId from (select max(id)+1 as maxId from brc_sys_menu) as t)">
</#if>
<#assign menuName = "${(tableComment!?length > 2)?string(tableComment?remove_beginning('系统'), tableComment!)}">
-- 添加菜单
insert into brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time)
values (${nextId}, '${menuName}管理', 'M', 1, '${moduleName}/${functionName}/index', '${moduleName}:${functionName}:page', 'icon-menu', '0', '0', null, '1', '0', '1', ${now}, null, null);

-- 按钮父菜单ID
set @parentId := (select max(id) from brc_sys_menu);

-- 按钮
insert into brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time)
values (${nextId}, '${menuName}新增', 'B', @parentId,null, '${moduleName}:${functionName}:save', '', '0', '1', null, '1', '0', '1', ${now}, null, null);
insert into brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time)
values (${nextId}, '${menuName}修改', 'B', @parentId, null, '${moduleName}:${functionName}:update', '', '0', '2', null, '1', '0', '1', ${now}, null, null);
insert into brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time)
values (${nextId}, '${menuName}删除', 'B', @parentId, null, '${moduleName}:${functionName}:delete', '', '0', '3', null, '1', '0', '1', ${now}, null, null);
insert into brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time)
values (${nextId}, '${menuName}详情', 'B', @parentId, null, '${moduleName}:${functionName}:info', '', '0', '4', null, '1', '0', '1', ${now}, null, null);
insert into brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time)
values (${nextId}, '${menuName}导出', 'B', @parentId, null, '${moduleName}:${functionName}:export', '', '0', '5', null, '1', '0', '1', ${now}, null, null);
insert into brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time)
values (${nextId}, '${menuName}导入', 'B', @parentId, null, '${moduleName}:${functionName}:import', '', '0', '6', null, '1', '0', '1', ${now}, null, null);