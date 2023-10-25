import request from '@/utils/request'

export const saveOrUpdate = (data: any) => {
    if (data.id) {
        return request.put('/${moduleName}/${functionName}', data)
    } else {
        return request.post('/${moduleName}/${functionName}', data)
    }
}

export const deleteByIds = (ids: bigint[]) => {
    return request.delete('/${moduleName}/${functionName}', { data: { ids } })
}

export const getById = (id: bigint) => {
    return request.get(`/${moduleName}/${functionName}/<#noparse>${id}</#noparse>`)
}

export const page = (data: any) => {
    return request.post('/${moduleName}/${functionName}/page', data)
}
