<template>
  <el-card shadow="never">
    <el-form ref="queryFormRef" :model="state.queryForm" :inline="true" label-width="68px" @keyup.enter="getPage()" @submit.prevent>
<#list queryList?filter(f -> f.attrName != "tenantId") as field>
  <#assign fieldCommentEnd = field.fieldComment!?index_of("（")>
  <#if fieldCommentEnd == -1>
    <#assign fieldComment = field.fieldComment!>
  <#else>
    <#assign fieldComment = field.fieldComment!?substring(0, fieldCommentEnd)>
  </#if>
  <#if field.queryType == "between">
    <#if field.queryFormType == 'date'>
      <el-form-item label="${fieldComment!}" <#if field.fieldComment!?length==2>label-width="40px" </#if>prop="${field.attrName}">
        <el-date-picker
            v-model="state.range.${field.attrName}"
            type="daterange"
            unlink-panels
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
            clearable />
      </el-form-item>
    <#elseif field.queryFormType == 'datetime'>
      <el-form-item label="${fieldComment!}" <#if fieldComment!?length==2>label-width="40px" </#if>prop="${field.attrName}">
        <el-date-picker
            v-model="state.range.${field.attrName}"
            type="datetimerange"
            unlink-panels
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="YYYY-MM-DD HH:mm"
            clearable />
      </el-form-item>
    </#if>
  <#else>
      <el-form-item label="${fieldComment!}" <#if fieldComment!?length==2>label-width="40px" </#if>prop="${field.attrName}">
      <#if field.queryFormType == 'text' || field.queryFormType == 'textarea' || field.queryFormType == 'editor'>
        <el-input v-model="state.queryForm.${field.attrName}" placeholder="请输入${fieldComment!}" clearable />
      <#elseif field.queryFormType == 'select'>
        <#if field.formDict??>
        <dict-select v-model="state.queryForm.${field.attrName}" dict-type="${field.formDict}" placeholder="${fieldComment!}" clearable />
        <#else>
        <el-select v-model="state.queryForm.${field.attrName}" placeholder="${fieldComment!}" clearable>
          <el-option label="选择" value="0"/>
        </el-select>
        </#if>
      <#elseif field.queryFormType == 'radio'>
        <#if field.formDict??>
          <dict-radio-group v-model="state.queryForm.${field.attrName}" dict-type="${field.formDict}" />
        <#else>
          <el-radio-group v-model="state.queryForm.${field.attrName}">
            <el-radio label="选择" value="0"/>
          </el-radio-group>
        </#if>
      <#elseif field.queryFormType == 'date'>
        <el-date-picker
          v-model="state.queryForm.${field.attrName}"
          type="daterange"
          value-format="YYYY-MM-DD"
          clearable />
      <#elseif field.queryFormType == 'datetime'>
        <el-date-picker
          v-model="state.queryForm.${field.attrName}"
          type="datetimerange"
          value-format="YYYY-MM-DD HH:mm"
          clearable />
      <#else>
        <el-input v-model="state.queryForm.${field.attrName}" placeholder="${fieldComment!}" clearable />
      </#if>
      </el-form-item>
  </#if>
</#list>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="getPage()">搜索</el-button>
        <el-button icon="RefreshLeft" @click="handleResetQuery()">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row class="mb-2">
      <el-button v-auth="'${moduleName}:${functionName}:save'" type="primary" icon="Plus" @click="handleAddOrEdit()">新增</el-button>
      <el-button v-auth="'${moduleName}:${functionName}:delete'" type="danger" icon="Delete" @click="handleDeleteBatch()">删除</el-button>
    </el-row>
    <el-table
      v-loading="state.loading"
      :data="state.data"
      :border="true"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" header-align="center" align="center" width="50" />
    <#list gridList as field>
      <#assign fieldCommentEnd = field.fieldComment!?index_of("（")>
      <#if fieldCommentEnd == -1>
        <#assign fieldComment = field.fieldComment!>
      <#else>
        <#assign fieldComment = field.fieldComment!?substring(0, fieldCommentEnd)>
      </#if>
      <#if field.formDict??>
      <dict-table-column label="${fieldComment!}" prop="${field.attrName}" <#if field.gridSort>sortable="custom" </#if>dict-type="${field.formDict}" />
      <#else>
      <el-table-column label="${fieldComment!}" prop="${field.attrName}" <#if field.gridSort>sortable="custom" </#if>header-align="center" align="center" />
      </#if>
    </#list>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template #default="scope">
          <el-button v-auth="'${moduleName}:${functionName}:update'" type="primary" link @click="handleAddOrEdit(scope.row.id)">编辑</el-button>
          <el-button v-auth="'${moduleName}:${functionName}:delete'" type="danger" link @click="handleDeleteBatch(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="state.current"
      :page-size="state.size"
      :total="state.total"
      :page-sizes="state.pageSizes"
      :layout="state.layout"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

    <!-- 弹窗，新增 / 修改 -->
    <AddOrEdit ref="addOrEditRef" @refresh-page="getPage" />
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import AddOrEdit from './add-or-edit.vue'
import { page, deleteByIds } from '@/api/${moduleName}/${functionName}'
import type { StateOptions } from "@/utils/state";
import { crud } from "@/utils/state";

const state: StateOptions = reactive({
  api: {
    page,
    deleteByIds
  },
  queryForm: {
    <#list queryList?filter(f -> f.attrName != "tenantId") as field>
    ${field.attrName}: ''<#sep>,</#sep>
    </#list>
  },
<#if queryList?filter(f -> f.queryType == "between")?size gt 0>
  range: {
  <#list queryList?filter(f -> f.queryType == "between") as field>
    ${field.attrName}: ''<#sep >,</#sep>
  </#list>
  },
</#if>
})

const queryFormRef = ref()
const addOrEditRef = ref()

onMounted(() => {
  getPage()
})

const {
  getPage,
  handleSizeChange,
  handleCurrentChange,
  handleDeleteBatch,
  handleSelectionChange,
} = crud(state)

/** 重置按钮操作 */
const handleResetQuery = () => {
  for (const key in state.range) {
    state.range[key] = []
  }

  if(queryFormRef.value) {
    queryFormRef.value.resetFields()
  }

  getPage()
}

/** 新增/修改 弹窗 */
const handleAddOrEdit = (id?: bigint) => {
  addOrEditRef.value.init(id)
}
</script>
