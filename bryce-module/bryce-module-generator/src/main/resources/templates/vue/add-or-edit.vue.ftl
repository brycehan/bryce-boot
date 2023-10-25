<#assign title = "${(tableComment!?length > 2)?string(tableComment?remove_beginning('系统'), tableComment!)}">
<template>
  <el-dialog
    v-model="state.visible"
    :title="!state.dataForm.id ? '新增${title}' : '修改${title}'"
    :close-on-click-modal="false"
  >
    <el-form
      ref="dataFormRef"
      :model="state.dataForm"
      :rules="dataRules"
      label-width="100px"
      @keyup.enter="handleSubmit()"
      class="mr-4"
    >
<#if formLayout == 2>
  <#list formList?chunk(2) as row>
      <el-row>
    <#list row as field>
      <#assign fieldCommentEnd = field.fieldComment!?index_of("（")>
      <#if fieldCommentEnd == -1>
        <#assign fieldComment = field.fieldComment!>
      <#else>
        <#assign fieldComment = field.fieldComment!?substring(0, fieldCommentEnd)>
      </#if>
        <el-col :span="12">
        <#if field.formItemType == 'text'>
          <el-form-item label="${fieldComment!}" prop="${field.attrName}">
            <el-input v-model="state.dataForm.${field.attrName}" placeholder="请输入${fieldComment!}" clearable />
          </el-form-item>
        <#elseif field.formItemType == 'textarea'>
          <el-form-item label="${fieldComment!}" prop="${field.attrName}">
            <el-input type="textarea" v-model="state.dataForm.${field.attrName}" />
          </el-form-item>
        <#elseif field.formItemType == 'editor'>
          <el-form-item label="${fieldComment!}" prop="${field.attrName}">
            <el-input type="textarea1" v-model="state.dataForm.${field.attrName}" placeholder="${fieldComment!}" />
          </el-form-item>
        <#elseif field.formItemType == 'select'>
          <#if field.formDict??>
            <el-form-item label="${fieldComment!}" prop="${field.attrName}">
              <dict-select v-model="state.dataForm.${field.attrName}" dict-type="${field.formDict}" placeholder="请选择${fieldComment!}" clearable />
            </el-form-item>
          <#else>
            <el-form-item label="${fieldComment!}" prop="${field.attrName}">
              <el-select v-model="state.dataForm.${field.attrName}" placeholder="请选择" clearable>
                <el-option label="请选择" value="0" />
              </el-select>
            </el-form-item>
          </#if>
        <#elseif field.formItemType == 'radio'>
          <#if field.formDict??>
            <el-form-item label="${fieldComment!}" prop="${field.attrName}">
              <dict-radio-group v-model="state.dataForm.${field.attrName}" dict-type="${field.formDict}" />
            </el-form-item>
          <#else>
            <el-form-item label="${fieldComment!}" prop="${field.attrName}">
              <el-radio-group v-model="state.dataForm.${field.attrName}">
                <el-radio :label="0">启用</el-radio>
                <el-radio :label="1">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </#if>
        <#elseif field.formItemType == 'checkbox'>
          <el-form-item label="${fieldComment!}" prop="${field.attrName}">
            <el-checkbox-group v-model="state.dataForm.${field.attrName}">
              <el-checkbox :label="启用" name="type"/>
              <el-checkbox :label="禁用" name="type"/>
            </el-checkbox-group>
          </el-form-item>
        <#elseif field.formItemType == 'date'>
          <el-form-item label="${fieldComment!}" prop="${field.attrName}">
            <el-date-picker type="date" v-model="state.dataForm.${field.attrName}" placeholder="${fieldComment!}"/>
          </el-form-item>
        <#elseif field.formItemType == 'datetime'>
          <el-form-item label="${fieldComment!}" prop="${field.attrName}">
            <el-date-picker type="datetime" v-model="state.dataForm.${field.attrName}" placeholder="${fieldComment!}"/>
          </el-form-item>
        <#else>
          <el-form-item label="${fieldComment!}" prop="${field.attrName}">
            <el-input v-model="state.dataForm.${field.attrName}" placeholder="${fieldComment!}" />
          </el-form-item>
        </#if>
        </el-col>
    </#list>
      </el-row>
  </#list>
</#if>
<#if formLayout == 1>
<#list formList as field>
  <#assign fieldCommentEnd = field.fieldComment!?index_of("（")>
  <#if fieldCommentEnd == -1>
    <#assign fieldComment = field.fieldComment!>
  <#else>
    <#assign fieldComment = field.fieldComment!?substring(0, fieldCommentEnd)>
  </#if>
    <#if field.formItemType == 'text'>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <el-input v-model="state.dataForm.${field.attrName}" placeholder="请输入${fieldComment!}" />
      </el-form-item>
    <#elseif field.formItemType == 'textarea'>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <el-input type="textarea" v-model="state.dataForm.${field.attrName}" placeholder="请输入内容" />
      </el-form-item>
    <#elseif field.formItemType == 'editor'>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <el-input type="textarea" v-model="state.dataForm.${field.attrName}" placeholder="${fieldComment!}" />
      </el-form-item>
    <#elseif field.formItemType == 'select'>
      <#if field.formDict??>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <dict-select v-model="state.dataForm.${field.attrName}" dict-type="${field.formDict}" placeholder="${fieldComment!}" />
      </el-form-item>
      <#else>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <el-select v-model="state.dataForm.${field.attrName}" placeholder="请选择">
          <el-option label="请选择" value="0" />
        </el-select>
      </el-form-item>
      </#if>
    <#elseif field.formItemType == 'radio'>
      <#if field.formDict??>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <dict-radio-group v-model="state.dataForm.${field.attrName}" dict-type="${field.formDict}" />
      </el-form-item>
      <#else>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <el-radio-group v-model="state.dataForm.${field.attrName}">
          <el-radio :label="0">启用</el-radio>
        </el-radio-group>
      </el-form-item>
      </#if>
    <#elseif field.formItemType == 'checkbox'>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <el-checkbox-group v-model="state.dataForm.${field.attrName}">
          <el-checkbox :label="启用" name="type"/>
          <el-checkbox :label="禁用" name="type"/>
        </el-checkbox-group>
      </el-form-item>
    <#elseif field.formItemType == 'date'>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <el-date-picker type="date" v-model="state.dataForm.${field.attrName}" placeholder="${fieldComment!}"/>
      </el-form-item>
    <#elseif field.formItemType == 'datetime'>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <el-date-picker type="datetime" v-model="state.dataForm.${field.attrName}" placeholder="${fieldComment!}"/>
      </el-form-item>
    <#else>
      <el-form-item label="${fieldComment!}" prop="${field.attrName}">
        <el-input v-model="state.dataForm.${field.attrName}" placeholder="请输入${fieldComment!}" />
      </el-form-item>
    </#if>
</#list>
</#if>
    </el-form>
    <template #footer>
      <el-button @click="state.visible = false">取消</el-button>
      <el-button type="primary" @click="handleSubmit()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { getById, saveOrUpdate } from '@/api/${moduleName}/${functionName}'
import type { StateOptions } from "@/utils/state";
import { crud } from "@/utils/state";

const emit = defineEmits(['refreshPage'])

const state: StateOptions  = reactive({
  api: {
    saveOrUpdate,
    getById,
    emit
  },
  dataForm: {
  <#if baseClass??>
    id: undefined,
  </#if>
  <#list fieldList?filter(f -> !f.baseField && f.attrName != "tenantId") as field>
    <#if field.attrName == 'status'>
    ${field.attrName}: true<#sep>, </#sep>
    <#else>
    ${field.attrName}: ''<#sep>, </#sep>
    </#if>
  </#list>
  }
})

const dataFormRef = ref()

const dataRules = reactive({
<#list formList?filter(f -> f.formRequired || f.characterMaximumLength gt 0) as field>
  <#assign fieldCommentEnd = field.fieldComment!?index_of("（")>
  <#if fieldCommentEnd == -1>
  <#assign fieldComment = field.fieldComment!>
  <#else>
  <#assign fieldComment = field.fieldComment!?substring(0, fieldCommentEnd)>
  </#if>
  <#if field.formRequired && field.characterMaximumLength?c?number gt 0>
    ${field.attrName}: [
      { required: true, message: '必填项不能为空', trigger: 'blur' },
      { min: 0, max: ${field.characterMaximumLength?c?number}, message: '${fieldComment!}长度不能超过${field.characterMaximumLength?c}个字符', trigger: 'blur' }
    ]<#sep>,</#sep>
  <#elseif field.formRequired>
    ${field.attrName}: [{ required: true, message: '必填项不能为空', trigger: 'blur' }]<#sep>,</#sep>
  <#elseif field.characterMaximumLength?c?number gt 0>
    ${field.attrName}: [{ min: 0, max: ${field.characterMaximumLength?c}, message: '${fieldComment!}长度不能超过${field.characterMaximumLength?c}个字符', trigger: 'blur' }]<#sep>,</#sep>
  </#if>
</#list>
})

const { getData, handleSaveOrUpdate } = crud(state)

/** 初始化详情数据 */
const init = (id?: bigint) => {
  state.visible = true
  state.dataForm.id = undefined

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields()
  }

  // id 存在则为修改
  if (id) {
    getData(id)
  }
}

/** 表单提交 */
const handleSubmit = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false
    }

    handleSaveOrUpdate()
  })
}

defineExpose({
    init
})
</script>
